package cas735.msad.ordermanagementsrv.ports;

import org.springframework.data.repository.CrudRepository;

import cas735.msad.ordermanagementsrv.business.entities.Location;

public interface LocationRepository extends CrudRepository<Location, Integer>{
    
}
