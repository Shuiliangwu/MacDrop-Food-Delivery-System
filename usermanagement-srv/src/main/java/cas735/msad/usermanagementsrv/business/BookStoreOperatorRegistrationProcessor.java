package cas735.msad.usermanagementsrv.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cas735.msad.usermanagementsrv.business.entities.Bookstoreoperator;
import cas735.msad.usermanagementsrv.dto.EmailRequest;
import cas735.msad.usermanagementsrv.dto.response.RoleCreationResponse;
import cas735.msad.usermanagementsrv.ports.BookStoreOperatorRegistrationService;
import cas735.msad.usermanagementsrv.ports.BookStoreOperatorRepository;
import cas735.msad.usermanagementsrv.ports.EmailService;

@Service
public class BookStoreOperatorRegistrationProcessor implements BookStoreOperatorRegistrationService{
    private final EmailService email;
    private final BookStoreOperatorRepository bookStoreOperatorRepository;

    @Autowired
    public BookStoreOperatorRegistrationProcessor(EmailService email, BookStoreOperatorRepository bookStoreOperatorRepository) {
        this.email = email;
        this.bookStoreOperatorRepository = bookStoreOperatorRepository;
    }

    @Override
    public RoleCreationResponse createBookStoreOperator(Bookstoreoperator bookStoreOperator) {
        try {
            bookStoreOperatorRepository.save(bookStoreOperator);
            EmailRequest req = EmailRequest.builder()
                    .from("macdropadminoffice@mcmaster.ca")
                    .to(bookStoreOperator.getEtfemail()) 
                    .object("Book Store Operator Registration on MacDrop")
                    .message("Book store operator registered successfully! " + "Your username is " + bookStoreOperator.getUsername())
                    .build();
            email.send(req); 
            return RoleCreationResponse.builder().statusCode(200).response("Created bookstoreoperator successfully!").build();
        } catch (Exception e) {
            return RoleCreationResponse.builder().statusCode(500).response("Failed to create bookstoreoperator successfully!").build();
        }

    }
    
}
