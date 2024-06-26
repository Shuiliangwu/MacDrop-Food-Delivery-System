package cas735.msad.adminmanagementsrv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@SpringBootApplication
@EnableEurekaClient
@EnableSwagger2
@EnableWebMvc
public class AdminmanagementSrvApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdminmanagementSrvApplication.class, args);
	}

}

