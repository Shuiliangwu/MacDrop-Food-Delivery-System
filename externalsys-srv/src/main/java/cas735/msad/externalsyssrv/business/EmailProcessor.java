package cas735.msad.externalsyssrv.business;

import cas735.msad.externalsyssrv.dto.EmailSent;
import cas735.msad.externalsyssrv.dto.SendEmailCommand;
import cas735.msad.externalsyssrv.ports.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailProcessor implements EmailService {

    private static final int SUCCESSCODE = 250;

    @Override
    public EmailSent send(SendEmailCommand request) {
        log.info("*** Processing email request ***");
        log.info("* - from:    " + request.getFrom());
        log.info("* - to:      " + request.getTo());
        log.info("* - Object:  " + request.getObject());
        log.info("* - Message: " + request.getMessage());
        log.info("*** Ending email request ***");
        return new EmailSent(SUCCESSCODE, "OK");
    }
}
