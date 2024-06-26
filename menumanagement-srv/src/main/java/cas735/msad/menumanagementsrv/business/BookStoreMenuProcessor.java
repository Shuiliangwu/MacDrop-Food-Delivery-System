package cas735.msad.menumanagementsrv.business;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.Set;
import java.util.stream.LongStream;

import cas735.msad.menumanagementsrv.business.entities.BookStoreMenu;
import cas735.msad.menumanagementsrv.business.entities.BookStoreMenuItem;
import cas735.msad.menumanagementsrv.ports.BookStoreMenuRepository;
import cas735.msad.menumanagementsrv.ports.BookStoreMenuService;
import cas735.msad.menumanagementsrv.ports.EmailService;
import cas735.msad.menumanagementsrv.dto.MenuResponse;
import cas735.msad.menumanagementsrv.dto.EmailRequest;

import com.redislabs.modules.rejson.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
//bookStoreItemId in the bookstore menu is unique which is the format of "bookStoreOperatorId+bookStoreItemId"

@Slf4j
@Service
public class BookStoreMenuProcessor implements BookStoreMenuService{
    private final BookStoreMenuRepository bookStoreMenuRepository;
    private final EmailService emailService;

    @Autowired
    public BookStoreMenuProcessor(BookStoreMenuRepository bookStoreMenuRepository, EmailService emailService) {
      this.bookStoreMenuRepository = bookStoreMenuRepository;
      this.emailService = emailService;
    }

    Path menuItemsPath = Path.of(".bookStoreMenuItems");

    @Override
    public BookStoreMenu get(String id) {
      Optional<BookStoreMenu> bookStoreMenuFinder = bookStoreMenuRepository.findById(id);
      if(bookStoreMenuFinder.isPresent()){
        return bookStoreMenuFinder.get();
      }else{
        log.info("Sorry the bookstore menu doesn't exist");
        Set<BookStoreMenuItem> bookStoreMenuItems = Collections.emptySet();
        return BookStoreMenu.builder().id(id).bookStoreMenuItems(bookStoreMenuItems).build();
      }

    }

    @Override
    public MenuResponse addToBookStoreMenu(String id, BookStoreMenuItem item) {
      try {
        Optional<BookStoreMenu> bookStoreMenuFinder = bookStoreMenuRepository.findById(id);
        if (bookStoreMenuFinder.isPresent()){
          //get bookStore menu key
          String bookStoreMenuKey = bookStoreMenuRepository.getKey(id);
          log.info("Found bookstore menu, the menu's key is " + bookStoreMenuKey);

          //check if the item is already in the bookStore menu. If yes, refuse to add it.
          String bookStoreId = item.getItemId();
          BookStoreMenu bookStoreMenu = bookStoreMenuFinder.get();
          List<BookStoreMenuItem> bookStoreMenuItems = new ArrayList<BookStoreMenuItem>(bookStoreMenu.getBookStoreMenuItems());
          OptionalLong bookStoreMenuItemIndex =  LongStream.range(0, bookStoreMenuItems.size()).filter(i -> bookStoreMenuItems.get((int) i).getItemId().equals(bookStoreId)).findFirst();
          if (bookStoreMenuItemIndex.isPresent()) {
            log.info("Sorry, this item exists");
            return MenuResponse.builder().statusCode(500).response("The item already exists").build();
          }

          //If no, add it
          log.info("Adding the item to the bookstore menu...");
          bookStoreMenuRepository.addToMenu(bookStoreMenuKey, menuItemsPath, item);
          log.info("Item added");
          return MenuResponse.builder().statusCode(200).response("The item has been added successfully").build();
        }else{
          log.info("No bookstore menu found, a new bookStore menu will be created");
          BookStoreMenu bookStoreMenu = BookStoreMenu.builder().id(id).bookStoreMenuItem(item).build();
          log.info("Bookstore menu is being created: " + bookStoreMenu.toString());
          bookStoreMenuRepository.save(bookStoreMenu);
          return MenuResponse.builder().statusCode(200).response("The item has been added successfully").build();
        }
      } catch (Exception e) {
        return MenuResponse.builder().statusCode(500).response("Internal Server Error").build();
      } 
    }

    @Override
    public MenuResponse removeFromBookStoreMenu(String id, String itemId) {
      try {
        Optional<BookStoreMenu> bookStoreMenuFinder = bookStoreMenuRepository.findById(id);
        if (bookStoreMenuFinder.isPresent()) {
          BookStoreMenu bookStoreMenu = bookStoreMenuFinder.get();
          String bookStoreMenuKey = bookStoreMenuRepository.getKey(bookStoreMenu.getId());
          List<BookStoreMenuItem> bookStoreMenuItems = new ArrayList<BookStoreMenuItem>(bookStoreMenu.getBookStoreMenuItems());
          OptionalLong bookStoreMenuItemIndex =  LongStream.range(0, bookStoreMenuItems.size()).filter(i -> bookStoreMenuItems.get((int) i).getItemId().equals(itemId)).findFirst();
          if (bookStoreMenuItemIndex.isPresent()) {
            log.info("Removing the item");
            bookStoreMenuRepository.removeFromMenu(bookStoreMenuKey, BookStoreMenuItem.class, menuItemsPath, bookStoreMenuItemIndex.getAsLong());
            log.info("Item removed");
            return MenuResponse.builder().statusCode(200).response("The item was removed successfully").build();
          }
          return MenuResponse.builder().statusCode(500).response("The item doesn't exist").build();
        }else{
          return MenuResponse.builder().statusCode(500).response("The item doesn't exist").build();
        }
      } catch (Exception e) {
        return MenuResponse.builder().statusCode(500).response("Internal Server Error").build();
      }
    }

    @Override
    public MenuResponse notifyNewMenu(String id, String bookStoreOperatorEmail) {
      try {
        BookStoreMenu bookStoreMenu = this.get(id);
        String bookStoreMenuList = "";
        for(BookStoreMenuItem bookStoreMenuItem : bookStoreMenu.getBookStoreMenuItems()){
          bookStoreMenuList += bookStoreMenuItem.toString() + "\n";
        }
        EmailRequest req = EmailRequest.builder()
                .from(bookStoreOperatorEmail)
                .to("allcommunitymembers@mcmaster.ca") 
                .object("New BookStore Menu From " + id)
                .message("\nHey there!\n\n" + id + " updated bookstore menu, you are welcome to take a look!\n\nHere is the updated menu:\n" + bookStoreMenuList + "\n\nRegards")
                .build();
        emailService.send(req);
        return MenuResponse.builder().statusCode(200).response("Notified community members new bookStore menu successfully!").build();
    } catch (Exception e) {
        return MenuResponse.builder().statusCode(500).response("Failed to notify community members new bookStore menu!").build();
    }

  }
}
