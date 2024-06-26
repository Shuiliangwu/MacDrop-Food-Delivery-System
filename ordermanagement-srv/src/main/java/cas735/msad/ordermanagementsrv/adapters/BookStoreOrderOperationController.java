package cas735.msad.ordermanagementsrv.adapters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;  
import org.springframework.web.bind.annotation.RequestBody;  
import org.springframework.web.bind.annotation.RestController;

import cas735.msad.ordermanagementsrv.ports.BookStoreOrderManagementService;
import cas735.msad.ordermanagementsrv.dto.response.BookStoreOrderManagementResponse;
import cas735.msad.ordermanagementsrv.dto.TimeRange;

@RestController
public class BookStoreOrderOperationController {
    public final BookStoreOrderManagementService bookStoreOrderManagementService;

    @Autowired
    public BookStoreOrderOperationController(BookStoreOrderManagementService bookStoreOrderManagementService){
        this.bookStoreOrderManagementService = bookStoreOrderManagementService;
    }

    @PostMapping("/bookstoreorderoperation/bookstoreoperator/mddcompensation/{bookstoreoperatorid}")
    private BookStoreOrderManagementResponse askForMddCompensation(@PathVariable("bookstoreoperatorid") String bookStoreOperatorId, @RequestBody TimeRange timeRange){
        return bookStoreOrderManagementService.askForBookStoreCompensation(bookStoreOperatorId, timeRange);
    }
}
