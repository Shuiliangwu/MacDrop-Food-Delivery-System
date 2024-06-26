package cas735.msad.cartmanagementsrv.ports;

import cas735.msad.cartmanagementsrv.business.entities.FoodCart;
import cas735.msad.cartmanagementsrv.business.entities.FoodCartItem;

import org.springframework.data.repository.CrudRepository;

import com.redislabs.modules.rejson.Path;

public interface FoodCartRepository extends CrudRepository<FoodCart, String>{
  String getKey(FoodCart foodCart);
  String getKey(String id);
  void addToCart(String foodCartKey, Path cartItemsPath, FoodCartItem item);
  void removeFromCart(String foodCartKey, Class<FoodCartItem> foodCartItemClass, Path cartItemsPath, Long index);
}
