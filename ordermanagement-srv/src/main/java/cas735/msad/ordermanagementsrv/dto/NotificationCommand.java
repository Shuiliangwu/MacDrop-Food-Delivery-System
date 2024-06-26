package cas735.msad.ordermanagementsrv.dto;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value @Builder
public class NotificationCommand {
    @NonNull String from;
    @NonNull String to;
    @NonNull String message;
}
