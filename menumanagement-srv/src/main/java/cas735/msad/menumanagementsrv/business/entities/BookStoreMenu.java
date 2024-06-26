package cas735.msad.menumanagementsrv.business.entities;

import cas735.msad.menumanagementsrv.business.entities.BookStoreMenuItem;

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
public class BookStoreMenu {
    private String id; //id in here is bookstoreoperatorid

    @Singular
    private Set<BookStoreMenuItem> bookStoreMenuItems;

    public Integer count() {
        return getBookStoreMenuItems().size();
    }
}
