package cas735.msad.menumanagementsrv.business.entities;

import cas735.msad.menumanagementsrv.business.entities.FoodMenuItem;

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
public class FoodMenu {
    private String id; //id in here is foodproviderid

    @Singular
    private Set<FoodMenuItem> foodMenuItems;

    public Integer count() {
        return getFoodMenuItems().size();
    }
}
