package cas735.msad.adminmanagementsrv.ports;

import cas735.msad.adminmanagementsrv.business.entities.Foodprovider;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodProviderRepository extends JpaRepository<Foodprovider, Integer> {
}
