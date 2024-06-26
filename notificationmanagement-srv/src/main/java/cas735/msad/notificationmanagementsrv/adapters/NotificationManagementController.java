package cas735.msad.notificationmanagementsrv.adapters;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cas735.msad.notificationmanagementsrv.MessagingConfiguration;
import cas735.msad.notificationmanagementsrv.business.NotificationProcessor;
import cas735.msad.notificationmanagementsrv.dto.NotificationCommand;
import cas735.msad.notificationmanagementsrv.dto.NotificationSent;
import cas735.msad.notificationmanagementsrv.ports.NotificationService;

@Component
@Slf4j
public class NotificationManagementController {

    private final NotificationService notificationService;

    @Autowired
    public NotificationManagementController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = MessagingConfiguration.QUEUE_NAME_NOTIFICATION, durable = "true"),
            exchange = @Exchange(
                    value = MessagingConfiguration.EXCHANGE_NAME_NOTIFICATION, ignoreDeclarationExceptions = "true"),
                    key = MessagingConfiguration.ROUTING_KEY_NOTIFICATION))
    public void receive(NotificationCommand notification) {
        try {
            log.info("Receiving notification");
            log.info("Reading notification: '" + notification + "'");
            notificationService.notify(notification);
        } catch (Exception exception) {
            log.info("Error receiving notification");
            log.info(exception.toString());
        }
        
    }

}
