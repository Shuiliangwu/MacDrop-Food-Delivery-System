package cas735.msad.adminmanagementsrv.ports;

import cas735.msad.adminmanagementsrv.business.entities.Foodorder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.List;

public interface FinancialFoodOrderRepository extends JpaRepository<Foodorder, Integer> {


    List<Foodorder> findByOrdertimeBetween(String fromTime, String toTime);
    List<Foodorder> findByOrdertimeBetweenAndFoodproviderid(String fromTime, String toTime, String id);

}
