package cas735.msad.externalsyssrv.adapters;

import cas735.msad.externalsyssrv.dto.EtfTransferSent;
import cas735.msad.externalsyssrv.dto.EtfTransferCommand;
import cas735.msad.externalsyssrv.ports.EtfTransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/etfpayment", produces = "application/json")
public class EtfTransferController {
    private final EtfTransferService etfTransferService;

    @Autowired
    public EtfTransferController(EtfTransferService etfTransferService) {
        this.etfTransferService = etfTransferService;
    }

    @PostMapping
    public EtfTransferSent process(@RequestBody EtfTransferCommand request) {
        return etfTransferService.etfSend(request);
    }
}
