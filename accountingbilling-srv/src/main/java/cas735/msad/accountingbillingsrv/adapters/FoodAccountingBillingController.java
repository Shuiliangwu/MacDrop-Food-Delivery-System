package cas735.msad.accountingbillingsrv.adapters;

import lombok.extern.slf4j.Slf4j;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cas735.msad.accountingbillingsrv.business.entities.FoodCart;
import cas735.msad.accountingbillingsrv.ports.FoodAccountingBillingService;
import cas735.msad.accountingbillingsrv.AccountingBillingMessageQueueConfiguration;

import org.springframework.beans.factory.annotation.Autowired;

@Component
@Slf4j
public class FoodAccountingBillingController {

    private final FoodAccountingBillingService foodAccountingBillingService;

    @Autowired
    public FoodAccountingBillingController(FoodAccountingBillingService foodAccountingBillingService){
        this.foodAccountingBillingService = foodAccountingBillingService;
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = AccountingBillingMessageQueueConfiguration.QUEUE_NAME_FOODCART, durable = "true"),
            exchange = @Exchange(
                    value = AccountingBillingMessageQueueConfiguration.EXCHANGE_NAME_FOODCART, ignoreDeclarationExceptions = "true"),
                    key = AccountingBillingMessageQueueConfiguration.ROUTING_FOODCART))
    public void receive(FoodCart req) {
        try {
            log.info("Reading message: '" + req + "'");
            foodAccountingBillingService.foodCartAccountingBilling(req);
        } catch (Exception e) {
            log.info("Error receiving message from FoodCartManagement");
            log.info(e.toString());
        }
    }
}
