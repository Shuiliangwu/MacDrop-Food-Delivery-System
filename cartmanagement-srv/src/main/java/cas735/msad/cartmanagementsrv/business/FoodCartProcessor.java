package cas735.msad.cartmanagementsrv.business;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.Set;
import java.util.stream.LongStream;

import cas735.msad.cartmanagementsrv.business.entities.FoodCart;
import cas735.msad.cartmanagementsrv.business.entities.FoodCartItem;
import cas735.msad.cartmanagementsrv.ports.FoodCartRepository;
import cas735.msad.cartmanagementsrv.ports.FoodCartService;
import cas735.msad.cartmanagementsrv.ports.AccountingBillingService;
import cas735.msad.cartmanagementsrv.dto.CartResponse;

import com.redislabs.modules.rejson.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
//foodId in the food cart is unique which is the format of "foodproviderId+foodId"

@Slf4j
@Service
public class FoodCartProcessor implements FoodCartService {
    private final AccountingBillingService accountingBillingService;
    private final FoodCartRepository foodCartRepository;

    @Autowired
    public FoodCartProcessor(FoodCartRepository foodCartRepository, AccountingBillingService accountingBillingService) {
      this.accountingBillingService = accountingBillingService;
      this.foodCartRepository = foodCartRepository;
    }


    Path cartItemsPath = Path.of(".foodCartItems");

    @Override
    public FoodCart get(String id) {
        Optional<FoodCart> foodCartFinder = foodCartRepository.findById(id);
        if(foodCartFinder.isPresent()){
          return foodCartFinder.get();
        }else{
          log.info("Sorry the food cart doesn't exist");
          Set<FoodCartItem> foodCartItems = Collections.emptySet();
          return FoodCart.builder().id(id).foodCartItems(foodCartItems).build();
        }
    }

    @Override
    public CartResponse addToFoodCart(String id, FoodCartItem item) {
      try {
        Optional<FoodCart> foodCartFinder = foodCartRepository.findById(id);
        if (foodCartFinder.isPresent()){
          //get food cart key
          String foodCartKey = foodCartRepository.getKey(id);
          log.info("Found food cart, the cart's key is " + foodCartKey);

          //check if the item is already in the food cart. If yes, refuse to add it.
          String foodId = item.getFoodId();
          FoodCart foodCart = foodCartFinder.get();
          List<FoodCartItem> foodCartItems = new ArrayList<FoodCartItem>(foodCart.getFoodCartItems());
          OptionalLong foodCartItemIndex =  LongStream.range(0, foodCartItems.size()).filter(i -> foodCartItems.get((int) i).getFoodId().equals(foodId)).findFirst();
          if (foodCartItemIndex.isPresent()) {
            log.info("Sorry, this item exists");
            return CartResponse.builder().statusCode(500).response("The item already exists").build();
          }

          //If no, add it
          log.info("Adding the item to the food cart...");
          foodCartRepository.addToCart(foodCartKey, cartItemsPath, item);
          log.info("Item added");
          return CartResponse.builder().statusCode(200).response("The item has been added successfully").build();
        }else{
          log.info("No food cart found, a new food cart will be created");
          FoodCart foodCart = FoodCart.builder().id(id).foodCartItem(item).build();
          log.info("Food cart is being created: " + foodCart.toString());
          foodCartRepository.save(foodCart);
          return CartResponse.builder().statusCode(200).response("The item has been added successfully").build();
        }
      } catch (Exception e) {
        return CartResponse.builder().statusCode(500).response("Internal Server Error").build();
      }  
    }

    @Override
    public CartResponse removeFromFoodCart(String id, String foodId) {
      try {
        Optional<FoodCart> foodCartFinder = foodCartRepository.findById(id);
        if (foodCartFinder.isPresent()) {
          FoodCart foodCart = foodCartFinder.get();
          String foodCartKey = foodCartRepository.getKey(foodCart.getId());
          List<FoodCartItem> foodCartItems = new ArrayList<FoodCartItem>(foodCart.getFoodCartItems());
          OptionalLong foodCartItemIndex =  LongStream.range(0, foodCartItems.size()).filter(i -> foodCartItems.get((int) i).getFoodId().equals(foodId)).findFirst();
          if (foodCartItemIndex.isPresent()) {
            log.info("Removing the item");
            foodCartRepository.removeFromCart(foodCartKey, FoodCartItem.class, cartItemsPath, foodCartItemIndex.getAsLong());
            log.info("Item removed");
            return CartResponse.builder().statusCode(200).response("The item was removed successfully").build();
          }
          return CartResponse.builder().statusCode(500).response("The item doesn't exist").build();
        }else{
          return CartResponse.builder().statusCode(500).response("The item doesn't exist").build();
        }
      } catch (Exception e) {
        return CartResponse.builder().statusCode(500).response("Internal Server Error").build();
      }

    }

    @Override
    public CartResponse checkoutFoodCart(String id) {
      try {
        Optional<FoodCart> foodCartFinder = foodCartRepository.findById(id);
        if(foodCartFinder.isPresent()){
          FoodCart foodCart = foodCartFinder.get();
          log.info("Sending the food cart to AccountingBillingService...");
          accountingBillingService.sendFoodCartToAccountingBilling(foodCart);
          log.info("Cart sent");
  
          log.info("Cleaning cart...");
          String foodCartKey = foodCartRepository.getKey(foodCart.getId());
          for(Long index = new Long(0); index < foodCart.getFoodCartItems().size(); index++){
            foodCartRepository.removeFromCart(foodCartKey, FoodCartItem.class, cartItemsPath, index);
          }
          log.info("Cart clean");
          return CartResponse.builder().statusCode(200).response("Checking out the cart").build();
        }else{
          log.info("Sorry the food cart doesn't exist");
          return CartResponse.builder().statusCode(500).response("Sorry the food cart doesn't exist").build();
        }
      } catch (Exception e) {
        return CartResponse.builder().statusCode(500).response("Internal Server Error").build();
      }
    }
}
