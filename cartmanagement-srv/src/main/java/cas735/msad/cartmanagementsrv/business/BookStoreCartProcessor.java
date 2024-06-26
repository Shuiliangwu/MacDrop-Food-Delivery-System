package cas735.msad.cartmanagementsrv.business;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.Set;
import java.util.stream.LongStream;

import cas735.msad.cartmanagementsrv.business.entities.BookStoreCart;
import cas735.msad.cartmanagementsrv.business.entities.BookStoreCartItem;
import cas735.msad.cartmanagementsrv.ports.BookStoreCartRepository;
import cas735.msad.cartmanagementsrv.ports.BookStoreCartService;
import cas735.msad.cartmanagementsrv.ports.AccountingBillingService;
import cas735.msad.cartmanagementsrv.dto.CartResponse;

import com.redislabs.modules.rejson.JReJSON;
import com.redislabs.modules.rejson.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
//bookStoreItemId in the food cart is unique which is the format of "bookStoreOperatorId+bookStoreItemId"

@Slf4j
@Service
public class BookStoreCartProcessor implements BookStoreCartService{
    @Autowired
    private BookStoreCartRepository bookStoreCartRepository;

    private final AccountingBillingService accountingBillingService;

    @Autowired
    public BookStoreCartProcessor(AccountingBillingService accountingBillingService) {
      this.accountingBillingService = accountingBillingService;
    }

    Path cartItemsPath = Path.of(".bookStoreCartItems");

    @Override
    public BookStoreCart get(String id) {
        Optional<BookStoreCart> bookStoreCartFinder = bookStoreCartRepository.findById(id);
        if(bookStoreCartFinder.isPresent()){
          return bookStoreCartFinder.get();
        }else{
          log.info("Sorry the book store cart doesn't exist");
          Set<BookStoreCartItem> bookStoreCartItems = Collections.emptySet();
          return BookStoreCart.builder().id(null).bookStoreCartItems(bookStoreCartItems).build();
        }
    }

    @Override
    public CartResponse addToBookStoreCart(String id, BookStoreCartItem item) {
      try {
        Optional<BookStoreCart> bookStoreCartFinder = bookStoreCartRepository.findById(id);
        if (bookStoreCartFinder.isPresent()){
          //get bookstore cart key
          String bookStoreCartKey = bookStoreCartRepository.getKey(id);
          log.info("Found bookstore cart, the cart's key is " + bookStoreCartKey);

          //check if the item is already in the bookstore cart. If yes, refuse to add it.
          String bookStoreItemId = item.getBookStoreItemId();
          BookStoreCart bookStoreCart = bookStoreCartFinder.get();
          List<BookStoreCartItem> bookStoreCartItems = new ArrayList<BookStoreCartItem>(bookStoreCart.getBookStoreCartItems());
          OptionalLong bookStoreCartItemIndex =  LongStream.range(0, bookStoreCartItems.size()).filter(i -> bookStoreCartItems.get((int) i).getBookStoreItemId().equals(bookStoreItemId)).findFirst();
          if (bookStoreCartItemIndex.isPresent()) {
            log.info("Sorry, this item exists");
            return CartResponse.builder().statusCode(200).response("The item already exists").build();
          }

          //If no, add it
          log.info("Adding the item to the bookstore cart...");
          bookStoreCartRepository.addToCart(bookStoreCartKey, cartItemsPath, item);
          log.info("Item added");
          return CartResponse.builder().statusCode(200).response("The item has been added successfully").build();
        }else{
          log.info("No bookstore cart found, a new bookstore cart will be created");
          BookStoreCart bookStoreCart = BookStoreCart.builder().id(id).bookStoreCartItem(item).build();
          log.info("Bookstore cart is being created: " + bookStoreCart.toString());
          bookStoreCartRepository.save(bookStoreCart);
          return CartResponse.builder().statusCode(200).response("The item has been added successfully").build();
        }
      } catch (Exception e) {
        return CartResponse.builder().statusCode(500).response("Internal Server Error").build();
      }  
    }

    @Override
    public CartResponse removeFromBookStoreCart(String id, String bookStoreItemId) {
      try {
        Optional<BookStoreCart> bookStoreCartFinder = bookStoreCartRepository.findById(id);
        if (bookStoreCartFinder.isPresent()) {
          BookStoreCart bookStoreCart = bookStoreCartFinder.get();
          String bookStoreCartKey = bookStoreCartRepository.getKey(bookStoreCart.getId());
          List<BookStoreCartItem> bookStoreCartItems = new ArrayList<BookStoreCartItem>(bookStoreCart.getBookStoreCartItems());
          OptionalLong bookStoreCartItemIndex =  LongStream.range(0, bookStoreCartItems.size()).filter(i -> bookStoreCartItems.get((int) i).getBookStoreItemId().equals(bookStoreItemId)).findFirst();
          if (bookStoreCartItemIndex.isPresent()) {
            log.info("Removing the item");
            bookStoreCartRepository.removeFromCart(bookStoreCartKey, BookStoreCartItem.class, cartItemsPath, bookStoreCartItemIndex.getAsLong());
            log.info("Item removed");
            return CartResponse.builder().statusCode(200).response("The item was removed successfully").build();
          }else{
            log.info("The item doesn't exist");
            return CartResponse.builder().statusCode(500).response("The item doesn't exist").build();
          }
        }else{
          log.info("The bookstore cart doesn't exist");
          return CartResponse.builder().statusCode(500).response("The item doesn't exist").build();
        }
      } catch (Exception e) {
        return CartResponse.builder().statusCode(500).response("Internal Server Error").build();
      }  
    }

    @Override
    public CartResponse checkoutBookStoreCart(String id) {
      try {
        Optional<BookStoreCart> bookStoreCartFinder = bookStoreCartRepository.findById(id);
        if(bookStoreCartFinder.isPresent()){
          BookStoreCart bookStoreCart = bookStoreCartFinder.get();
          log.info("Sending the bookstore cart to AccountingBillingService...");
          accountingBillingService.sendBookStoreCartToAccountingBilling(bookStoreCart);
          log.info("Cart sent");
  
          log.info("Cleaning cart...");
          String bookStoreCartKey = bookStoreCartRepository.getKey(bookStoreCart.getId());
          for(Long index = new Long(0); index < bookStoreCart.getBookStoreCartItems().size(); index++){
            bookStoreCartRepository.removeFromCart(bookStoreCartKey, BookStoreCartItem.class, cartItemsPath, index);
          }
          log.info("Cart clean");
          return CartResponse.builder().statusCode(200).response("Checking out the cart").build();
        }else{
          log.info("Sorry the bookstore cart doesn't exist");
          return CartResponse.builder().statusCode(500).response("Sorry the food cart doesn't exist").build();
        }
      } catch (Exception e) {
        return CartResponse.builder().statusCode(500).response("Internal Server Error").build();
      }  

    }
}
