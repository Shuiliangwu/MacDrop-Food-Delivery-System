package cas735.msad.adminmanagementsrv.adapters;

import cas735.msad.adminmanagementsrv.MddCompensationQueueConfiguration;
import cas735.msad.adminmanagementsrv.business.entities.Biker;
import cas735.msad.adminmanagementsrv.business.entities.Foodorder;
import cas735.msad.adminmanagementsrv.business.entities.Location;
import cas735.msad.adminmanagementsrv.dto.MddCompensation;
import cas735.msad.adminmanagementsrv.ports.ApprovalsService;
import cas735.msad.adminmanagementsrv.ports.FinancialService;
import cas735.msad.adminmanagementsrv.ports.MonitoringService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@Component
@Slf4j
public class AdminController {

    public final MonitoringService monitoringService;
    public final FinancialService financialService;
    public final ApprovalsService approvalsService;

    @Autowired
    public AdminController(MonitoringService monitoringService, FinancialService financialService, ApprovalsService approvalsService) {
        this.monitoringService = monitoringService;
        this.financialService = financialService;
        this.approvalsService = approvalsService;
    }

    // Monitoring Service

    @GetMapping("/admin/bikers/{id}")
    public Biker getBiker(@PathVariable int id){
        return monitoringService.getBiker(id);
    }

    @GetMapping("/admin/bikers")
    public List<Biker> getAllBikers(){
        return monitoringService.getAllBikers();
    }

    @GetMapping("/admin/bikers/currentworkloads")
    public Map<Integer, Integer> getCurrentBikerLoads(){
        return monitoringService.getCurrentBikerLoads();
    }
    @GetMapping("/admin/bikers/historyworkloads")
    public Map<Integer, Integer> getHistoryBikerLoads(){
        return monitoringService.getHistoryBikerLoads();
    }

    @GetMapping("/admin/locations/{id}")
    public Location getLocation(@PathVariable int id){
        return monitoringService.getLocation(id);
    }

    @GetMapping("/admin/locations")
    public List<Location> getAllLocations(){
        return monitoringService.getAllLocations();
    }

    @GetMapping("/admin/locations/currentworkloads")
    public Map<Integer, Integer> getCurrentLocationLoads(){
        return monitoringService.getLocationCurrentLoads();
    }
    @GetMapping("/admin/locations/historyworkloads")
    public Map<Integer, Integer> getHistoryLocationLoads(){
        return monitoringService.getLocationHistoryLoads();
    }

    // Financial Service
    @GetMapping("/admin/financial/bikercentercost/{fromTime}to{toTime}")
    public String getBikerCenterCost(@PathVariable String fromTime, @PathVariable String toTime){
        BigDecimal bikerCenterCost = financialService.getBikerCenterCost(fromTime, toTime);
        return "The biker center cost between " + fromTime + " to " + toTime + " is " + bikerCenterCost.toString();
    }

    @GetMapping("/admin/financial/bookstorecost/{fromTime}to{toTime}")
    public String getBookStoreCost(@PathVariable String fromTime, @PathVariable String toTime){
        BigDecimal bookStoreCost = financialService.getBookStoreCost(fromTime, toTime);
        return "The bookstore cost between " + fromTime + " to " + toTime + " is " + bookStoreCost.toString();
    }

    @GetMapping("/admin/financial/report/{fromTime}to{toTime}")
    public Map<String, BigDecimal> getFinancialReport(@PathVariable String fromTime, @PathVariable String toTime){
        return financialService.getFinancialReport(fromTime, toTime);
    }

    // Daily transaction approval to food provider
    @GetMapping("/admin/dailytransaction/foodorder/billingstatement/{id}/{date}")
    public String getDailyFoodOrderBillStatement(@PathVariable String id, @PathVariable String date){
        return approvalsService.getDailyFoodOrderBillStatement(id, date);
    }

    @GetMapping("/admin/dailytransaction/foodorder/approval/{id}/{date}")
    public void approveDailyFoodOrderTransactions(@PathVariable String id, @PathVariable String date){
        approvalsService.approveDailyFoodOrderTransactions(id, date);
    }
}
