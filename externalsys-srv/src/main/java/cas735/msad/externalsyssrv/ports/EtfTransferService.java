package cas735.msad.externalsyssrv.ports;

import cas735.msad.externalsyssrv.dto.EtfTransferCommand;
import cas735.msad.externalsyssrv.dto.EtfTransferSent;

public interface EtfTransferService {
    EtfTransferSent etfSend(EtfTransferCommand request);
}
