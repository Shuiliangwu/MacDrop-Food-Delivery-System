package cas735.msad.accountingbillingsrv.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor @NoArgsConstructor
public class BookStoreOrder {
    private String communitymemberid;
    private String bookstoreoperatorid;
    private String bookstoreitems;
    private String mddprice;
    private String ordertime;
}
