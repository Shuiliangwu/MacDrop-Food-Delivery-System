package cas735.msad.ordermanagementsrv.dto;

import lombok.*;

@ToString
@Getter @Setter @NoArgsConstructor
public class EmailResponse {
    @NonNull Integer smtpStatusCode;
    @NonNull String smtpServerResponse;
}