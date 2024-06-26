package cas735.msad.cartmanagementsrv.business.entities;

import cas735.msad.cartmanagementsrv.business.entities.FoodCartItem;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Singular;
import lombok.Setter;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor @AllArgsConstructor 
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
