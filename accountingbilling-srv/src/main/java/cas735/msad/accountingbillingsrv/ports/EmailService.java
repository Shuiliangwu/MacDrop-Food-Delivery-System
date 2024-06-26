package cas735.msad.accountingbillingsrv.ports;

import cas735.msad.accountingbillingsrv.dto.EmailRequest;

public interface EmailService {
    void send(EmailRequest req);
}
