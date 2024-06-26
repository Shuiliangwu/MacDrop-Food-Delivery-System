package cas735.msad.accountingbillingsrv.business.entities;

import cas735.msad.accountingbillingsrv.business.entities.FoodCartItem;

import java.util.Set;

import lombok.*;

@Data
@Builder
@AllArgsConstructor @NoArgsConstructor
public class FoodCart {
    private String id; //id in here is communitymemberid

    @Singular
    private Set<FoodCartItem> foodCartItems;

    public Integer count() {
        return getFoodCartItems().size();
    }

    public Double getTotal() {
        return foodCartItems //
            .stream() //
            .mapToDouble(ci -> ci.getPrice() * ci.getQuantity()) //
            .sum();
      }
}

