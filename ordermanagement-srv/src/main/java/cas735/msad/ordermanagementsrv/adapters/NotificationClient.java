package cas735.msad.ordermanagementsrv.adapters;

import org.springframework.stereotype.Service;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;

import cas735.msad.ordermanagementsrv.OrderManagementQueueConfiguration;
import cas735.msad.ordermanagementsrv.dto.NotificationCommand;
import cas735.msad.ordermanagementsrv.ports.NotificationService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
@Service
public class NotificationClient implements NotificationService{
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public NotificationClient(RabbitTemplate rabbitTemplate){
        this.rabbitTemplate = rabbitTemplate;
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
    }

    @Override
    public void notify(NotificationCommand notification) {
        log.info("Notifying...");
        rabbitTemplate.convertAndSend(OrderManagementQueueConfiguration.EXCHANGE_NAME_NOTIFICATION, OrderManagementQueueConfiguration.ROUTING_NOTIFICATION, notification);
        log.info("Notified");
    }
}
