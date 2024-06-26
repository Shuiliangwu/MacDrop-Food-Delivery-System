package cas735.msad.accountingbillingsrv;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AccountingBillingMessageQueueConfiguration {
    public static final String QUEUE_NAME_FOODCART = "accountingbillingfoodcartqueue";
    public static final String ROUTING_FOODCART = "accountingbillingfoodcart";
    public static final String EXCHANGE_NAME_FOODCART = "amq.direct";

    public static final String QUEUE_NAME_BOOKSTORECART = "accountingbillingbookstorecartqueue";
    public static final String ROUTING_BOOKSTORECART = "accountingbillingbookstorecart";
    public static final String EXCHANGE_NAME_BOOKSTORECART = "amq.direct";


    @Bean
    public MessageConverter converter(){
        return new Jackson2JsonMessageConverter();
    }
}
