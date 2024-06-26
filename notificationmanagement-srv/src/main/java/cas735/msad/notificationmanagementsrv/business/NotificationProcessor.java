package cas735.msad.notificationmanagementsrv.business;

import cas735.msad.notificationmanagementsrv.ports.NotificationService;
import cas735.msad.notificationmanagementsrv.dto.NotificationCommand;
import cas735.msad.notificationmanagementsrv.dto.NotificationSent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificationProcessor implements NotificationService{

    private static final int SUCCESSCODE = 200;

    @Override
    public NotificationSent notify(NotificationCommand notification) {
        log.info("*** Starting notification ***");
        log.info("* - from:    " + notification.getFrom());
        log.info("* - to:      " + notification.getTo());
        log.info("* - Message: " + notification.getMessage());
        log.info("*** Ending notification ***");
        return new NotificationSent(SUCCESSCODE, "OK");
    }
}
