package cas735.msad.ordermanagementsrv.dto;

import lombok.*;

@ToString
@Getter @Setter @NoArgsConstructor
public class NotificationSent{
    @NonNull Integer notificationStatusCode;
    @NonNull String notificationServiceResponse;
}
