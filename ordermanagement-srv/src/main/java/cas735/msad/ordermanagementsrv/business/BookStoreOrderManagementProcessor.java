package cas735.msad.ordermanagementsrv.business;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

import cas735.msad.ordermanagementsrv.business.entities.Bookstoreorder;
import cas735.msad.ordermanagementsrv.business.entities.Bookstoreoperator;
import cas735.msad.ordermanagementsrv.dto.BookStoreOrderList;
import cas735.msad.ordermanagementsrv.dto.NotificationCommand;
import cas735.msad.ordermanagementsrv.dto.EmailRequest;
import cas735.msad.ordermanagementsrv.dto.TimeRange;
import cas735.msad.ordermanagementsrv.dto.MddCompensation;
import cas735.msad.ordermanagementsrv.dto.response.BookStoreOrderManagementResponse;
import cas735.msad.ordermanagementsrv.ports.BookStoreOrderManagementService;
import cas735.msad.ordermanagementsrv.ports.BookStoreOrderRepository;
import cas735.msad.ordermanagementsrv.ports.BookStoreOperatorRepository;
import cas735.msad.ordermanagementsrv.ports.NotificationService;
import cas735.msad.ordermanagementsrv.ports.EmailService;
import cas735.msad.ordermanagementsrv.ports.MddCompensationService;

@Slf4j
@Service
public class BookStoreOrderManagementProcessor implements BookStoreOrderManagementService{
    
    private final BookStoreOrderRepository bookStoreOrderRepository;
    private final BookStoreOperatorRepository bookStoreOperatorRepository;
    private final NotificationService notificationService; 
    private final EmailService emailService;
    private final MddCompensationService mddCompensationService;
    
    @Autowired
    public BookStoreOrderManagementProcessor(BookStoreOrderRepository bookStoreOrderRepository, BookStoreOperatorRepository bookStoreOperatorRepository, NotificationService notificationService, EmailService emailService, MddCompensationService mddCompensationService) {
        this.bookStoreOrderRepository = bookStoreOrderRepository;
        this.bookStoreOperatorRepository = bookStoreOperatorRepository;
        this.notificationService = notificationService;
        this.emailService = emailService;
        this.mddCompensationService = mddCompensationService;
    }

    public void createBookStoreOrder(BookStoreOrderList bookStoreOrderList) {
        //Create the bookstore order(s)
        log.info("Creating bookstore order(s)...");
        for(Bookstoreorder bookstoreorder: bookStoreOrderList.getBookstoreorders()){
            bookStoreOrderRepository.save(bookstoreorder);
        }
        log.info("Bookstore orders created");

        //Notify the community member
        String communityMemberId = bookStoreOrderList.getBookstoreorders().iterator().next().getCommunitymemberid();
        log.info("Start to notify community member " + communityMemberId);
        NotificationCommand notificationCommunityMember = NotificationCommand.builder()
        .from("OrderManagementService")
        .to(communityMemberId)
        .message("Your bookstore order has been plcaed, please pick at bookstore, thanks!")
        .build();
        notificationService.notify(notificationCommunityMember);

        //Notify the bookstore operator 
        for(Bookstoreorder bookstoreorder : bookStoreOrderList.getBookstoreorders()){
            log.info("Start to notify bookstore operator " + bookstoreorder.getBookstoreoperatorid());
            NotificationCommand notificationBookStoreOperator = NotificationCommand.builder()
            .from("OrderManagementService")
            .to(bookstoreorder.getBookstoreoperatorid())
            .message("New book store order, thanks!")
            .build();
            notificationService.notify(notificationBookStoreOperator);
        }
    }

