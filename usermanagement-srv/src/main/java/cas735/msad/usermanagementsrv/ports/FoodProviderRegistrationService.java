package cas735.msad.usermanagementsrv.ports;

import cas735.msad.usermanagementsrv.business.entities.Foodprovider;
import cas735.msad.usermanagementsrv.dto.response.RoleCreationResponse;

public interface FoodProviderRegistrationService {
    RoleCreationResponse createFoodProvider(Foodprovider foodprovider);
}
