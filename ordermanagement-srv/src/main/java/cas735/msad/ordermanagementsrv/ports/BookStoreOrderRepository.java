package cas735.msad.ordermanagementsrv.ports;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import cas735.msad.ordermanagementsrv.business.entities.Bookstoreorder;

public interface BookStoreOrderRepository extends CrudRepository<Bookstoreorder, Integer>{

    @Query("select bookstoreorder from Bookstoreorder bookstoreorder where bookstoreorder.bookstoreoperatorid = ?1 And bookstoreorder.ordertime > ?2 And bookstoreorder.ordertime < ?3")
    List<Bookstoreorder> findByOrderTimeRange(String bookStoreOperatorId, String pastTime, String currentTime);
}
