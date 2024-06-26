package cas735.msad.adminmanagementsrv.business;

import cas735.msad.adminmanagementsrv.business.entities.Bookstoreorder;
import cas735.msad.adminmanagementsrv.business.entities.Foodorder;
import cas735.msad.adminmanagementsrv.ports.FinancialBookStoreOrderRepository;
import cas735.msad.adminmanagementsrv.ports.FinancialFoodOrderRepository;
import cas735.msad.adminmanagementsrv.ports.FinancialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FinancialProcessor implements FinancialService {
    private final FinancialFoodOrderRepository financialFoodOrderRepository;
    private final FinancialBookStoreOrderRepository financialBookStoreOrderRepository;
    private final Double costPerDelivery = 10.00;

    @Autowired
    public FinancialProcessor(FinancialFoodOrderRepository financialFoodOrderRepository, FinancialBookStoreOrderRepository financialBookStoreOrderRepository) {
        this.financialFoodOrderRepository = financialFoodOrderRepository;
        this.financialBookStoreOrderRepository = financialBookStoreOrderRepository;
    }

    @Override
    public BigDecimal getBikerCenterCost(String fromTime, String toTime) {

        List<Foodorder> foodorders = financialFoodOrderRepository.findByOrdertimeBetween(fromTime, toTime);

        List<Foodorder> validFoodOrders = new ArrayList();

        for(Foodorder foodorder : foodorders){
            if(foodorder.getStatus().equals("dropped-off & complete")){
                validFoodOrders.add(foodorder);
            }
        }

        double bikerCenterCost = validFoodOrders.size() * costPerDelivery;

        return BigDecimal.valueOf(bikerCenterCost).setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public BigDecimal getBookStoreCost(String fromTime, String toTime) {
        List<Bookstoreorder> bookstoreorders = financialBookStoreOrderRepository.findByOrdertimeBetween(fromTime, toTime);
        double bookStoreCost = 0.0;
        for (Bookstoreorder bookstoreorder : bookstoreorders) {
            bookStoreCost += Double.parseDouble(bookstoreorder.getMddprice());
        }
        return BigDecimal.valueOf(bookStoreCost).setScale(2, RoundingMode.HALF_UP);

    }

    @Override
    public Map<String, BigDecimal> getFinancialReport(String fromTime, String toTime) {
        Map<String, BigDecimal> financialReport = new HashMap<>();
        BigDecimal bikerCenterCost = getBikerCenterCost(fromTime, toTime);
        BigDecimal MMDRewardProgramCost = getBookStoreCost(fromTime, toTime);
        financialReport.put("Biker Center Cost, Cnd$", bikerCenterCost);
        financialReport.put("MDD Reward Program Cost, Cnd$", MMDRewardProgramCost);

        financialReport.put("Cost In Total (" + fromTime + " to " + toTime+ "), Cnd$", bikerCenterCost.add(MMDRewardProgramCost));
        return financialReport;
    }
}
