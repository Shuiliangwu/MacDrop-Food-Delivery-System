package cas735.msad.ordermanagementsrv.adapters;

import org.springframework.stereotype.Service;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;

import cas735.msad.ordermanagementsrv.OrderManagementQueueConfiguration;
import cas735.msad.ordermanagementsrv.ports.MddCompensationService;
import cas735.msad.ordermanagementsrv.dto.MddCompensation;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
@Service
public class MddCompensationClient implements MddCompensationService{

    private final RabbitTemplate rabbitTemplate;
    
    @Autowired
    public MddCompensationClient(RabbitTemplate rabbitTemplate){
        this.rabbitTemplate = rabbitTemplate;
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
    }

    @Override
    public void askForMddCompensation(MddCompensation mddCompensation) {
        log.info("Sending MDD compensation to AdminService...");
        rabbitTemplate.convertAndSend(OrderManagementQueueConfiguration.EXCHANGE_NAME_MDDCOMPENSATION, OrderManagementQueueConfiguration.ROUTING_MDDCOMPENSATION, mddCompensation);
        log.info("MDD compensation sent");
    }
    
}
