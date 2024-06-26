package cas735.msad.cartmanagementsrv.adapters;

import cas735.msad.cartmanagementsrv.business.entities.FoodCart;
import cas735.msad.cartmanagementsrv.business.entities.FoodCartItem;
import cas735.msad.cartmanagementsrv.ports.FoodCartService;
import cas735.msad.cartmanagementsrv.dto.CartResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class FoodCartController{
    public final FoodCartService foodCartService;

    @Autowired
    public FoodCartController(FoodCartService foodCartService){
        this.foodCartService = foodCartService;
    }

    @GetMapping("/foodcart/management/{communitymemberid}")
    private FoodCart getFoodCart(@PathVariable("communitymemberid") String communityMemberId) {
        return foodCartService.get(communityMemberId);
    }

    /*
     * test item payload:
     * {
          "foodProviderId": "20000032",
          "foodId": "20000032001",
          "price": 3.99,
          "quantity": 5
        }
     */
    @PostMapping("/foodcart/management/{communitymemberid}")
    public CartResponse addToFoodCart(@PathVariable("communitymemberid") String communityMemberId, @RequestBody FoodCartItem foodCartItem) {
        return foodCartService.addToFoodCart(communityMemberId, foodCartItem);
    }

    //foodId in the food cart is unique which is the format of "foodproviderId+foodId"
    @DeleteMapping("/foodcart/management/{communitymemberid}")
    public CartResponse removeFromFoodCart(@PathVariable("communitymemberid") String communityMemberId, @RequestBody String foodId) {
        return foodCartService.removeFromFoodCart(communityMemberId, foodId);
    }

    @PostMapping("/foodcart/payment/{communitymemberid}")
    public CartResponse checkoutFoodCart(@PathVariable("communitymemberid") String communityMemberId) {
        return foodCartService.checkoutFoodCart(communityMemberId);
    }
}