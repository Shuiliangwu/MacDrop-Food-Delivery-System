package cas735.msad.usermanagementsrv.ports;

import cas735.msad.usermanagementsrv.business.entities.Admin;
import cas735.msad.usermanagementsrv.dto.response.RoleCreationResponse;

public interface AdminRegistrationService {
    RoleCreationResponse createAdmin(Admin admin);
}
