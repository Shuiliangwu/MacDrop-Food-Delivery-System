package cas735.msad.cartmanagementsrv.ports;

import cas735.msad.cartmanagementsrv.business.entities.FoodCart;
import cas735.msad.cartmanagementsrv.business.entities.FoodCartItem;
import cas735.msad.cartmanagementsrv.dto.CartResponse;

public interface FoodCartService {
    FoodCart get(String id);
    CartResponse addToFoodCart(String id, FoodCartItem item);
    CartResponse removeFromFoodCart(String id, String foodId);
    CartResponse checkoutFoodCart(String id);
}
