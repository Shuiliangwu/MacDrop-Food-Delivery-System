package cas735.msad.usermanagementsrv.adapters;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;

import cas735.msad.usermanagementsrv.dto.EmailRequest;
import cas735.msad.usermanagementsrv.dto.EmailResponse;
import cas735.msad.usermanagementsrv.ports.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;

import java.util.Objects;
import java.util.Random;

@Component
@Slf4j
public class EmailClient implements EmailService{
    
    private static final String APPNAME = "EXTERNALSERVICE";
    private static final String ENDPOINT = "/mailbox";
    private final EurekaClient registry;

    @Autowired
    public EmailClient(EurekaClient registry) {
        this.registry = registry;
    }

    @Override
    public void send(EmailRequest req) {
        log.info("** Preparing sending email");
        try {
            WebClient webClient = buildClient();
            EmailResponse response = webClient.post()
                    .uri(ENDPOINT)
                    .body(BodyInserters.fromValue(req))
                    .retrieve()
                    .bodyToMono(EmailResponse.class)
                    .block();
            log.info("** Response: " + response);
            log.info("** Email Sent");
        }
        catch (IllegalStateException ex) {
            log.error("No email service available!");
        }
        catch(WebClientException ex) {
            log.error("Communication Error while sending email");
            log.error(ex.toString());
        }
    }

    private WebClient buildClient() {
        String url = locateExternalService();
        log.info("** Using instance: " + url);
        return WebClient.builder()
                .baseUrl(url)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    private String locateExternalService() {
        Application candidates = registry.getApplication(APPNAME);
        if (Objects.isNull(candidates)) { // no email service in the registry
            throw new IllegalStateException();
        }
        Random rand = new Random();
        InstanceInfo infos = // Randomly picking one email service among candidates
                candidates.getInstances().get(rand.nextInt(candidates.size()));
        return "http://"+infos.getIPAddr()+":"+infos.getPort();
    }


}
