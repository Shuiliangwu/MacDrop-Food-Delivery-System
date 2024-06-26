package cas735.msad.adminmanagementsrv.dto;

import lombok.*;

import java.io.Serializable;

@ToString
@Getter @Setter @NoArgsConstructor
public class EtfTransferResponse implements Serializable{
    int etfTransferStatusCode;
    String BankingServerResponse;
}


