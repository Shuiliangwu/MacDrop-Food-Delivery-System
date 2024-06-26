package cas735.msad.cartmanagementsrv.business.entities;

import cas735.msad.cartmanagementsrv.business.entities.BookStoreCartItem;

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
