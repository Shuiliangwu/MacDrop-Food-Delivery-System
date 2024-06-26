package cas735.msad.menumanagementsrv.ports;

import cas735.msad.menumanagementsrv.business.entities.FoodMenu;
import cas735.msad.menumanagementsrv.business.entities.FoodMenuItem;

import org.springframework.data.repository.CrudRepository;

import com.redislabs.modules.rejson.Path;

public interface FoodMenuRepository extends CrudRepository<FoodMenu, String>{
    String getKey(FoodMenu foodMenu);
    String getKey(String id);
    void addToMenu(String foodMenuKey, Path menuItemsPath, FoodMenuItem item);
    void removeFromMenu(String foodMenuKey, Class<FoodMenuItem> foodMenuItemClass, Path menuItemsPath, Long index);
}
