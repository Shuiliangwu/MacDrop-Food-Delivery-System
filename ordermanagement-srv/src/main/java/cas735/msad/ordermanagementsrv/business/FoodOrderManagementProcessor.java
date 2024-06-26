package cas735.msad.ordermanagementsrv.business;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

import cas735.msad.ordermanagementsrv.business.entities.Communitymember;
import cas735.msad.ordermanagementsrv.business.entities.Foodorder;
import cas735.msad.ordermanagementsrv.business.entities.Biker;
import cas735.msad.ordermanagementsrv.business.entities.Location;
import cas735.msad.ordermanagementsrv.dto.FoodOrderList;
import cas735.msad.ordermanagementsrv.dto.FoodOrderLocationTimeSelection;
import cas735.msad.ordermanagementsrv.dto.FoodOrderResponseMessage;
import cas735.msad.ordermanagementsrv.dto.NotificationCommand;
import cas735.msad.ordermanagementsrv.dto.TimeRange;
import cas735.msad.ordermanagementsrv.dto.response.OrderManagementResponse;
import cas735.msad.ordermanagementsrv.ports.FoodOrderManagementService;
import cas735.msad.ordermanagementsrv.ports.FoodOrderRepository;
import cas735.msad.ordermanagementsrv.ports.BikerRepository;
import cas735.msad.ordermanagementsrv.ports.CommunityMemberRepository;
import cas735.msad.ordermanagementsrv.ports.LocationRepository;
import cas735.msad.ordermanagementsrv.ports.NotificationService;

@Slf4j
@Service
public class FoodOrderManagementProcessor implements FoodOrderManagementService {
    private final CommunityMemberRepository communityMemberRepository;
    private final FoodOrderRepository foodOrderRepository;
    private final BikerRepository bikerRepository;
    private final LocationRepository locationRepository;
    private final NotificationService notificationService;
    private final String VALIDSTATUS = "dropped-off & complete";

    @Autowired
    public FoodOrderManagementProcessor(CommunityMemberRepository communityMemberRepository, FoodOrderRepository foodOrderRepository, BikerRepository bikerRepository, LocationRepository locationRepository, NotificationService notificationService) {
        this.communityMemberRepository = communityMemberRepository;
        this.foodOrderRepository = foodOrderRepository;
        this.bikerRepository = bikerRepository;
        this.locationRepository = locationRepository;
        this.notificationService = notificationService;

    }

    @Override
    public void createFoodOrder(FoodOrderList foodOrderList) {
        //Create the food order(s)
        log.info("Creating food order(s)...");
        for(Foodorder foodorder: foodOrderList.getFoodorders()){
            foodOrderRepository.save(foodorder);
        }
        log.info("Food orders created");
        //Notify the community member
        String communityMemberId = foodOrderList.getFoodorders().iterator().next().getCommunitymemberid();
        log.info("Start to notify community member " + communityMemberId);
        NotificationCommand notificationCommunityMember = NotificationCommand.builder()
        .from("OrderManagementService")
        .to(communityMemberId)
        .message("You ordered food just now, please select drop-off location & delivery time, thanks!")
        .build();
        notificationService.notify(notificationCommunityMember); 
    }

    @Override
    public OrderManagementResponse acceptFoodOrder(String foodorderId) {
        try {
            Foodorder foodOrder = foodOrderRepository.findById(Integer.parseInt(foodorderId)).get();
            foodOrder.setStatus("accepted & waiting for pickup");
            foodOrderRepository.save(foodOrder);
    
            //Notify community member
            log.info("Notify community member that the food order was accepted & ready for pickup");
            NotificationCommand notificationAcceptanceCommunityMember = NotificationCommand.builder()
            .from("OrderManagementService")
            .to(foodOrder.getCommunitymemberid())
            .message("Your order " + foodOrder.getFoodorderid() + " has been accepted and ready for be picked up by biker!")
            .build();
            notificationService.notify(notificationAcceptanceCommunityMember);
    
            //Notify community member
            log.info("Notify bikers that the food is ready for pickup");
            NotificationCommand notificationAcceptanceBiker = NotificationCommand.builder()
            .from("OrderManagementService")
            .to("All bikers")
            .message("Hi bikers, the order " + foodOrder.getFoodorderid() + " is ready to be picked up!")
            .build();
            notificationService.notify(notificationAcceptanceBiker);
            return OrderManagementResponse.builder().statusCode(200).response("Accepted the order successfully!").build();            
        } catch (Exception e) {
            return OrderManagementResponse.builder().statusCode(500).response("Internal Server Error").build();
        }

    }

