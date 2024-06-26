package cas735.msad.externalsyssrv.adapters;

import cas735.msad.externalsyssrv.dto.EmailSent;
import cas735.msad.externalsyssrv.dto.SendEmailCommand;
import cas735.msad.externalsyssrv.ports.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/mailbox", produces = "application/json")
public class EmailController {
    private final EmailService businessLogic;

    @Autowired
    public EmailController(EmailService businessLogic) {
        this.businessLogic = businessLogic;
    }

    @PostMapping
    public EmailSent process(@RequestBody SendEmailCommand request) {
        return businessLogic.send(request);
    }

}

