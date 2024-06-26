package cas735.msad.accountingbillingsrv.ports;

import org.springframework.data.repository.CrudRepository;
import cas735.msad.accountingbillingsrv.business.entities.Bookstoreoperator;

public interface BookStoreOperatorRepository extends CrudRepository<Bookstoreoperator, Integer>{
    
}
