package cas735.msad.accountingbillingsrv.business.entities;

import cas735.msad.accountingbillingsrv.business.entities.BookStoreCartItem;

import java.util.Set;

import lombok.*;

@Data
@Builder
@AllArgsConstructor @NoArgsConstructor
public class BookStoreCart {
    private String id; //id in here is communitymemberid

    @Singular
    private Set<BookStoreCartItem> bookStoreCartItems;

    public Integer count() {
        return getBookStoreCartItems().size();
    }

    public Double getTotal() {
        return bookStoreCartItems //
            .stream() //
            .mapToDouble(ci -> ci.getMddPrice() * ci.getQuantity()) //
            .sum();
      }

}
