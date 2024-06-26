package cas735.msad.menumanagementsrv.business.entities;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Configuration
@Component
@ComponentScan
public class RedisConfig {

    @Getter
    @Value("${spring.redis.host}")
    private String host;

    @Getter
    @Value("${spring.redis.port}")
    private String port;
    
}