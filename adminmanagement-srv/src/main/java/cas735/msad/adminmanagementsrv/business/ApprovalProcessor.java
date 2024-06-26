package cas735.msad.adminmanagementsrv.business;

import cas735.msad.adminmanagementsrv.business.entities.Bookstoreorder;
import cas735.msad.adminmanagementsrv.business.entities.Foodorder;
import cas735.msad.adminmanagementsrv.business.entities.Foodprovider;
import cas735.msad.adminmanagementsrv.dto.EmailRequest;
import cas735.msad.adminmanagementsrv.dto.EtfTransferRequest;
import cas735.msad.adminmanagementsrv.dto.MddCompensation;
import cas735.msad.adminmanagementsrv.ports.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ApprovalProcessor implements ApprovalsService {
    private final FinancialBookStoreOrderRepository financialBookStoreOrderRepository;
    private final FinancialFoodOrderRepository financialFoodOrderRepository;
    private final FoodProviderRepository foodProviderRepository;
    private final EmailService emailService;
    private final EtfTransferService etfTransferService;
    private final static String ADMINEMAIL = "macdropadminoffice@mcmaster.ca";
    double totalFoodOrderAmount;
    List<Foodorder> foodOrders = null;

    @Autowired
    public ApprovalProcessor(FinancialBookStoreOrderRepository financialBookStoreOrderRepository, FinancialFoodOrderRepository financialFoodOrderRepository, FoodProviderRepository foodProviderRepository, EmailService emailService, EtfTransferService etfTransferService) {
        this.financialBookStoreOrderRepository = financialBookStoreOrderRepository;
        this.financialFoodOrderRepository = financialFoodOrderRepository;
        this.foodProviderRepository = foodProviderRepository;
        this.emailService = emailService;
        this.etfTransferService = etfTransferService;
    }


    @Override
    public String getDailyFoodOrderBillStatement(String id, String date) {
        // set up date
        String dayEnd = date + " 23:59:59";
        String dayStart = date + " 00:00:00";

        log.info("Food Order BillStatement Time Period: " + dayStart + " to " + dayEnd);

        //get billing Statement
        totalFoodOrderAmount = 0.00;
        String billingInfo = "";
        foodOrders = financialFoodOrderRepository.findByOrdertimeBetweenAndFoodproviderid(dayStart, dayEnd, id);

        for (Foodorder foodOrder:foodOrders) {
            if(foodOrder.getStatus().equals("dropped-off & complete")){
              totalFoodOrderAmount += Double.parseDouble(foodOrder.getFoodorderprice());
              billingInfo += "Transaction Time: [" + foodOrder.getOrdertime() + "] Food Item: [" + foodOrder.getFooditems() + "] Purchased by Community Member: [" + foodOrder.getCommunitymemberid() + "] Price: [CAD " + foodOrder.getFoodorderprice() + "]\n";
            }
        }
        String billingStatement = "\n********************************************\n" +
                "Hi, Here is the daily transaction summary of your food store on MacDrop today by " + dayEnd.substring(0, 19) + ":\nBilling Details:\n" + billingInfo + "\n\nYour Total Amount On The Way: CAD " + String.format("%.2f", totalFoodOrderAmount) + "\n\nRegards, \nMacDrop" + "\n********************************************\n";
        return billingStatement;
    }


    @Override
    public void approveDailyFoodOrderTransactions(String id, String date) {
        // get food provider ETF email
        String billingStatement = getDailyFoodOrderBillStatement(id, date);
        String email;
        Optional<Foodprovider> tempFoodprovider = foodProviderRepository.findById(Integer.valueOf(id));
        if (!tempFoodprovider.isPresent() || foodOrders.isEmpty()) {
            log.info("No transaction at this food store. No payment to be processed");
        } else {
            email = tempFoodprovider.get().getEtfemail();
            log.info("The food provider's email is " + email);

            //send email to food provider
            EmailRequest emailReq = EmailRequest.builder()
                    .from(ADMINEMAIL)
                    .to(email)
                    .object("MacDrop Food Billing Statement")
                    .message(billingStatement)
                    .build();
            emailService.send(emailReq);

            //ETF Transfer to food provider
            EtfTransferRequest etfPaymentReq = EtfTransferRequest.builder()
                    .from(ADMINEMAIL)
                    .to(email)
                    .message("The Payment From MacDrop Admin")
                    .amount(String.valueOf(BigDecimal.valueOf(totalFoodOrderAmount).setScale(2, RoundingMode.UP)))
                    .build();
            etfTransferService.etfTransfer(etfPaymentReq);
        }
    }

    @Override
    public void approveWeeklyBookStoreMddCompensationInvoice(MddCompensation mddCompensation) {
        String startTime = mddCompensation.getStarttime();
        String endTime = mddCompensation.getEndtime();
        String bookStoreOperatorId = mddCompensation.getBookstoreoperatorid();

        log.info("MDD Compensation Invoice Time Period: " + startTime + " to " + endTime);

        // check MDD compensation invoice

        double totalMdd = 0.00;
        List<Bookstoreorder> bookstoreorders = financialBookStoreOrderRepository.findByOrdertimeBetweenAndBookstoreoperatorid(startTime, endTime, bookStoreOperatorId);

        for (Bookstoreorder bookStoreOrder: bookstoreorders) {
            totalMdd += Double.parseDouble(bookStoreOrder.getMddprice());
        }

        log.info("Checking the MacDrop: The total MMD for compensation from book store id " + bookStoreOperatorId + " is " + totalMdd);

        if (totalMdd == Double.parseDouble(mddCompensation.getTotalmdd())){
            log.info("Matching with the MMD from Invoice");
            String approvedMessage = "\n********************************************\n" +
                    "Hi, Your Total Amount of MDD Compensation: CAD" + BigDecimal.valueOf(totalMdd).setScale(2, RoundingMode.HALF_UP) + " Is On The Way. " +
                    "\n\nRegards, \nMacDrop" + "\n********************************************\n";

            // send approval message to book store
            EmailRequest emailReq = EmailRequest.builder()
                    .from(ADMINEMAIL)
                    .to(mddCompensation.getBookstoreoperatoremail())
                    .object("MacDrop MDD Compensation")
                    .message(approvedMessage)
                    .build();
            emailService.send(emailReq);

            //ETF Transfer to book store
            EtfTransferRequest etfPaymentReq = EtfTransferRequest.builder()
                    .from(ADMINEMAIL)
                    .to(mddCompensation.getBookstoreoperatoremail())
                    .message("The Payment From MacDrop Admin For MDD Compensation")
                    .amount(String.valueOf(BigDecimal.valueOf(totalMdd).setScale(2, RoundingMode.UP)))
                    .build();
            etfTransferService.etfTransfer(etfPaymentReq);
        }
        else {
            log.info("Not Matching with the MMD from Invoice");
            String disapprovedMessage = "\n********************************************\n" +
                    "Sorry, Your Request of Total Amount of MDD Compensation: CAD" + BigDecimal.valueOf(Double.parseDouble(mddCompensation.getTotalmdd())).setScale(2, RoundingMode.HALF_UP) + " Is Denied. Please Check Your Invoice Again. " +
                    "\n\nRegards, \nMacDrop" + "\n********************************************\n";

            // send disapproval message to book store
            EmailRequest emailReq = EmailRequest.builder()
                    .from(ADMINEMAIL)
                    .to(mddCompensation.getBookstoreoperatoremail())
                    .object("MacDrop MDD Compensation")
                    .message(disapprovedMessage)
                    .build();
            emailService.send(emailReq);
        }

    }
}

