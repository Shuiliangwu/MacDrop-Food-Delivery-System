package cas735.msad.adminmanagementsrv.ports;

import cas735.msad.adminmanagementsrv.business.entities.Bookstoreorder;
import cas735.msad.adminmanagementsrv.business.entities.Foodorder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.List;

public interface FinancialBookStoreOrderRepository extends JpaRepository<Bookstoreorder, Integer> {


    List<Bookstoreorder> findByOrdertimeBetween(String fromTime, String toTime);

    List<Bookstoreorder> findByOrdertimeBetweenAndBookstoreoperatorid(String fromTime, String toTime, String id);

}
