package cas735.msad.accountingbillingsrv.dto;

import cas735.msad.accountingbillingsrv.dto.BookStoreOrder;

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
public class BookStoreOrderList {
    @Singular
    private Set<BookStoreOrder> bookstoreorders;

    public Integer count() {
        return getBookstoreorders().size();
    }
}