    @Override
    public OrderManagementResponse refuseFoodOrder(String foodorderId, FoodOrderResponseMessage msg) {
        try {
            Foodorder foodOrder = foodOrderRepository.findById(Integer.parseInt(foodorderId)).get();
            foodOrder.setStatus("refused");
            foodOrderRepository.save(foodOrder);
    
            log.info("Notify community member that the food order was refused");
            NotificationCommand notificationRefusion = NotificationCommand.builder()
            .from("OrderManagementService")
            .to(foodOrder.getCommunitymemberid())
            .message("We feel Sorry that your food order has been refused, the reason is " + msg.getMessage())
            .build();
            notificationService.notify(notificationRefusion);    
            return OrderManagementResponse.builder().statusCode(200).response("Refused the order successfully!").build();
        } catch (Exception e) {
            return OrderManagementResponse.builder().statusCode(500).response("Internal Server Error").build();
        }

    }

    @Override
    public OrderManagementResponse determineLocationTimeCommunityMember(String foodorderId, FoodOrderLocationTimeSelection foodOrderLocationTimeSelection) {
        try {
            //determine location & time
            Foodorder foodOrder = foodOrderRepository.findById(Integer.parseInt(foodorderId)).get();
            if(foodOrderLocationTimeSelection.getLocation() != null){
                foodOrder.setDropofflocationid(foodOrderLocationTimeSelection.getLocation());
            }
            if(foodOrderLocationTimeSelection.getTime() != null){
                foodOrder.setEstimatedpickuptime(foodOrderLocationTimeSelection.getTime());
            }
            foodOrder.setStatus("placed");

            //Notify the food provider
            log.info("Start to notify food provider " + foodOrder.getFoodproviderid());
            NotificationCommand notificationFoodProvider = NotificationCommand.builder()
            .from("OrderManagementService")
            .to(foodOrder.getFoodproviderid())
            .message("Hi, new food order, thanks!")
            .build();
            notificationService.notify(notificationFoodProvider);

            //Save the food order
            foodOrderRepository.save(foodOrder);    
            return OrderManagementResponse.builder().statusCode(200).response("Determined the location & time successfully!").build();  
        } catch (Exception e) {
            return OrderManagementResponse.builder().statusCode(500).response("Internal Server Error").build();
        }
        
    }

    @Override
    public OrderManagementResponse modifyLocationTimeFoodProvider(String foodorderId, FoodOrderLocationTimeSelection foodOrderLocationTimeSelection) {
        try {
            Foodorder foodOrder = foodOrderRepository.findById(Integer.parseInt(foodorderId)).get();
            if(foodOrderLocationTimeSelection.getLocation() != null && foodOrderLocationTimeSelection.getTime() == null){
                foodOrder.setDropofflocationid(foodOrderLocationTimeSelection.getLocation());
                //Notify the community member
                log.info("Start to notify community member " + foodOrder.getCommunitymemberid());
                NotificationCommand notificationCommunityMember = NotificationCommand.builder()
                .from("OrderManagementService")
                .to(foodOrder.getCommunitymemberid())
                .message("Hi, Your order of " + foodorderId + "'s drop-off location has been modified by food provider', thanks!")
                .build();
                notificationService.notify(notificationCommunityMember);
                //Save the food order
                foodOrderRepository.save(foodOrder);            
            }else if(foodOrderLocationTimeSelection.getLocation() == null && foodOrderLocationTimeSelection.getTime() != null){
                foodOrder.setEstimatedpickuptime(foodOrderLocationTimeSelection.getTime());
                //Notify the community member
                log.info("Start to notify community member " + foodOrder.getCommunitymemberid());
                NotificationCommand notificationCommunityMember = NotificationCommand.builder()
                .from("OrderManagementService")
                .to(foodOrder.getCommunitymemberid())
                .message("Hi, Your order of " + foodorderId + "'s estimated delivery time has been modified by food provider', thanks!")
                .build();
                notificationService.notify(notificationCommunityMember);
                //Save the food order
                foodOrderRepository.save(foodOrder); 
            }else if(foodOrderLocationTimeSelection.getLocation() != null && foodOrderLocationTimeSelection.getTime() != null){
                foodOrder.setDropofflocationid(foodOrderLocationTimeSelection.getLocation());
                foodOrder.setEstimatedpickuptime(foodOrderLocationTimeSelection.getTime());
                //Notify the community member
                log.info("Start to notify community member " + foodOrder.getCommunitymemberid());
                NotificationCommand notificationCommunityMember = NotificationCommand.builder()
                .from("OrderManagementService")
                .to(foodOrder.getCommunitymemberid())
                .message("Hi, Your order of " + foodorderId + "'s drop-off location & estimated delivery time has been modified by food provider', thanks!")
                .build();
                notificationService.notify(notificationCommunityMember);
                
                //Save the food order
                foodOrderRepository.save(foodOrder);
            }  
            return OrderManagementResponse.builder().statusCode(200).response("Modified the location & time successfully!").build();  
        } catch (Exception e) {
            return OrderManagementResponse.builder().statusCode(500).response("Internal Server Error").build();
        }
        
    }

