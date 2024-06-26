package cas735.msad.usermanagementsrv.adapters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;  
import org.springframework.web.bind.annotation.RequestBody;  
import org.springframework.web.bind.annotation.RestController;

import cas735.msad.usermanagementsrv.business.entities.Foodprovider;
import cas735.msad.usermanagementsrv.ports.FoodProviderRegistrationService;
import cas735.msad.usermanagementsrv.dto.response.RoleCreationResponse;

@RestController
public class FoodProviderController {
    public final FoodProviderRegistrationService foodProviderRegistrationService;

    @Autowired
    public FoodProviderController(FoodProviderRegistrationService foodProviderRegistrationService){
        this.foodProviderRegistrationService = foodProviderRegistrationService;
    }

    /*
     * test json payload for creating biker
     * {  
    "username": "hong",  
    "password": "%$#$%$%$%$%",  
    "firstname": "hong",  
    "lastname": "li",
    "etfemail": "xxx@xxx"
    }   
     */

    @PostMapping("/foodprovider")
    private RoleCreationResponse createFoodProvider(@RequestBody Foodprovider foodprovider){
        return foodProviderRegistrationService.createFoodProvider(foodprovider);
    }
}
