package cas735.msad.accountingbillingsrv.ports;

import cas735.msad.accountingbillingsrv.dto.BookStoreOrderList;

public interface BookStoreOrderManagementService {
    void createBookStoreOrder(BookStoreOrderList bookStoreOrderList);
}
