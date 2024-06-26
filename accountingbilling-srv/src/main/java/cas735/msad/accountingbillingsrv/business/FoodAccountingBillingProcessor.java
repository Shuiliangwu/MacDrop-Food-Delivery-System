package cas735.msad.accountingbillingsrv.business;

import cas735.msad.accountingbillingsrv.business.entities.FoodCart;
import cas735.msad.accountingbillingsrv.business.entities.FoodCartItem;
import cas735.msad.accountingbillingsrv.business.entities.Communitymember;
import cas735.msad.accountingbillingsrv.dto.FoodOrder;
import cas735.msad.accountingbillingsrv.dto.FoodOrderList;
import cas735.msad.accountingbillingsrv.dto.EmailRequest;
import cas735.msad.accountingbillingsrv.dto.EtfTransferRequest;
import cas735.msad.accountingbillingsrv.ports.FoodAccountingBillingService;
import cas735.msad.accountingbillingsrv.ports.FoodOrderManagementService;
import cas735.msad.accountingbillingsrv.ports.EmailService;
import cas735.msad.accountingbillingsrv.ports.EtfTransferService;
import cas735.msad.accountingbillingsrv.ports.CommunityMemberRepository;

import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FoodAccountingBillingProcessor implements FoodAccountingBillingService{

    private final FoodOrderManagementService foodOrderManagementService;
    private final EmailService emailService;
    private final EtfTransferService etfTransferService;
    private final CommunityMemberRepository communityMemberRepository;
    private final static String ADMINEMAIL = "macdropadminoffice@mcmaster.ca";

    @Autowired
    public FoodAccountingBillingProcessor(FoodOrderManagementService foodOrderManagementService, EmailService emailService, EtfTransferService etfTransferService, CommunityMemberRepository communityMemberRepository) {
        this.foodOrderManagementService = foodOrderManagementService;
        this.emailService = emailService;
        this.etfTransferService = etfTransferService;
        this.communityMemberRepository = communityMemberRepository;
    }


    @Override
    public void foodCartAccountingBilling(FoodCart foodCart) {
        log.info("The food cart is \n" + foodCart.toString());

        //Convert the food cart to food order list
        log.info("Converting the food cart to food order list...");
        Set<String> foodProviderIds = new HashSet<String>();
        for(FoodCartItem foodCartItem : foodCart.getFoodCartItems()){
            foodProviderIds.add(foodCartItem.getFoodProviderId());
        }
        log.info("The number of order is " + foodProviderIds.size());
        
        Set<FoodOrder> foodOrders = new HashSet<FoodOrder>();
        for(String foodProviderId: foodProviderIds){
            String communitymemberid = foodCart.getId();
            String foodproviderid = foodProviderId;
            String fooditems = "";
            Double foodorderprice = 0.0;
            String ordertime = null;
            String bikerid = null;
            String dropofflocationid = null;
            String estimatedpickuptime = null;
            String status = "location & time needed";

            for (FoodCartItem foodCartItem : foodCart.getFoodCartItems()){
                if(foodCartItem.getFoodProviderId().equals(foodProviderId)){
                    fooditems += foodCartItem.getFoodName() + "(ID" + foodCartItem.getFoodId() + "); ";
                    foodorderprice += (Double)foodCartItem.getPrice() * foodCartItem.getQuantity();
                }
            }
            DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            ZonedDateTime timestamp = ZonedDateTime.now();
            log.info("The timestamp to create food order is " + timestamp.format(timeFormat));
            ordertime = timestamp.format(timeFormat);
            foodOrders.add(FoodOrder.builder()
            .communitymemberid(communitymemberid)
            .foodproviderid(foodproviderid)
            .fooditems(fooditems)
            .foodorderprice(String.format("%.2f", foodorderprice))
            .ordertime(ordertime)
            .bikerid(bikerid)
            .dropofflocationid(dropofflocationid)
            .estimatedpickuptime(estimatedpickuptime)
            .status(status)
            .build()
            );
        }
        FoodOrderList foodOrderList = FoodOrderList.builder().foodorders(foodOrders).build();
        log.info("The food order list is \n" + foodOrderList.toString());
 
        //Send the food order billing info to community member by ExternalSys(email)
        int communityMemberId = Integer.valueOf(foodOrders.iterator().next().getCommunitymemberid());
        Optional<Communitymember> communityMember = communityMemberRepository.findById(communityMemberId);
        String email = communityMember.get().getEtfemail();
        log.info("The member's email is " + email);

        //billing info
        String billingItems = "";
        for(FoodOrder foodOrder : foodOrderList.getFoodorders()){
            billingItems += "Items:[" + foodOrder.getFooditems() + "] From Food Provider:[" + foodOrder.getFoodproviderid() + "] Price:[ CAD " + foodOrder.getFoodorderprice() +"]\n";  
        }
        String billingInfo = "\n********************************************\n" + 
        "Hi, Here is your food billing on MacDrop:\nItems details:\n" + billingItems + "\n\nTotal price: CAD " + String.format("%.2f", foodCart.getTotal()) + "\n\nRegards, \nMacDrop" + "\n********************************************\n";

        EmailRequest emailReq = EmailRequest.builder()
        .from(FoodAccountingBillingProcessor.ADMINEMAIL)
        .to(email) 
        .object("MacDrop Food Billing")
        .message(billingInfo)
        .build();
        emailService.send(emailReq);

        //ETF Transfer to Admin
        EtfTransferRequest etfPaymentReq = EtfTransferRequest.builder()
        .from(email)
        .to(FoodAccountingBillingProcessor.ADMINEMAIL)
        .message("The Payment From Community Member")
        .amount(String.format("%.2f", foodCart.getTotal()))
        .build();
        etfTransferService.etfTransfer(etfPaymentReq);

        //Send the food order list to FoorOrderManagement
        log.info("Sending the food order list to FoorOrderManagement...");
        this.foodOrderManagementService.createFoodOrder(foodOrderList);
    }
    
}
