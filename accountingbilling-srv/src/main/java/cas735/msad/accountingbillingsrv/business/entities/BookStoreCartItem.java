package cas735.msad.accountingbillingsrv.business.entities;

import lombok.*;

@Data
@Builder
@AllArgsConstructor @NoArgsConstructor
public class BookStoreCartItem {
    private String bookStoreOperatorId; 
    private String bookStoreItemName;
    private String bookStoreItemId;
    private Double mddPrice;
    private Long quantity;
}
