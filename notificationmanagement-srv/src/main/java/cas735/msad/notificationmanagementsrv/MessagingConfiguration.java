package cas735.msad.notificationmanagementsrv;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessagingConfiguration {

    // public static final String QUEUE_NAME = "accountingbillingqueue";
    public static final String QUEUE_NAME_NOTIFICATION = "notificationmanagementqueue";
    public static final String ROUTING_KEY_NOTIFICATION = "notificationmanagement";
    public static final String EXCHANGE_NAME_NOTIFICATION = "amq.direct";

    @Bean
    public MessageConverter converter(){
        return new Jackson2JsonMessageConverter();
    }

}
