package cas735.msad.notificationmanagementsrv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;

import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableEurekaClient
@EnableRabbit
@EnableSwagger2
@EnableWebMvc
public class NotificationmanagementSrvApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotificationmanagementSrvApplication.class, args);
	}

	@Bean
	public Docket productApi() {
		return new Docket(DocumentationType.SWAGGER_2).select()
		   .apis(RequestHandlerSelectors.basePackage("cas735.msad.notificationmanagementsrv")).build();
	 }

}
