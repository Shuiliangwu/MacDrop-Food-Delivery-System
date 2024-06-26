package cas735.msad.cartmanagementsrv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;
import java.util.Set;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.DirectExchange;

@SpringBootApplication
@EnableEurekaClient
@EnableSwagger2
@EnableWebMvc
public class CartmanagementSrvApplication {
	@Bean
	DirectExchange exchange() {
	  return new DirectExchange("amq.direct");
	}

	//Initialize and bind accountingbillingbookstorecartqueue
	@Bean
	Queue queueAccountingbillingbookstorecart() {
		return new Queue("accountingbillingbookstorecartqueue", true);
	}
	@Bean
	Binding bindingAccountingbillingbookstorecart() {
	  return BindingBuilder.bind(queueAccountingbillingbookstorecart()).to(exchange()).with("accountingbillingbookstorecart");
	}

	//Initialize and bind accountingbillingfoodcartqueue
	@Bean
	Queue queueAccountingbillingfoodcart() {
		return new Queue("accountingbillingfoodcartqueue", true);
	}
	@Bean
	Binding bindingAccountingbillingfoodcart() {
	  return BindingBuilder.bind(queueAccountingbillingfoodcart()).to(exchange()).with("accountingbillingfoodcart");
	}

	//Initialize and bind bookstoreordermanagementqueue	
	@Bean
	Queue queueBookstoreordermanagement() {
		return new Queue("bookstoreordermanagementqueue", true);
	}
	@Bean
	Binding bindingBookstoreordermanagement() {
	  return BindingBuilder.bind(queueBookstoreordermanagement()).to(exchange()).with("bookstoreordermanagement");
	}

	//Initialize and bind foodordermanagementqueue
	@Bean
	Queue queueFoodordermanagement() {
		return new Queue("foodordermanagementqueue", true);
	}
	@Bean
	Binding bindingFoodordermanagement() {
	  return BindingBuilder.bind(queueFoodordermanagement()).to(exchange()).with("foodordermanagement");
	}

	//Initialize and bind notificationmanagementqueue
	@Bean
	Queue queueNotificationmanagement() {
		return new Queue("notificationmanagementqueue", true);
	}
	@Bean
	Binding bindingNotificationmanagement() {
	  return BindingBuilder.bind(queueNotificationmanagement()).to(exchange()).with("notificationmanagement");
	}

	//Initialize and bind mddcompensationqueue
	@Bean
	Queue queueMddcompensation() {
		return new Queue("mddcompensationqueue", true);
	}
	@Bean
	Binding bindingMddcompensation() {
	  return BindingBuilder.bind(queueMddcompensation()).to(exchange()).with("mddcompensation");
	}

	public static void main(String[] args) {
		SpringApplication.run(CartmanagementSrvApplication.class, args);
	}

	@Bean
	public Docket productApi() {
		return new Docket(DocumentationType.SWAGGER_2).select()
		   .apis(RequestHandlerSelectors.basePackage("cas735.msad.cartmanagementsrv")).build();
	 }
}
