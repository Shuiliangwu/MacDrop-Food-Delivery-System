package cas735.msad.cartmanagementsrv.adapters;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.annotation.PostConstruct;

import cas735.msad.cartmanagementsrv.business.entities.BookStoreCart;
import cas735.msad.cartmanagementsrv.business.entities.BookStoreCartItem;
import cas735.msad.cartmanagementsrv.ports.BookStoreCartRepository;
import cas735.msad.cartmanagementsrv.business.entities.RedisConfig;

import com.redislabs.modules.rejson.JReJSON;
import com.redislabs.modules.rejson.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Repository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class BookStoreCartRepoClient implements BookStoreCartRepository{

    @Autowired
    private RedisConfig redisConfig;

    private JReJSON redisJson;
    private final static String idPrefix = BookStoreCart.class.getName();
  
    @Autowired
    private RedisTemplate<String, String> template;
  
    @PostConstruct
    private void postConstruct() {
      log.info("The redisconfig(post constrocutor)is " + redisConfig.getHost() + " & " + redisConfig.getPort());
      this.redisJson = new JReJSON(redisConfig.getHost(), Integer.parseInt(redisConfig.getPort()));
    }

    private SetOperations<String, String> redisSets() {
      return template.opsForSet();
    }
  
    private HashOperations<String, String, String> redisHash() {
      return template.opsForHash();
    }
  
    @Override
    public <S extends BookStoreCart> S save(S bookStoreCart) {
      String key = getKey(bookStoreCart);
      redisJson.set(key, bookStoreCart);
      log.info("Bookstore cart created!");
      return bookStoreCart;
    }
  
    @Override
    public <S extends BookStoreCart> Iterable<S> saveAll(Iterable<S> carts) {
      return StreamSupport //
          .stream(carts.spliterator(), false) //
          .map(bookStoreCart -> save(bookStoreCart)) //
          .collect(Collectors.toList());
    }
  
    @Override
    public Optional<BookStoreCart> findById(String id) {
      try {
        log.info("Start to get the bookstore cart");
        BookStoreCart bookStoreCart = redisJson.get(getKey(id), BookStoreCart.class);
        log.info("Got the bookstore cart");
        return Optional.ofNullable(bookStoreCart);
      } catch (Exception e) {
        return Optional.empty();
      }
    }
  
    @Override
    public boolean existsById(String id) {
      return template.hasKey(getKey(id));
    }
  
    @Override
    public Iterable<BookStoreCart> findAll() {
      String[] keys = redisSets().members(idPrefix).stream().toArray(String[]::new);
      return (Iterable<BookStoreCart>) redisJson.mget(BookStoreCart.class, keys);
    }
  
    @Override
    public Iterable<BookStoreCart> findAllById(Iterable<String> ids) {
      String[] keys = StreamSupport.stream(ids.spliterator(), false) //
        .map(id -> getKey(id)).toArray(String[]::new);
      return (Iterable<BookStoreCart>) redisJson.mget(BookStoreCart.class, keys);
    }
  
    @Override
    public long count() {
      return redisSets().size(idPrefix);
    }
  
    @Override
    public void deleteById(String id) {
      redisJson.del(getKey(id));
    }
  
    @Override
    public void delete(BookStoreCart bookStoreCart) {
      deleteById(bookStoreCart.getId());
    }
  
    @Override
    public void deleteAll(Iterable<? extends BookStoreCart> carts) {
      List<String> keys = StreamSupport //
          .stream(carts.spliterator(), false) //
          .map(bookStoreCart -> idPrefix + bookStoreCart.getId()) //
          .collect(Collectors.toList());
      redisSets().getOperations().delete(keys);
    }
  
    @Override
    public void deleteAll() {
      redisSets().getOperations().delete(redisSets().members(idPrefix));
    }
    
    @Override
    public void deleteAllById(Iterable<? extends String> ids) {
        // TODO Auto-generated method stub
    }

    public String getKey(BookStoreCart bookStoreCart) {
      return String.format("%s:%s", idPrefix, bookStoreCart.getId());
    }
  
    public String getKey(String id) {
      return String.format("%s:%s", idPrefix, id);
    }

    @Override
    public void addToCart(String bookStoreCartKey, Path cartItemsPath, BookStoreCartItem item) {
      redisJson.arrAppend(bookStoreCartKey, cartItemsPath, item);
    }

    @Override
    public void removeFromCart(String bookStoreCartKey, Class<BookStoreCartItem> bookStoreCartItemClass, Path cartItemsPath, Long index) {
      redisJson.arrPop(bookStoreCartKey, bookStoreCartItemClass, cartItemsPath, index);
    }
}
