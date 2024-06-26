package cas735.msad.usermanagementsrv.ports;

import cas735.msad.usermanagementsrv.business.entities.Bookstoreoperator;
import cas735.msad.usermanagementsrv.dto.response.RoleCreationResponse;

public interface BookStoreOperatorRegistrationService {
    RoleCreationResponse createBookStoreOperator(Bookstoreoperator bookStoreOperator);
}
