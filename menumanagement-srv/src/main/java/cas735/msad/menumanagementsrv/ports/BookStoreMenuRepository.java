package cas735.msad.menumanagementsrv.ports;

import cas735.msad.menumanagementsrv.business.entities.BookStoreMenu;
import cas735.msad.menumanagementsrv.business.entities.BookStoreMenuItem;

import org.springframework.data.repository.CrudRepository;

import com.redislabs.modules.rejson.Path;

public interface BookStoreMenuRepository extends CrudRepository<BookStoreMenu, String>{
    String getKey(BookStoreMenu bookStoreMenu);
    String getKey(String id);
    void addToMenu(String bookStoreMenuKey, Path menuItemsPath, BookStoreMenuItem item);
    void removeFromMenu(String bookStoreMenuKey, Class<BookStoreMenuItem> bookStoreMenuItemClass, Path menuItemsPath, Long index); 
}
