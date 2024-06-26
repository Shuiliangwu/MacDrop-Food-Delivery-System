package cas735.msad.ordermanagementsrv.ports;

import cas735.msad.ordermanagementsrv.dto.NotificationCommand;

public interface NotificationService {
    void notify(NotificationCommand notification);
}
