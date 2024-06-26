package cas735.msad.menumanagementsrv.ports;

import cas735.msad.menumanagementsrv.business.entities.FoodMenu;
import cas735.msad.menumanagementsrv.business.entities.FoodMenuItem;
import cas735.msad.menumanagementsrv.dto.MenuResponse;

public interface FoodMenuService {
    FoodMenu get(String id);
    MenuResponse addToFoodMenu(String id, FoodMenuItem item);
    MenuResponse removeFromFoodMenu(String id, String itemId);
    MenuResponse notifyNewMenu(String id, String foodProviderEmail);
}
