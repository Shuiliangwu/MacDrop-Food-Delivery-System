package cas735.msad.menumanagementsrv.adapters;

import cas735.msad.menumanagementsrv.business.entities.FoodMenu;
import cas735.msad.menumanagementsrv.business.entities.FoodMenuItem;
import cas735.msad.menumanagementsrv.ports.FoodMenuService;
import cas735.msad.menumanagementsrv.dto.MenuResponse;

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
public class FoodMenuController {
    public final FoodMenuService foodMenuService;

    @Autowired
    public FoodMenuController(FoodMenuService foodMenuService){
        this.foodMenuService = foodMenuService;
    }

    @GetMapping("/foodmenu/management/{foodproviderid}")
    private FoodMenu getFoodMenu(@PathVariable("foodproviderid") String foodProviderId) {
        return foodMenuService.get(foodProviderId);
    }

    /*
     * test item payload:
     * {
          "ItemId": "20000001001",
          "ItemName": "Sandwich-1",
          "ItemDescription": "This is sandwich",
          "Price": 5.0
        }
     */
    @PostMapping("/foodmenu/management/{foodproviderid}")
    public MenuResponse addToFoodMenu(@PathVariable("foodproviderid") String foodProviderId, @RequestBody FoodMenuItem foodMenuItem) {
        return foodMenuService.addToFoodMenu(foodProviderId, foodMenuItem);
    }

    //foodItemId in the food menu is unique which is the format of "foodproviderId+foodId"
    @DeleteMapping("/foodmenu/management/{foodproviderid}")
    public MenuResponse removeFromFoodMenu(@PathVariable("foodproviderid") String foodProviderId, @RequestBody String foodItemId) {
        return foodMenuService.removeFromFoodMenu(foodProviderId, foodItemId);
    }

    @PostMapping("/foodmenu/management/newmenunotification/{foodproviderid}")
    public MenuResponse notifyNewFoodMenu(@PathVariable("foodproviderid") String foodProviderId, @RequestBody String foodProviderEmail){
        return foodMenuService.notifyNewMenu(foodProviderId, foodProviderEmail);
    }

}
