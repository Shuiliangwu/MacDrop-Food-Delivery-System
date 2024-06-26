package cas735.msad.menumanagementsrv.business;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.Set;
import java.util.stream.LongStream;

import cas735.msad.menumanagementsrv.business.entities.FoodMenu;
import cas735.msad.menumanagementsrv.business.entities.FoodMenuItem;
import cas735.msad.menumanagementsrv.ports.FoodMenuRepository;
import cas735.msad.menumanagementsrv.ports.FoodMenuService;
import cas735.msad.menumanagementsrv.ports.EmailService;
import cas735.msad.menumanagementsrv.dto.MenuResponse;
import cas735.msad.menumanagementsrv.dto.EmailRequest;

import com.redislabs.modules.rejson.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
//foodItemId in the food menu is unique which is the format of "foodproviderId+foodId"

@Slf4j
@Service
public class FoodMenuProcessor implements FoodMenuService{
    private final FoodMenuRepository foodMenuRepository;
    private final EmailService emailService;

    @Autowired
    public FoodMenuProcessor(FoodMenuRepository foodMenuRepository, EmailService emailService) {
      this.foodMenuRepository = foodMenuRepository;
      this.emailService = emailService;
    }

    Path menuItemsPath = Path.of(".foodMenuItems");

    @Override
    public FoodMenu get(String id) {
      Optional<FoodMenu> foodMenuFinder = foodMenuRepository.findById(id);
      if(foodMenuFinder.isPresent()){
        return foodMenuFinder.get();
      }else{
        log.info("Sorry the food menu doesn't exist");
        Set<FoodMenuItem> foodMenuItems = Collections.emptySet();
        return FoodMenu.builder().id(id).foodMenuItems(foodMenuItems).build();
      }

    }

    @Override
    public MenuResponse addToFoodMenu(String id, FoodMenuItem item) {
      try {
        Optional<FoodMenu> foodMenuFinder = foodMenuRepository.findById(id);
        if (foodMenuFinder.isPresent()){
          //get food menu key
          String foodMenuKey = foodMenuRepository.getKey(id);
          log.info("Found food menu, the menu's key is " + foodMenuKey);

          //check if the item is already in the food menu. If yes, refuse to add it.
          String foodId = item.getItemId();
          FoodMenu foodMenu = foodMenuFinder.get();
          List<FoodMenuItem> foodMenuItems = new ArrayList<FoodMenuItem>(foodMenu.getFoodMenuItems());
          OptionalLong foodMenuItemIndex =  LongStream.range(0, foodMenuItems.size()).filter(i -> foodMenuItems.get((int) i).getItemId().equals(foodId)).findFirst();
          if (foodMenuItemIndex.isPresent()) {
            log.info("Sorry, this item exists");
            return MenuResponse.builder().statusCode(500).response("The item already exists").build();
          }

          //If no, add it
          log.info("Adding the item to the food menu...");
          foodMenuRepository.addToMenu(foodMenuKey, menuItemsPath, item);
          log.info("Item added");
          return MenuResponse.builder().statusCode(200).response("The item has been added successfully").build();
        }else{
          log.info("No food menu found, a new food menu will be created");
          FoodMenu foodMenu = FoodMenu.builder().id(id).foodMenuItem(item).build();
          log.info("Food menu is being created: " + foodMenu.toString());
          foodMenuRepository.save(foodMenu);
          return MenuResponse.builder().statusCode(200).response("The item has been added successfully").build();
        }
      } catch (Exception e) {
        return MenuResponse.builder().statusCode(500).response("Internal Server Error").build();
      } 
    }

    @Override
    public MenuResponse removeFromFoodMenu(String id, String itemId) {
      try {
        Optional<FoodMenu> foodMenuFinder = foodMenuRepository.findById(id);
        if (foodMenuFinder.isPresent()) {
          FoodMenu foodMenu = foodMenuFinder.get();
          String foodMenuKey = foodMenuRepository.getKey(foodMenu.getId());
          List<FoodMenuItem> foodMenuItems = new ArrayList<FoodMenuItem>(foodMenu.getFoodMenuItems());
          OptionalLong foodMenuItemIndex =  LongStream.range(0, foodMenuItems.size()).filter(i -> foodMenuItems.get((int) i).getItemId().equals(itemId)).findFirst();
          if (foodMenuItemIndex.isPresent()) {
            log.info("Removing the item");
            foodMenuRepository.removeFromMenu(foodMenuKey, FoodMenuItem.class, menuItemsPath, foodMenuItemIndex.getAsLong());
            log.info("Item removed");
            return MenuResponse.builder().statusCode(200).response("The item was removed successfully").build();
          }
          return MenuResponse.builder().statusCode(500).response("The item doesn't exist").build();
        }else{
          return MenuResponse.builder().statusCode(500).response("The item doesn't exist").build();
        }
      } catch (Exception e) {
        return MenuResponse.builder().statusCode(500).response("Internal Server Error").build();
      }
    }

    @Override
    public MenuResponse notifyNewMenu(String id, String foodProviderEmail) {
      try {
        FoodMenu foodMenu = this.get(id);
        String foodMenuList = "";
        for(FoodMenuItem foodMenuItem : foodMenu.getFoodMenuItems()){
          foodMenuList += foodMenuItem.toString() + "\n";
        }
        EmailRequest req = EmailRequest.builder()
                .from(foodProviderEmail)
                .to("allcommunitymembers@mcmaster.ca") 
                .object("New Food Menu From " + id)
                .message("\nHey there!\n\n" + id + " updated food menu, you are welcome to take a look!\n\nHere is the updated menu:\n" + foodMenuList + "\n\nRegards")
                .build();
        emailService.send(req);
        return MenuResponse.builder().statusCode(200).response("Notified community members new food menu successfully!").build();
    } catch (Exception e) {
        return MenuResponse.builder().statusCode(500).response("Failed to notify community members new food menu!").build();
    }

  }

}
