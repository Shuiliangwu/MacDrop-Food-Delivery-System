package cas735.msad.accountingbillingsrv.dto;

import lombok.*;

import java.io.Serializable;

@ToString
@Getter @Setter @NoArgsConstructor
public class EtfTransferResponse implements Serializable{
    int etfTransferStatusCode;
    String BankingServerResponse; 
}
