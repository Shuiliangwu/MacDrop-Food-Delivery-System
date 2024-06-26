package cas735.msad.cartmanagementsrv.dto;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value @Builder
public class CartResponse {
    @NonNull Integer statusCode;
    @NonNull String response;
}
