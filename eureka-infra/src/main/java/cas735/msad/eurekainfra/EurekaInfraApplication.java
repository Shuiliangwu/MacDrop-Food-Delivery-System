package cas735.msad.eurekainfra;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaInfraApplication {

	public static void main(String[] args) {
		SpringApplication.run(EurekaInfraApplication.class, args);
	}

}
