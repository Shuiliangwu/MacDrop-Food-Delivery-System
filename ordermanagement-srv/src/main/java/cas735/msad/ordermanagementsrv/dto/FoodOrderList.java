package cas735.msad.ordermanagementsrv.dto;

import cas735.msad.ordermanagementsrv.business.entities.Foodorder;

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
public class FoodOrderList {
    @Singular
    private Set<Foodorder> foodorders;

    public Integer count() {
        return getFoodorders().size();
    }
}
