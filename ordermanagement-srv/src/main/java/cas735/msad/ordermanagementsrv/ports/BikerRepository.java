package cas735.msad.ordermanagementsrv.ports;

import org.springframework.data.repository.CrudRepository;

import cas735.msad.ordermanagementsrv.business.entities.Biker;

public interface BikerRepository extends CrudRepository<Biker, Integer>{
    
}
