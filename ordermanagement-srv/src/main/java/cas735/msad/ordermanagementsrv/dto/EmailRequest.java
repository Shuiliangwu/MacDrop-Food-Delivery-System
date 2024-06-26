package cas735.msad.ordermanagementsrv.dto;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;


@Value @Builder
public class EmailRequest {
    @NonNull String from;
    @NonNull String to;
    @NonNull String object;
    @NonNull String message;
}