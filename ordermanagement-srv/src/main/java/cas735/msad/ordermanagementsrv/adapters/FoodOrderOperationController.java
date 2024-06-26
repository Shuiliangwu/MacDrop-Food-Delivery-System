package cas735.msad.ordermanagementsrv.adapters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;  
import org.springframework.web.bind.annotation.RequestBody;  
import org.springframework.web.bind.annotation.RestController;

import cas735.msad.ordermanagementsrv.ports.FoodOrderManagementService;
import cas735.msad.ordermanagementsrv.dto.FoodOrderResponseMessage;
import cas735.msad.ordermanagementsrv.dto.FoodOrderLocationTimeSelection;
import cas735.msad.ordermanagementsrv.dto.TimeRange;
import cas735.msad.ordermanagementsrv.dto.response.OrderManagementResponse;

@RestController
public class FoodOrderOperationController {
    public final FoodOrderManagementService foodOrderManagementService;

    @Autowired
    public FoodOrderOperationController(FoodOrderManagementService foodOrderManagementService){
        this.foodOrderManagementService = foodOrderManagementService;
    }

    @PostMapping("/foodorderoperation/foodprovider/acceptance/{foodorderid}")
    private OrderManagementResponse acceptFoodOrder(@PathVariable("foodorderid") String foodorderid){
        return foodOrderManagementService.acceptFoodOrder(foodorderid);
    }

    @PostMapping("/foodorderoperation/foodprovider/refusion/{foodorderid}")
    private OrderManagementResponse refuseFoodOrder(@PathVariable("foodorderid") String foodorderid, @RequestBody FoodOrderResponseMessage msg){
        return foodOrderManagementService.refuseFoodOrder(foodorderid, msg);
    }

    @PostMapping("/foodorderoperation/foodprovider/modification/{foodorderid}")
    private OrderManagementResponse modifyLocationTimeFoodOrder(@PathVariable("foodorderid") String foodorderid, @RequestBody FoodOrderLocationTimeSelection foodOrderLocationTimeSelectionFoodOrderResponseMessage){
        return foodOrderManagementService.modifyLocationTimeFoodProvider(foodorderid, foodOrderLocationTimeSelectionFoodOrderResponseMessage);
    }

    @PostMapping("/foodorderoperation/communitymember/locationtimeselection/{foodorderid}")
    private OrderManagementResponse selectLocationTimeFoodOrder(@PathVariable("foodorderid") String foodorderid, @RequestBody FoodOrderLocationTimeSelection foodOrderLocationTimeSelection){
        return foodOrderManagementService.determineLocationTimeCommunityMember(foodorderid, foodOrderLocationTimeSelection);
    }

    @PostMapping("/foodorderoperation/biker/delivery/{foodorderid}")
    private OrderManagementResponse pickUpDeliverFoodOrder(@PathVariable("foodorderid") String foodorderid, @RequestBody String bikerId){
        return foodOrderManagementService.pickUpFoodOrderBiker(foodorderid, bikerId);
    }

    @PostMapping("/foodorderoperation/biker/dropoff/{foodorderid}")
    private OrderManagementResponse dropOffCompleteFoodOrder(@PathVariable("foodorderid") String foodorderid){
        return foodOrderManagementService.dropOffCompleteFoodOrderBiker(foodorderid);
    }

    @GetMapping("/foodorderoperation/biker/waitingforpickuporders")
    private String getWaitingForPickUpOrders(){
        return foodOrderManagementService.getWaitingForPickUpOrders("accepted & waiting for pickup");
    }

    @PostMapping("/foodorderoperation/mddcalculation/{rewardrate}")
    private void mddCalculation(@PathVariable("rewardrate") String rewardrate, @RequestBody TimeRange timeRange){
        foodOrderManagementService.mddCalculation(timeRange, rewardrate);
    }
}
