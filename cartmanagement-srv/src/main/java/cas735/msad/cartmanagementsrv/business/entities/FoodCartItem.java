package cas735.msad.cartmanagementsrv.business.entities;

import lombok.Builder;
import lombok.Data;

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
public class FoodCartItem {
    private String foodProviderId;
    private String foodName;
    private String foodId;
    private Double price;
    private Long quantity;
}