    @Override
    public OrderManagementResponse pickUpFoodOrderBiker(String foodorderId, String bikerId) {
        try {
            Foodorder foodOrder = foodOrderRepository.findById(Integer.parseInt(foodorderId)).get();
            foodOrder.setBikerid(bikerId);
            foodOrder.setStatus("delivering");
            foodOrderRepository.save(foodOrder);
    
            //Modify location wordload
            Integer dropOffLocationId= Integer.parseInt(foodOrder.getDropofflocationid());
            Location location = locationRepository.findById(dropOffLocationId).get();
            Integer currentWorkloadLocation = location.getCurrentworkload();
            Integer historyWorkloadLocation = location.getHistoryworkload();
            currentWorkloadLocation++;
            historyWorkloadLocation++;
            location.setCurrentworkload(currentWorkloadLocation);
            location.setHistoryworkload(historyWorkloadLocation);
            locationRepository.save(location);
    
            //Modify biker workload
            Biker biker = bikerRepository.findById(Integer.parseInt(bikerId)).get();
            Integer currentWorkloadBiker = biker.getCurrentworkload();
            Integer historyWorkloadBiker = biker.getHistoryworkload();
            currentWorkloadBiker++;
            historyWorkloadBiker++;
            biker.setCurrentworkload(currentWorkloadBiker);
            biker.setHistoryworkload(historyWorkloadBiker);
            bikerRepository.save(biker);
    
            //Notify community member
            log.info("Start to notify community member " + foodOrder.getCommunitymemberid());
            NotificationCommand notificationFoodProvider = NotificationCommand.builder()
            .from("OrderManagementService")
            .to(foodOrder.getCommunitymemberid())
            .message("Hi, your order " + foodOrder.getFoodorderid() + " is being delivered by " + bikerId)
            .build();
            notificationService.notify(notificationFoodProvider);  
            return OrderManagementResponse.builder().statusCode(200).response("Picked up the order successfully!").build();          
        } catch (Exception e) {
            return OrderManagementResponse.builder().statusCode(500).response("Internal Server Error").build();
        }

    }

    @Override
    public OrderManagementResponse dropOffCompleteFoodOrderBiker(String foodorderId) {
        try {
            Foodorder foodOrder = foodOrderRepository.findById(Integer.parseInt(foodorderId)).get();
            String bikerId = foodOrder.getBikerid();
            foodOrder.setStatus("dropped-off & complete");
            foodOrderRepository.save(foodOrder);
            
            //Modify location wordload
            Integer dropOffLocationId= Integer.parseInt(foodOrder.getDropofflocationid());
            Location location = locationRepository.findById(dropOffLocationId).get();
            Integer currentWorkloadLocation = location.getCurrentworkload();
            currentWorkloadLocation--;
            location.setCurrentworkload(currentWorkloadLocation);
            locationRepository.save(location);
    
            //Modify biker workload
            Biker biker = bikerRepository.findById(Integer.parseInt(bikerId)).get();
            Integer currentWorkloadBiker = biker.getCurrentworkload();
            currentWorkloadBiker--;
            biker.setCurrentworkload(currentWorkloadBiker);
            bikerRepository.save(biker);
    
            //Notify community member
            log.info("Start to notify community member " + foodOrder.getCommunitymemberid());
            NotificationCommand notificationDropoffCommunityMember = NotificationCommand.builder()
            .from("OrderManagementService")
            .to(foodOrder.getCommunitymemberid())
            .message("Hi, your order " + foodOrder.getFoodorderid() + " has been dropped off, please pick up at " + foodOrder.getDropofflocationid())
            .build();
            notificationService.notify(notificationDropoffCommunityMember);
    
            //Notify the food provider
            log.info("Start to notify food provider " + foodOrder.getFoodproviderid());
            NotificationCommand notificationFoodProvider = NotificationCommand.builder()
            .from("OrderManagementService")
            .to(foodOrder.getFoodproviderid())
            .message("Hi, the food order " + foodOrder.getFoodorderid() + " has been dropped off")
            .build();
            notificationService.notify(notificationFoodProvider);
            return OrderManagementResponse.builder().statusCode(200).response("Dropped off the order successfully!").build();
        } catch (Exception e) {
            return OrderManagementResponse.builder().statusCode(500).response("Internal Server Error").build();
        }
        
    }

