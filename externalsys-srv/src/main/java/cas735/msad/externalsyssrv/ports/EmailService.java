package cas735.msad.externalsyssrv.ports;

import cas735.msad.externalsyssrv.dto.SendEmailCommand;
import cas735.msad.externalsyssrv.dto.EmailSent;

public interface EmailService {
    EmailSent send(SendEmailCommand request);
}
