package cas735.msad.cartmanagementsrv.ports;

import cas735.msad.cartmanagementsrv.business.entities.FoodCart;
import cas735.msad.cartmanagementsrv.business.entities.BookStoreCart;

public interface AccountingBillingService {
    void sendFoodCartToAccountingBilling(FoodCart foodCart);
    void sendBookStoreCartToAccountingBilling(BookStoreCart bookStoreCart);
}
