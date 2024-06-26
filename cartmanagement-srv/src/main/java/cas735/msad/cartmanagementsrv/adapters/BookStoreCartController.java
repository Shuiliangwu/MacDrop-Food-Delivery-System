package cas735.msad.cartmanagementsrv.adapters;

import cas735.msad.cartmanagementsrv.business.entities.BookStoreCart;
import cas735.msad.cartmanagementsrv.business.entities.BookStoreCartItem;
import cas735.msad.cartmanagementsrv.ports.BookStoreCartService;
import cas735.msad.cartmanagementsrv.dto.CartResponse;

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
public class BookStoreCartController {
    public final BookStoreCartService bookStoreCartService;

    @Autowired
    public BookStoreCartController(BookStoreCartService bookStoreCartService){
        this.bookStoreCartService = bookStoreCartService;
    }

    @GetMapping("/bookstorecart/management/{communitymemberid}")
    private BookStoreCart getBookStoreCart(@PathVariable("communitymemberid") String communityMemberId) {
        return bookStoreCartService.get(communityMemberId);
    }

    /*
     * test item payload:
     * {
          "bookStoreOperatorId": "40000032",
          "bookStoreItemName": "Java tutorial",
          "bookStoreItemId": "40000032001",
          "mddPrice": 4,
          "quantity": 5
        }
     */
    @PostMapping("/bookstorecart/management/{communitymemberid}")
    public CartResponse addToBookStoreCart(@PathVariable("communitymemberid") String communityMemberId, @RequestBody BookStoreCartItem bookStoreCartItem) {
        return bookStoreCartService.addToBookStoreCart(communityMemberId, bookStoreCartItem);
    }

    //bookStoreItemId in the bookstore cart is unique which is the format of "bookStoreOperatorId+bookStoreItemId"
    @DeleteMapping("/bookstorecart/management/{communitymemberid}")
    public CartResponse removeFromBookStoreCart(@PathVariable("communitymemberid") String communityMemberId, @RequestBody String bookStoreItemId) {
        return bookStoreCartService.removeFromBookStoreCart(communityMemberId, bookStoreItemId);
    }

    @PostMapping("/bookstorecart/payment/{communitymemberid}")
    public CartResponse checkoutBookStoreCart(@PathVariable("communitymemberid") String communityMemberId) {
        return bookStoreCartService.checkoutBookStoreCart(communityMemberId);
    }
}
