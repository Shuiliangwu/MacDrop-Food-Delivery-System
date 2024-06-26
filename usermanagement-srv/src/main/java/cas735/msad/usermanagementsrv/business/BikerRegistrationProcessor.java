package cas735.msad.usermanagementsrv.business;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cas735.msad.usermanagementsrv.business.entities.Biker;
import cas735.msad.usermanagementsrv.dto.EmailRequest;
import cas735.msad.usermanagementsrv.dto.response.RoleCreationResponse;
import cas735.msad.usermanagementsrv.ports.BikerRegistrationService;
import cas735.msad.usermanagementsrv.ports.BikerRepository;
import cas735.msad.usermanagementsrv.ports.EmailService;

@Service
public class BikerRegistrationProcessor implements BikerRegistrationService{
    private final EmailService email;
    private final BikerRepository bikerRepository;

    @Autowired
    public BikerRegistrationProcessor(EmailService email, BikerRepository bikerRepository) {
        this.email = email;
        this.bikerRepository = bikerRepository;
    }

    @Override
    public RoleCreationResponse createBiker(Biker biker) { 
        try {
            bikerRepository.save(biker);
            EmailRequest req = EmailRequest.builder()
                    .from("macdropadminoffice@mcmaster.ca")
                    .to(biker.getEtfemail()) 
                    .object("Biker Registration on MacDrop")
                    .message("Biker registered successfully! " + "Your username is " + biker.getUsername())
                    .build();
            email.send(req);
            return RoleCreationResponse.builder().statusCode(200).response("Created biker successfully!").build();
        } catch (Exception e) {
            return RoleCreationResponse.builder().statusCode(500).response("Failed to create biker!").build();
        }
    }    
}
