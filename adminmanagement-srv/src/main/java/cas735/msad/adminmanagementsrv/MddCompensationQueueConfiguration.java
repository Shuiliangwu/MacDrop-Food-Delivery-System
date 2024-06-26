package cas735.msad.adminmanagementsrv;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MddCompensationQueueConfiguration {
    public static final String QUEUE_NAME_MDDCOMPENSATION = "mddcompensationqueue";
    public static final String ROUTING_MDDCOMPENSATION = "mddcompensation";
    public static final String EXCHANGE_NAME_MDDCOMPENSATION = "amq.direct";

    @Bean
    public Jackson2JsonMessageConverter converter(){
        return new Jackson2JsonMessageConverter();
    }

}
