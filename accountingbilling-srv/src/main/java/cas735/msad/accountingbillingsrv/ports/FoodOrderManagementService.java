package cas735.msad.accountingbillingsrv.ports;

import cas735.msad.accountingbillingsrv.dto.FoodOrderList;

public interface FoodOrderManagementService {
    void createFoodOrder(FoodOrderList foodOrderList);
}
