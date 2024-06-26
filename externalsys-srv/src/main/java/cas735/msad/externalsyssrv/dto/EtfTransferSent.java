package cas735.msad.externalsyssrv.dto;

import lombok.Value;

import java.io.Serializable;

@Value
public class EtfTransferSent implements Serializable{
    int etfTransferStatusCode;
    String BankingServerResponse; 
}
