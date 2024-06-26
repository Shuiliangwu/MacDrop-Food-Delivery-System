package cas735.msad.accountingbillingsrv.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor @NoArgsConstructor
public class FoodOrder {
    private String communitymemberid;
    private String foodproviderid;
    private String fooditems;
    private String foodorderprice;
    private String ordertime;
    private String bikerid;
    private String dropofflocationid;
    private String estimatedpickuptime;
    private String status;
}
