package cas735.msad.cartmanagementsrv.ports;

import cas735.msad.cartmanagementsrv.business.entities.BookStoreCart;
import cas735.msad.cartmanagementsrv.business.entities.BookStoreCartItem;
import org.springframework.data.repository.CrudRepository;

import com.redislabs.modules.rejson.Path;

public interface BookStoreCartRepository extends CrudRepository<BookStoreCart, String>{
  String getKey(BookStoreCart bookStoreCart);
  String getKey(String id);
  void addToCart(String bookStoreCartKey, Path cartItemsPath, BookStoreCartItem item);
  void removeFromCart(String bookStoreCartKey, Class<BookStoreCartItem> bookStoreCartItemClass, Path cartItemsPath, Long index);
}
