package cas735.msad.usermanagementsrv.ports;

import org.springframework.data.repository.CrudRepository;

import cas735.msad.usermanagementsrv.business.entities.Admin;

public interface AdminRepository extends CrudRepository<Admin, Integer>{
    
}
