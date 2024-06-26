package cas735.msad.accountingbillingsrv.adapters;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;

import cas735.msad.accountingbillingsrv.dto.EtfTransferRequest;
import cas735.msad.accountingbillingsrv.dto.EtfTransferResponse;
import cas735.msad.accountingbillingsrv.ports.EtfTransferService;
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
public class EtfTransferClient implements EtfTransferService{
    private static final String APPNAME = "EXTERNALSERVICE";
    private static final String ENDPOINT = "/etfpayment";
    private final EurekaClient registry;

    @Autowired
    public EtfTransferClient(EurekaClient registry) {
        this.registry = registry;
    }

    @Override
    public void etfTransfer(EtfTransferRequest req) {
        log.info("** Preparing transferring money");
        try {
            WebClient webClient = buildClient();
            EtfTransferResponse response = webClient.post()
                    .uri(ENDPOINT)
                    .body(BodyInserters.fromValue(req))
                    .retrieve()
                    .bodyToMono(EtfTransferResponse.class)
                    .block();
            log.info("** Response: " + response);
            log.info("** Money transferred");
        }
        catch (IllegalStateException ex) {
            log.error("No ETF payment service available!");
        }
        catch(WebClientException ex) {
            log.error("Communication Error while transferring money");
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
