package cas735.msad.adminmanagementsrv.ports;

import cas735.msad.adminmanagementsrv.business.entities.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationMonitoringRepository extends JpaRepository<Location, Integer>  {
    
}
