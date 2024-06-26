package cas735.msad.ordermanagementsrv.ports;

import cas735.msad.ordermanagementsrv.dto.EmailRequest;

public interface EmailService {
    void send(EmailRequest req);
}