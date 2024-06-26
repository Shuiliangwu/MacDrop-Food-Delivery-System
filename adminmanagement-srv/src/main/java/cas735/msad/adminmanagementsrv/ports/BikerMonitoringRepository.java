package cas735.msad.adminmanagementsrv.ports;

import cas735.msad.adminmanagementsrv.business.entities.Biker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BikerMonitoringRepository extends JpaRepository<Biker, Integer>  {
    
}
