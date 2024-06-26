package cas735.msad.usermanagementsrv.ports;

import cas735.msad.usermanagementsrv.business.entities.Communitymember;
import cas735.msad.usermanagementsrv.dto.response.RoleCreationResponse;

public interface CommunityMemberRegistrationService {
    RoleCreationResponse createCommunityMember(Communitymember biker);
}
