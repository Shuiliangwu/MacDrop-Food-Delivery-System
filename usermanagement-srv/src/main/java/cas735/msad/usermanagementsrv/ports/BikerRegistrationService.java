package cas735.msad.usermanagementsrv.ports;
import java.util.List;

import cas735.msad.usermanagementsrv.business.entities.Biker;
import cas735.msad.usermanagementsrv.dto.response.RoleCreationResponse;

public interface BikerRegistrationService {
    RoleCreationResponse createBiker(Biker biker);
}
