package cas735.msad.ordermanagementsrv.dto.response;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value @Builder
public class OrderManagementResponse {
    @NonNull Integer statusCode;
    @NonNull String response;
}
