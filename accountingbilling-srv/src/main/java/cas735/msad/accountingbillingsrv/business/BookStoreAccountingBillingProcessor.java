package cas735.msad.accountingbillingsrv.business;

import cas735.msad.accountingbillingsrv.business.entities.BookStoreCart;
import cas735.msad.accountingbillingsrv.business.entities.BookStoreCartItem;
import cas735.msad.accountingbillingsrv.business.entities.Communitymember;
import cas735.msad.accountingbillingsrv.business.entities.Bookstoreoperator;
import cas735.msad.accountingbillingsrv.dto.BookStoreOrder;
import cas735.msad.accountingbillingsrv.dto.BookStoreOrderList;
import cas735.msad.accountingbillingsrv.dto.EmailRequest;
import cas735.msad.accountingbillingsrv.dto.EmailResponse;
import cas735.msad.accountingbillingsrv.ports.BookStoreAccountingBillingService;
import cas735.msad.accountingbillingsrv.ports.BookStoreOrderManagementService;
import cas735.msad.accountingbillingsrv.ports.EmailService;
import cas735.msad.accountingbillingsrv.ports.CommunityMemberRepository;
import cas735.msad.accountingbillingsrv.ports.BookStoreOperatorRepository;

import org.springframework.stereotype.Service;

import java.util.Optional;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BookStoreAccountingBillingProcessor implements BookStoreAccountingBillingService{
    
    private final BookStoreOrderManagementService bookStoreOrderManagementService;
    private final EmailService emailService;
    private final CommunityMemberRepository communityMemberRepository;
    private final BookStoreOperatorRepository bookStoreOperatorRepository;
    private final static String ADMINEMAIL = "macdropadminoffice@mcmaster.ca";
    
    @Autowired
    public BookStoreAccountingBillingProcessor(BookStoreOrderManagementService bookStoreOrderManagementService, EmailService emailService, CommunityMemberRepository communityMemberRepository, BookStoreOperatorRepository bookStoreOperatorRepository) {
        this.bookStoreOrderManagementService = bookStoreOrderManagementService;
        this.emailService = emailService;
        this.communityMemberRepository = communityMemberRepository;
        this.bookStoreOperatorRepository = bookStoreOperatorRepository;
    }

    @Override
    public void bookStoreCartAccountingBilling(BookStoreCart bookStoreCart) {
        log.info("The bookstore cart is \n" + bookStoreCart.toString());

        //Convert the bookstore cart to bookstore order list
        log.info("Converting the bookstore cart to bookstore order list...");
        Set<String> bookStoreOperatorIds = new HashSet<String>();
        for(BookStoreCartItem bookStoreCartItem : bookStoreCart.getBookStoreCartItems()){
            bookStoreOperatorIds.add(bookStoreCartItem.getBookStoreOperatorId());
        }
        log.info("The numer of order is " + bookStoreOperatorIds.size());
        
        Set<BookStoreOrder> bookStoreOrders = new HashSet<BookStoreOrder>();
        for(String bookStoreOperatorId: bookStoreOperatorIds){
            String communitymemberid = bookStoreCart.getId();
            String bookstoreoperatorid = bookStoreOperatorId;
            String bookstoreitems = "";
            Double mddprice = 0.0;
            String ordertime = null;

            for (BookStoreCartItem bookStoreCartItem : bookStoreCart.getBookStoreCartItems()){
                if(bookStoreCartItem.getBookStoreOperatorId().equals(bookStoreOperatorId)){
                    bookstoreitems += bookStoreCartItem.getBookStoreItemName() + "(ID:" + bookStoreCartItem.getBookStoreItemId() + "); ";
                    mddprice += (Double)bookStoreCartItem.getMddPrice() * bookStoreCartItem.getQuantity();
                }
            }
            DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            ZonedDateTime timestamp = ZonedDateTime.now();
            log.info("The timestamp to create food order is " + timestamp.format(timeFormat));
            ordertime = timestamp.format(timeFormat);
            bookStoreOrders.add(BookStoreOrder.builder()
            .communitymemberid(communitymemberid)
            .bookstoreoperatorid(bookstoreoperatorid)
            .bookstoreitems(bookstoreitems)
            .mddprice(String.format("%.2f", mddprice))
            .ordertime(ordertime)
            .build()
            );
        }
        BookStoreOrderList bookStoreOrderList = BookStoreOrderList.builder().bookstoreorders(bookStoreOrders).build();
        log.info("The bookstore order list is \n" + bookStoreOrderList.toString());

        //Send the food order billing info to community member by ExternalSys(email)
        int communityMemberId = Integer.valueOf(bookStoreOrders.iterator().next().getCommunitymemberid());
        Optional<Communitymember> communityMember = communityMemberRepository.findById(communityMemberId);
        String email = communityMember.get().getEtfemail();
        log.info("The member's email is " + email);

        //Check if Mdd balance is enough to pay the bookstore bill, if no, it should return a relative message.
        if(Double.parseDouble(communityMember.get().getCurrentmdd()) < bookStoreCart.getTotal()){
            log.info("Low Mdd balance, the payment failed");
            return;
        }

        //billing info
        String billingItems = "";
        for(BookStoreOrder bookStoreOrder : bookStoreOrderList.getBookstoreorders()){
            billingItems += "Items:[" + bookStoreOrder.getBookstoreitems() + "] From Bookstore:[" + bookStoreOrder.getBookstoreoperatorid() + "] Price:[ MDD " + bookStoreOrder.getMddprice() +"]\n";  
        }
        String billingInfo = "\n********************************************\n" + 
        "Hi, Here is your bookstore billing on MacDrop:\nItems details:\n" + billingItems + "\n\nTotal price: MDD " + String.format("%.2f", bookStoreCart.getTotal()) + "\n\nRegards, \nMacDrop" + "\n********************************************\n";

        EmailRequest emailReq = EmailRequest.builder()
        .from(BookStoreAccountingBillingProcessor.ADMINEMAIL)
        .to(email) 
        .object("MacDrop Bookstore Billing")
        .message(billingInfo)
        .build();
        emailService.send(emailReq);     
        log.info(billingInfo);

        //Pay by MDD from community member's MDD wallet
        Double currentMdd = Double.parseDouble(communityMember.get().getCurrentmdd());
        Double afterPaymentMdd = currentMdd - bookStoreCart.getTotal();
        communityMember.get().setCurrentmdd(afterPaymentMdd.toString());
        communityMemberRepository.save(communityMember.get());

        //Add MDD to bookstore operator's MDD wallet
        for(BookStoreOrder bookStoreOrder : bookStoreOrderList.getBookstoreorders()){
            Integer bookStoreOperatorId = Integer.parseInt(bookStoreOrder.getBookstoreoperatorid());
            Optional<Bookstoreoperator> bookstoreoperator = bookStoreOperatorRepository.findById(bookStoreOperatorId);
            Double currentMddBookStoreOperator = Double.parseDouble(bookstoreoperator.get().getCurrentmdd());
            Double afterPaymentMddBookStoreOperator = currentMddBookStoreOperator + Double.parseDouble(bookStoreOrder.getMddprice());
            bookstoreoperator.get().setCurrentmdd(afterPaymentMddBookStoreOperator.toString());
            bookStoreOperatorRepository.save(bookstoreoperator.get());
        }


        //Send the bookstore order list to BookStoreOrderManagement
        log.info("Sending the bookstore order list to BookStoreOrderManagement...");
        this.bookStoreOrderManagementService.createBookStoreOrder(bookStoreOrderList);
    }
    
}
