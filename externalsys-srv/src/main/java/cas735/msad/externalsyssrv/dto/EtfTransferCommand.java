package cas735.msad.externalsyssrv.dto;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.io.Serializable;

@Jacksonized 
@Builder
@Value
public class EtfTransferCommand implements Serializable{
    @NonNull String from;
    @NonNull String to;
    @NonNull String message;
    @NonNull String amount;
}
