package cas735.msad.usermanagementsrv.ports;

import org.springframework.data.repository.CrudRepository;

import cas735.msad.usermanagementsrv.business.entities.Bookstoreoperator;

public interface BookStoreOperatorRepository extends CrudRepository<Bookstoreoperator, Integer>{
    
}