    @Override
    public BookStoreOrderManagementResponse askForBookStoreCompensation(String bookStoreOperatorId, TimeRange timeRange) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            ZonedDateTime leftTime = ZonedDateTime.of(Integer.parseInt(timeRange.getLeftyear()), Integer.parseInt(timeRange.getLeftmonth()), Integer.parseInt(timeRange.getLeftday()), Integer.parseInt(timeRange.getLefthour()), Integer.parseInt(timeRange.getLeftmin()), Integer.parseInt(timeRange.getLeftsec()), 0, ZoneId.systemDefault());
            log.info("The left time in time range is " + leftTime.format(formatter));
            ZonedDateTime rightTime = ZonedDateTime.of(Integer.parseInt(timeRange.getRightyear()), Integer.parseInt(timeRange.getRightmonth()), Integer.parseInt(timeRange.getRightday()), Integer.parseInt(timeRange.getRighthour()), Integer.parseInt(timeRange.getRightmin()), Integer.parseInt(timeRange.getRightsec()), 0, ZoneId.systemDefault());
            log.info("The right time in time range is " + rightTime.format(formatter));

            List<Bookstoreorder> bookStoreOrdersByTimeRange = bookStoreOrderRepository.findByOrderTimeRange(bookStoreOperatorId, leftTime.format(formatter), rightTime.format(formatter));
            log.info("The bookstore orders for " + bookStoreOperatorId + " between " + leftTime.format(formatter) + " and " + rightTime.format(formatter) + " are \n" + bookStoreOrdersByTimeRange);
            
            if(bookStoreOrdersByTimeRange.size() == 0){
                return BookStoreOrderManagementResponse.builder().statusCode(200).response("Sorry, you don't have any book store orders during the given time").build();
            }

            String bookStoreOrderInfo = "";
            Double totalMdd = 0.0;
            for(Bookstoreorder bookstoreorder : bookStoreOrdersByTimeRange){
                bookStoreOrderInfo += bookstoreorder.toString() + "\n";
                totalMdd += Double.parseDouble(bookstoreorder.getMddprice());
            }

            Bookstoreoperator bookstoreoperator = bookStoreOperatorRepository.findById(Integer.parseInt(bookStoreOperatorId)).get();
            String bookStoreOperatorUserName = bookstoreoperator.getUsername();
            String bookStoreOperatorEamil = bookstoreoperator.getEtfemail();

            String compensationMessage = "\nDear Administrator,\n\nThe following are the list and invoice for bookstore MDD merchandise between " 
            + leftTime.format(formatter) 
            + " and " 
            + rightTime.format(formatter)
            + ". Please compensate me accordingly.\n"
            + "Items sold with MDD:\n"
            + bookStoreOrderInfo
            + "\nTotal MDD:\n"
            + totalMdd.toString()
            + "\n\n"
            + "Regards,\n"
            + bookStoreOperatorUserName;
            log.info("The compensation message is " + compensationMessage);

            Double MddAfterCompensation = Double.parseDouble(bookstoreoperator.getCurrentmdd()) - totalMdd;
            bookstoreoperator.setCurrentmdd(MddAfterCompensation.toString());
            bookStoreOperatorRepository.save(bookstoreoperator);

            //Send list and invoice to admin through email
            EmailRequest req = EmailRequest.builder()
            .from(bookStoreOperatorEamil)
            .to("admins@mcmaster.ca") 
            .object("BookStore MDD Compensation " + "[" + bookStoreOperatorId + "]")
            .message(compensationMessage)
            .build();
            emailService.send(req);

            //Send list and invoice to admin service
            MddCompensation mddCompensation = MddCompensation.builder()
            .listinvoice(bookStoreOrderInfo)
            .starttime(leftTime.format(formatter))
            .endtime(rightTime.format(formatter))
            .totalmdd(totalMdd.toString())
            .message(compensationMessage)
            .bookstoreoperatorid(bookStoreOperatorId)
            .bookstoreoperatoremail(bookStoreOperatorEamil)
            .build();
            mddCompensationService.askForMddCompensation(mddCompensation);

            return BookStoreOrderManagementResponse.builder().statusCode(200).response("Asking for compensation...").build();
        } catch (Exception e) {
            return BookStoreOrderManagementResponse.builder().statusCode(500).response("Internal Server Error").build();
        }
        
    }
}
