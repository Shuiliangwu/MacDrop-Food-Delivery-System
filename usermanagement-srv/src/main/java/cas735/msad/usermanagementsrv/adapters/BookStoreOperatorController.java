package cas735.msad.usermanagementsrv.adapters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;  
import org.springframework.web.bind.annotation.RequestBody;  
import org.springframework.web.bind.annotation.RestController;

import cas735.msad.usermanagementsrv.business.entities.Bookstoreoperator;
import cas735.msad.usermanagementsrv.ports.BookStoreOperatorRegistrationService;
import cas735.msad.usermanagementsrv.dto.response.RoleCreationResponse;

@RestController
public class BookStoreOperatorController {
    
    public final BookStoreOperatorRegistrationService bookStoreOperatorRegistrationService;

    @Autowired
    public BookStoreOperatorController(BookStoreOperatorRegistrationService bookStoreOperatorRegistrationService){
        this.bookStoreOperatorRegistrationService = bookStoreOperatorRegistrationService;
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
    @PostMapping("/bookstoreoperator")
    private RoleCreationResponse createBookStoreOperator(@RequestBody Bookstoreoperator bookStoreOperator){
        return bookStoreOperatorRegistrationService.createBookStoreOperator(bookStoreOperator);
    }
}
