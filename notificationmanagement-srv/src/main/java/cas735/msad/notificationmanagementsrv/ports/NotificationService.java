package cas735.msad.notificationmanagementsrv.ports;

import cas735.msad.notificationmanagementsrv.dto.NotificationCommand;
import cas735.msad.notificationmanagementsrv.dto.NotificationSent;

public interface NotificationService {
    NotificationSent notify(NotificationCommand notification);
}
