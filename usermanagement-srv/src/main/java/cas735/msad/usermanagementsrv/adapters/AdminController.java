package cas735.msad.usermanagementsrv.adapters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;  
import org.springframework.web.bind.annotation.RequestBody;  
import org.springframework.web.bind.annotation.RestController;

import cas735.msad.usermanagementsrv.business.entities.Admin;
import cas735.msad.usermanagementsrv.dto.response.RoleCreationResponse;
import cas735.msad.usermanagementsrv.ports.AdminRegistrationService;

@RestController
public class AdminController {
    public final AdminRegistrationService adminRegistrationService;

    @Autowired
    public AdminController(AdminRegistrationService adminRegistrationService){
        this.adminRegistrationService = adminRegistrationService;
    }

    /*
     * test json payload
     * {  
    "username": "hong",  
    "password": "%$#$%$%$%$%",  
    "firstname": "hong",  
    "lastname": "li",
    "etfemail": "xxx@xxx"
    }   
     */
    @PostMapping("/admin")
    private RoleCreationResponse createAdmin(@RequestBody Admin admin){
        return adminRegistrationService.createAdmin(admin);
    }
}
