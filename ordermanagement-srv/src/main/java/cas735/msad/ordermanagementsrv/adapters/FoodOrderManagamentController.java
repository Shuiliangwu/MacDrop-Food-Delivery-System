package cas735.msad.ordermanagementsrv.adapters;

import lombok.extern.slf4j.Slf4j;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.web.bind.annotation.RestController;
import org.springframework.stereotype.Component;

import cas735.msad.ordermanagementsrv.dto.FoodOrderList;
import cas735.msad.ordermanagementsrv.ports.FoodOrderManagementService;
import cas735.msad.ordermanagementsrv.OrderManagementQueueConfiguration;

@RestController
@Component
@Slf4j
public class FoodOrderManagamentController {
    public final FoodOrderManagementService foodOrderManagementService;

    @Autowired
    public FoodOrderManagamentController(FoodOrderManagementService foodOrderManagementService){
        this.foodOrderManagementService = foodOrderManagementService;
    }

    /*
     * Test payload to create FoodOrder
     * {  
    "communitymemberid": "10000032",  
    "foodproviderid": "20000032",  
    "fooditems": "xxx",  
    "foodorderprice": "100",
    "ordertime": "2022-10-17T16:02:29",
    "bikerid": "20000032",
    "dropofflocationid": "80000032",
    "estimatedpickuptime": "2022-10-17T18:02:29",
    "status": "placed"
    }
     */

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = OrderManagementQueueConfiguration.QUEUE_NAME_FOODORDER, durable = "true"),
            exchange = @Exchange(
                    value = OrderManagementQueueConfiguration.EXCHANGE_NAME_FOODORDER, ignoreDeclarationExceptions = "true"),
                    key = OrderManagementQueueConfiguration.ROUTING_FOODORDER))
    public void receive(FoodOrderList req) {
        try {
            log.info("Reading message: '" + req + "'");
            foodOrderManagementService.createFoodOrder(req);
        } catch (Exception e) {
            log.info("Error receiving message from AccountingBillingService");
            log.info(e.toString());
        }
    }
}
