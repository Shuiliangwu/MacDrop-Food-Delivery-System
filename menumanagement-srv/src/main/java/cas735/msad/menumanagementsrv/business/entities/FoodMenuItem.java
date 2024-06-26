package cas735.msad.menumanagementsrv.business.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor @AllArgsConstructor
public class FoodMenuItem {
    private String ItemId;
    private String ItemName;
    private String ItemDescription;
    private Double Price;
}
