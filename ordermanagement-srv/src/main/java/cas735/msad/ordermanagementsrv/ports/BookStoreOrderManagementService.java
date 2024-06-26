package cas735.msad.ordermanagementsrv.ports;

import cas735.msad.ordermanagementsrv.dto.BookStoreOrderList;
import cas735.msad.ordermanagementsrv.dto.TimeRange;
import cas735.msad.ordermanagementsrv.dto.response.BookStoreOrderManagementResponse;

public interface BookStoreOrderManagementService {
    void createBookStoreOrder(BookStoreOrderList bookStoreOrderList);
    BookStoreOrderManagementResponse askForBookStoreCompensation(String bookStoreOperatorId, TimeRange timeRange);
}
