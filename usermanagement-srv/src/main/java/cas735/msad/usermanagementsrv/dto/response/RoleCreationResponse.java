package cas735.msad.usermanagementsrv.dto.response;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value @Builder
public class RoleCreationResponse {
    @NonNull Integer statusCode;
    @NonNull String response;
}
