package cas735.msad.usermanagementsrv.ports;

import org.springframework.data.repository.CrudRepository;

import cas735.msad.usermanagementsrv.business.entities.Biker;

public interface BikerRepository extends CrudRepository<Biker, Integer>{
    
}
