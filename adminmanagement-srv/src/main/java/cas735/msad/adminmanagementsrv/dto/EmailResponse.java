package cas735.msad.adminmanagementsrv.dto;

import lombok.*;

@ToString
@Getter @Setter @NoArgsConstructor
public class EmailResponse {
    @NonNull Integer smtpStatusCode;
    @NonNull String smtpServerResponse;
}

