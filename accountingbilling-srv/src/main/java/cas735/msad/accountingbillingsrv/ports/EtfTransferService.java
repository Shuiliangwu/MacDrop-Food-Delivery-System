package cas735.msad.accountingbillingsrv.ports;

import cas735.msad.accountingbillingsrv.dto.EtfTransferRequest;

public interface EtfTransferService {
    void etfTransfer(EtfTransferRequest req);
}
