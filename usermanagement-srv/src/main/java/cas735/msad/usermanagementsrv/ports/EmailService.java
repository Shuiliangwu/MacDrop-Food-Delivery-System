package cas735.msad.usermanagementsrv.ports;

import cas735.msad.usermanagementsrv.dto.EmailRequest;

public interface EmailService {
    void send(EmailRequest req);
}
