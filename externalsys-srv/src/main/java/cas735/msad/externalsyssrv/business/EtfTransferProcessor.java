package cas735.msad.externalsyssrv.business;

import cas735.msad.externalsyssrv.dto.EtfTransferSent;
import cas735.msad.externalsyssrv.dto.EtfTransferCommand;
import cas735.msad.externalsyssrv.ports.EtfTransferService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EtfTransferProcessor implements EtfTransferService{
    private static final int SUCCESSCODE = 200;

    @Override
    public EtfTransferSent etfSend(EtfTransferCommand request) {
        log.info("*** Processing ETF transfer request ***");
        log.info("* - from:    " + request.getFrom());
        log.info("* - to:      " + request.getTo());
        log.info("* - amount:  " + request.getAmount());
        log.info("* - Message: " + request.getMessage());
        log.info("*** Ending ETF transfer request ***");
        return new EtfTransferSent(SUCCESSCODE, "ETf transfer to " + request.getTo() + " is complete!");
    }
}
