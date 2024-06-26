package cas735.msad.cartmanagementsrv.business.entities;

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
public class BookStoreCartItem {
    private String bookStoreOperatorId; 
    private String bookStoreItemName;
    private String bookStoreItemId;
    private Double mddPrice;
    private Long quantity;
}
