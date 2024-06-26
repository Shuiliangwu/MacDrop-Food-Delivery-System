package cas735.msad.ordermanagementsrv.ports;

import cas735.msad.ordermanagementsrv.dto.FoodOrderList;
import cas735.msad.ordermanagementsrv.dto.FoodOrderResponseMessage;
import cas735.msad.ordermanagementsrv.dto.FoodOrderLocationTimeSelection;
import cas735.msad.ordermanagementsrv.dto.response.OrderManagementResponse;
import cas735.msad.ordermanagementsrv.dto.TimeRange;

public interface FoodOrderManagementService {
    void createFoodOrder(FoodOrderList foodOrderList);
    OrderManagementResponse acceptFoodOrder(String foodorderId);
    OrderManagementResponse refuseFoodOrder(String foodorderId, FoodOrderResponseMessage msg);
    OrderManagementResponse determineLocationTimeCommunityMember(String foodorderId, FoodOrderLocationTimeSelection foodOrderLocationTimeSelection);
    OrderManagementResponse modifyLocationTimeFoodProvider(String foodorderId, FoodOrderLocationTimeSelection foodOrderLocationTimeSelection);
    OrderManagementResponse pickUpFoodOrderBiker(String foodorderId, String bikerId);
    OrderManagementResponse dropOffCompleteFoodOrderBiker(String foodorderId);
    void mddCalculation(TimeRange timeRange, String rewardRate);
    String getWaitingForPickUpOrders(String status);
}
