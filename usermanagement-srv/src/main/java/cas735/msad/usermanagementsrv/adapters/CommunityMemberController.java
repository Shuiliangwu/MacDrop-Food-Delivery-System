package cas735.msad.usermanagementsrv.adapters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;  
import org.springframework.web.bind.annotation.RequestBody;  
import org.springframework.web.bind.annotation.RestController;

import cas735.msad.usermanagementsrv.business.entities.Communitymember;
import cas735.msad.usermanagementsrv.ports.CommunityMemberRegistrationService;
import cas735.msad.usermanagementsrv.dto.response.RoleCreationResponse;

@RestController
public class CommunityMemberController {

    public final CommunityMemberRegistrationService communityMemberRegistrationService;

    @Autowired
    public CommunityMemberController(CommunityMemberRegistrationService communityMemberRegistrationService){
        this.communityMemberRegistrationService = communityMemberRegistrationService;
    }


    /*
     * test json payload
     * {  
    "username": "hong",  
    "password": "%$#$%$%$%$%",  
    "firstname": "hong",  
    "lastname": "li",
    "currentmdd": "1",
    "etfemail": "xxx@xxx"
    }   
     */
    @PostMapping("/communitymember")
    private RoleCreationResponse createCommunityMember(@RequestBody Communitymember communityMember){
        return communityMemberRegistrationService.createCommunityMember(communityMember);
    }
}
