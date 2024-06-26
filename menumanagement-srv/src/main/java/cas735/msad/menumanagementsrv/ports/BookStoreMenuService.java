package cas735.msad.menumanagementsrv.ports;

import cas735.msad.menumanagementsrv.business.entities.BookStoreMenu;
import cas735.msad.menumanagementsrv.business.entities.BookStoreMenuItem;
import cas735.msad.menumanagementsrv.dto.MenuResponse;

public interface BookStoreMenuService {
    BookStoreMenu get(String id);
    MenuResponse addToBookStoreMenu(String id, BookStoreMenuItem item);
    MenuResponse removeFromBookStoreMenu(String id, String itemId);
    MenuResponse notifyNewMenu(String id, String bookStoreOperatorEmail);
}
