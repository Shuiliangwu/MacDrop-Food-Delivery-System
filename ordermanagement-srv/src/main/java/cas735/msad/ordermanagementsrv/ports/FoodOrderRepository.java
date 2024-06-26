package cas735.msad.ordermanagementsrv.ports;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import cas735.msad.ordermanagementsrv.business.entities.Foodorder;

public interface FoodOrderRepository extends CrudRepository<Foodorder, Integer>{

    @Query("select foodorder from Foodorder foodorder where foodorder.ordertime > ?1 And foodorder.ordertime < ?2")
    List<Foodorder> findByOrderTimeRange(String pastTime, String currentTime);

    @Query("select foodorder from Foodorder foodorder where foodorder.status = ?1")
    List<Foodorder> findWaitingForPickUpOrders(String status);
}
