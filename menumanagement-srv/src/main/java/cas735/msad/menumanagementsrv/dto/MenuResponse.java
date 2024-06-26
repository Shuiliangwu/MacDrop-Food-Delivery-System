package cas735.msad.menumanagementsrv.dto;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value @Builder
public class MenuResponse {
    @NonNull Integer statusCode;
    @NonNull String response;
}
