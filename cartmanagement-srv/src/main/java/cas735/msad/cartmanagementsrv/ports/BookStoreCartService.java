package cas735.msad.cartmanagementsrv.ports;

import cas735.msad.cartmanagementsrv.business.entities.BookStoreCart;
import cas735.msad.cartmanagementsrv.business.entities.BookStoreCartItem;
import cas735.msad.cartmanagementsrv.dto.CartResponse;

public interface BookStoreCartService {
    BookStoreCart get(String id);
    CartResponse addToBookStoreCart(String id, BookStoreCartItem item);
    CartResponse removeFromBookStoreCart(String id, String bookStoreItemId);
    CartResponse checkoutBookStoreCart(String id);
}
