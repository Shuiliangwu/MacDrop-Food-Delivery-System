package cas735.msad.accountingbillingsrv.dto;

import cas735.msad.accountingbillingsrv.dto.FoodOrder;

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
    private Set<FoodOrder> foodorders;

    public Integer count() {
        return getFoodorders().size();
    }

}
