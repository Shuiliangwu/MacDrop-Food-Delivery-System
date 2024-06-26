package cas735.msad.menumanagementsrv.adapters;

import cas735.msad.menumanagementsrv.business.entities.BookStoreMenu;
import cas735.msad.menumanagementsrv.business.entities.BookStoreMenuItem;
import cas735.msad.menumanagementsrv.ports.BookStoreMenuService;
import cas735.msad.menumanagementsrv.dto.MenuResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class BookStoreMenuController {
    public final BookStoreMenuService bookStoreMenuService;

    @Autowired
    public BookStoreMenuController(BookStoreMenuService bookStoreMenuService){
        this.bookStoreMenuService = bookStoreMenuService;
    }

    @GetMapping("/bookstoremenu/management/{bookstoreproviderid}")
    private BookStoreMenu getBookStoreMenu(@PathVariable("bookstoreproviderid") String bookStoreProviderId) {
        return bookStoreMenuService.get(bookStoreProviderId);
    }

    /*
     * test item payload:
     * {
          "ItemId": "40000001001",
          "ItemName": "Java textbook-1",
          "ItemDescription": "This is Java textbook",
          "MddPrice": 5.0
        }
     */
    @PostMapping("/bookstoremenu/management/{bookstoreproviderid}")
    public MenuResponse addToBookStoreMenu(@PathVariable("bookstoreproviderid") String bookStoreProviderId, @RequestBody BookStoreMenuItem bookStoreMenuItem) {
        return bookStoreMenuService.addToBookStoreMenu(bookStoreProviderId, bookStoreMenuItem);
    }

    //bookStoreItemId in the bookStore menu is unique which is the format of "bookStoreproviderId+bookStoreId"
    @DeleteMapping("/bookstoremenu/management/{bookstoreproviderid}")
    public MenuResponse removeFromBookStoreMenu(@PathVariable("bookstoreproviderid") String bookStoreProviderId, @RequestBody String bookStoreItemId) {
        return bookStoreMenuService.removeFromBookStoreMenu(bookStoreProviderId, bookStoreItemId);
    }

    @PostMapping("/bookstoremenu/management/newmenunotification/{bookstoreproviderid}")
    public MenuResponse notifyNewBookStoreMenu(@PathVariable("bookstoreproviderid") String bookStoreProviderId, @RequestBody String bookStoreProviderEmail){
        return bookStoreMenuService.notifyNewMenu(bookStoreProviderId, bookStoreProviderEmail);
    }
}
