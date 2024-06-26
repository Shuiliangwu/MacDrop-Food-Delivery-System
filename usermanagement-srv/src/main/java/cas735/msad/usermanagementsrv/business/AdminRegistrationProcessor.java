package cas735.msad.usermanagementsrv.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cas735.msad.usermanagementsrv.business.entities.Admin;
import cas735.msad.usermanagementsrv.dto.EmailRequest;
import cas735.msad.usermanagementsrv.dto.response.RoleCreationResponse;
import cas735.msad.usermanagementsrv.ports.AdminRegistrationService;
import cas735.msad.usermanagementsrv.ports.AdminRepository;
import cas735.msad.usermanagementsrv.ports.EmailService;

@Service
public class AdminRegistrationProcessor implements AdminRegistrationService{
    private final EmailService email;
    private final AdminRepository adminRepository;

    @Autowired
    public AdminRegistrationProcessor(EmailService email, AdminRepository adminRepository) {
        this.email = email;
        this.adminRepository = adminRepository;
    }

    @Override
    public RoleCreationResponse createAdmin(Admin admin) {
        try{
            adminRepository.save(admin);
            EmailRequest req = EmailRequest.builder()
                    .from("macdropadminoffice@mcmaster.ca")
                    .to(admin.getEtfemail()) 
                    .object("Administrator Registration on MacDrop")
                    .message("Administrator registered successfully! " + "Your username is " + admin.getUsername())
                    .build();
            email.send(req);
            return RoleCreationResponse.builder().statusCode(200).response("Created administrator successfully!").build();
        } catch(Exception e) {
            return RoleCreationResponse.builder().statusCode(500).response("Failed to create administrator!").build();
        }

    }
    
}
