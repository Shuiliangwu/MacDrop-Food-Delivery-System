package cas735.msad.usermanagementsrv.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cas735.msad.usermanagementsrv.business.entities.Foodprovider;
import cas735.msad.usermanagementsrv.dto.EmailRequest;
import cas735.msad.usermanagementsrv.dto.response.RoleCreationResponse;
import cas735.msad.usermanagementsrv.ports.EmailService;
import cas735.msad.usermanagementsrv.ports.FoodProviderRegistrationService;
import cas735.msad.usermanagementsrv.ports.FoodProviderRepository;

@Service
public class FoodProviderRegistrationProcessor implements FoodProviderRegistrationService{
    private final EmailService email;
    private final FoodProviderRepository foodProviderRepository;

    @Autowired
    public FoodProviderRegistrationProcessor(EmailService email, FoodProviderRepository foodProviderRepository) {
        this.email = email;
        this.foodProviderRepository = foodProviderRepository;
    }

    @Override
    public RoleCreationResponse createFoodProvider(Foodprovider foodprovider) {
        try {
            foodProviderRepository.save(foodprovider);
            EmailRequest req = EmailRequest.builder()
                    .from("macdropadminoffice@mcmaster.ca")
                    .to(foodprovider.getEtfemail()) 
                    .object("Food Provider Registration on MacDrop")
                    .message("Food provider registered successfully! " + "Your username is " + foodprovider.getUsername())
                    .build();
            email.send(req);
            return RoleCreationResponse.builder().statusCode(200).response("Created food provider successfully!").build();
        } catch (Exception e) {
            return RoleCreationResponse.builder().statusCode(500).response("Failed to create food provider successfully!").build();
        }
 
    }
    
}
