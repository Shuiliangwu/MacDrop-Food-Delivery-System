package cas735.msad.menumanagementsrv.ports;

import cas735.msad.menumanagementsrv.dto.EmailRequest;

public interface EmailService {
    void send(EmailRequest req);
}