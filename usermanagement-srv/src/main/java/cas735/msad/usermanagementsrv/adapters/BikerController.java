package cas735.msad.usermanagementsrv.adapters;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;  
import org.springframework.web.bind.annotation.RequestBody;  
import org.springframework.web.bind.annotation.RestController;

import cas735.msad.usermanagementsrv.business.entities.Biker;
import cas735.msad.usermanagementsrv.dto.response.RoleCreationResponse;
import cas735.msad.usermanagementsrv.ports.BikerRegistrationService;

@RestController
public class BikerController {

    public final BikerRegistrationService bikerRegistrationService;

    @Autowired
    public BikerController(BikerRegistrationService bikerRegistrationService){
        this.bikerRegistrationService = bikerRegistrationService;
    }


    /*
     * test json
     * {  
    "username": "hong",  
    "password": "%$#$%$%$%$%",  
    "firstname": "hong",  
    "lastname": "li",
    "currentworkload": "1",
    "historyworkload": "2",
    "etfemail": "xxx@xxx"
    }   
     */
    @PostMapping("/biker")
    private RoleCreationResponse createBiker(@RequestBody Biker biker){
        return bikerRegistrationService.createBiker(biker);
    }
}
