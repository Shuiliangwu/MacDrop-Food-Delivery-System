package cas735.msad.adminmanagementsrv.ports;

import cas735.msad.adminmanagementsrv.dto.EmailRequest;

public interface EmailService {
    void send(EmailRequest req);
}