    @Override
    public void mddCalculation(TimeRange timeRange, String rewardRate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        ZonedDateTime leftTime = ZonedDateTime.of(Integer.parseInt(timeRange.getLeftyear()), Integer.parseInt(timeRange.getLeftmonth()), Integer.parseInt(timeRange.getLeftday()), Integer.parseInt(timeRange.getLefthour()), Integer.parseInt(timeRange.getLeftmin()), Integer.parseInt(timeRange.getLeftsec()), 0, ZoneId.systemDefault());
        log.info("The left time in time range is " + leftTime.format(formatter));
        ZonedDateTime rightTime = ZonedDateTime.of(Integer.parseInt(timeRange.getRightyear()), Integer.parseInt(timeRange.getRightmonth()), Integer.parseInt(timeRange.getRightday()), Integer.parseInt(timeRange.getRighthour()), Integer.parseInt(timeRange.getRightmin()), Integer.parseInt(timeRange.getRightsec()), 0, ZoneId.systemDefault());
        log.info("The right time in time range is " + rightTime.format(formatter));  

        List<Foodorder> foodOrdersByTimeRange = foodOrderRepository.findByOrderTimeRange(leftTime.format(formatter), rightTime.format(formatter));
        log.info("The food orders between " + leftTime.format(formatter) + " and " + rightTime.format(formatter) + " are \n" + foodOrdersByTimeRange);
    
        //MDD Calculation
        Double rewardRateCoefficient = Double.parseDouble(rewardRate) * 0.01;
        Set<String> communityMemberIds = new HashSet<String>();
        for(Foodorder foodOrder : foodOrdersByTimeRange){
            if(foodOrder.getStatus().equals(VALIDSTATUS)){
                log.info("It is here now\n" + foodOrder.getCommunitymemberid());
                communityMemberIds.add(foodOrder.getCommunitymemberid());
            }else{
                continue;
            }
        }
        log.info("The size is " + communityMemberIds.size());

        for(String communityMemberId : communityMemberIds){
            List<Foodorder> foodOrders = new ArrayList<Foodorder>();
            Set<String> foodProviderIds = new HashSet<String>();
            Double totalExpense = 0.0;
            for(Foodorder foodOrder : foodOrdersByTimeRange){
                if(foodOrder.getCommunitymemberid() == communityMemberId){
                    foodOrders.add(foodOrder);
                    foodProviderIds.add(foodOrder.getFoodproviderid());
                    totalExpense += Double.parseDouble(foodOrder.getFoodorderprice());
                }
            }
            if(foodProviderIds.size() == 1){
                log.info("No MDD will be earned");
                continue;
            }else{
                Double totalMdd = totalExpense * rewardRateCoefficient * (foodProviderIds.size() - 1);
                log.info(communityMemberId + " earned " + totalMdd + " MDD!");
                Optional<Communitymember> communityMembers = communityMemberRepository.findById(Integer.parseInt(communityMemberId));
                Communitymember communityMember = communityMembers.get();

                Double mddAfterCalculation =  Double.parseDouble(communityMember.getCurrentmdd()) + totalMdd;
                communityMember.setCurrentmdd(mddAfterCalculation.toString());
                communityMemberRepository.save(communityMember);
            }
        }
    }

    @Override
    public String getWaitingForPickUpOrders(String status) {
        List<Foodorder> foodorders = foodOrderRepository.findWaitingForPickUpOrders(status);
        if(foodorders.isEmpty()){
            return "Sorry, there is no food orders waiting for pickup";
        }
        String waitingForPickUpFoodOrders = "The following is a list of food orders waiting for pickup\n";
        for(Foodorder foodorder : foodorders){
            waitingForPickUpFoodOrders += foodorder.toString() + "\n";
        }

        return waitingForPickUpFoodOrders;
    }
}