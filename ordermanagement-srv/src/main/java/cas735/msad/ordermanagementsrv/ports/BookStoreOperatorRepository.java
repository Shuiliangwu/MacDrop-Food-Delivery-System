package cas735.msad.ordermanagementsrv.ports;

import org.springframework.data.repository.CrudRepository;

import cas735.msad.ordermanagementsrv.business.entities.Bookstoreoperator;

public interface BookStoreOperatorRepository extends CrudRepository<Bookstoreoperator, Integer>{
    
}
