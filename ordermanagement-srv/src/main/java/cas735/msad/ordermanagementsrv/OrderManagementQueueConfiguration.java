package cas735.msad.ordermanagementsrv;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderManagementQueueConfiguration {
    public static final String QUEUE_NAME_FOODORDER = "foodordermanagementqueue";
    public static final String ROUTING_FOODORDER = "foodordermanagement";
    public static final String EXCHANGE_NAME_FOODORDER = "amq.direct";

    public static final String QUEUE_NAME_BOOKSTOREORDER = "bookstoreordermanagementqueue";
    public static final String ROUTING_BOOKSTOREORDER = "bookstoreordermanagement";
    public static final String EXCHANGE_NAME_BOOKSTOREORDER = "amq.direct";

    public static final String ROUTING_NOTIFICATION = "notificationmanagement";
    public static final String EXCHANGE_NAME_NOTIFICATION = "amq.direct";

    public static final String QUEUE_NAME_MDDCOMPENSATION = "mddcompensationqueue";
    public static final String ROUTING_MDDCOMPENSATION = "mddcompensation";
    public static final String EXCHANGE_NAME_MDDCOMPENSATION = "amq.direct";
    @Bean
    public MessageConverter converter(){
        return new Jackson2JsonMessageConverter();
    }
   
}
