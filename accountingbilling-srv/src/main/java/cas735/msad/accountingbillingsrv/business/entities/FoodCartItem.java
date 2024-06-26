package cas735.msad.accountingbillingsrv.business.entities;

import lombok.*;

@Data
@Builder
@AllArgsConstructor @NoArgsConstructor
public class FoodCartItem {
    private String foodProviderId;
    private String foodName;
    private String foodId;
    private Double price;
    private Long quantity;
}
