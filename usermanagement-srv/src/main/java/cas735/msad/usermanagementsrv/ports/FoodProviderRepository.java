package cas735.msad.usermanagementsrv.ports;

import org.springframework.data.repository.CrudRepository;

import cas735.msad.usermanagementsrv.business.entities.Foodprovider;

public interface FoodProviderRepository extends CrudRepository<Foodprovider, Integer>{
    
}
