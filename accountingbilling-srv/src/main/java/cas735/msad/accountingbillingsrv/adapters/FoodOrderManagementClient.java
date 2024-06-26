package cas735.msad.accountingbillingsrv.adapters;

import org.springframework.stereotype.Service;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;

import cas735.msad.accountingbillingsrv.OrderManagementQueueConfiguration;
import cas735.msad.accountingbillingsrv.dto.FoodOrderList;
import cas735.msad.accountingbillingsrv.ports.FoodOrderManagementService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
@Service
public class FoodOrderManagementClient implements FoodOrderManagementService{

    private final RabbitTemplate rabbitTemplate;
    
    @Autowired
    public FoodOrderManagementClient(RabbitTemplate rabbitTemplate){
        this.rabbitTemplate = rabbitTemplate;
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
    }

    @Override
    public void createFoodOrder(FoodOrderList foodOrderList) {
        log.info("Sending food order list to OrderManagementService...");
        rabbitTemplate.convertAndSend(OrderManagementQueueConfiguration.EXCHANGE_NAME_FOODORDER, OrderManagementQueueConfiguration.ROUTING_FOODORDER, foodOrderList);
        log.info("Food order list sent");
    }
    
}
