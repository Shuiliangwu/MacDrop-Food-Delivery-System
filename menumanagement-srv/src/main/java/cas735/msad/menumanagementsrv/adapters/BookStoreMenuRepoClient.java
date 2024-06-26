package cas735.msad.menumanagementsrv.adapters;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.annotation.PostConstruct;

import cas735.msad.menumanagementsrv.business.entities.BookStoreMenu;
import cas735.msad.menumanagementsrv.business.entities.BookStoreMenuItem;
import cas735.msad.menumanagementsrv.ports.BookStoreMenuRepository;
import cas735.msad.menumanagementsrv.business.entities.RedisConfig;

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
public class BookStoreMenuRepoClient implements BookStoreMenuRepository{
    
    @Autowired
    private RedisConfig redisConfig;

    private JReJSON redisJson;
    private final static String idPrefix = BookStoreMenu.class.getName();

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
    public <S extends BookStoreMenu> S save(S bookStoreMenu) {
      String key = getKey(bookStoreMenu);
      redisJson.set(key, bookStoreMenu);
      log.info("Bookstore menu created!");
      return bookStoreMenu;
    }

    @Override
    public <S extends BookStoreMenu> Iterable<S> saveAll(Iterable<S> menus) {
      return StreamSupport //
          .stream(menus.spliterator(), false) //
          .map(bookStoreMenu -> save(bookStoreMenu)) //
          .collect(Collectors.toList());
    }

    @Override
    public Optional<BookStoreMenu> findById(String id) {
      try {
        log.info("Start to get the bookstore menu");
        BookStoreMenu bookStoreMenu = redisJson.get(getKey(id), BookStoreMenu.class);
        log.info("Got the bookstore menu");
        return Optional.ofNullable(bookStoreMenu);
      } catch (Exception e) {
        return Optional.empty();
      }
    }

    @Override
    public boolean existsById(String id) {
      return template.hasKey(getKey(id));
    }

    @Override
    public Iterable<BookStoreMenu> findAll() {
      String[] keys = redisSets().members(idPrefix).stream().toArray(String[]::new);
      return (Iterable<BookStoreMenu>) redisJson.mget(BookStoreMenu.class, keys);
    }

    @Override
    public Iterable<BookStoreMenu> findAllById(Iterable<String> ids) {
      String[] keys = StreamSupport.stream(ids.spliterator(), false) //
        .map(id -> getKey(id)).toArray(String[]::new);
      return (Iterable<BookStoreMenu>) redisJson.mget(BookStoreMenu.class, keys);
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
    public void delete(BookStoreMenu bookStoreMenu) {
      deleteById(bookStoreMenu.getId());
    }

    @Override
    public void deleteAll(Iterable<? extends BookStoreMenu> menus) {
      List<String> keys = StreamSupport //
          .stream(menus.spliterator(), false) //
          .map(bookStoreMenu -> idPrefix + bookStoreMenu.getId()) //
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


    @Override
    public String getKey(BookStoreMenu bookStoreMenu) {
      return String.format("%s:%s", idPrefix, bookStoreMenu.getId());
    }
  
    @Override
    public String getKey(String id) {
      return String.format("%s:%s", idPrefix, id);
    }

    @Override
    public void addToMenu(String bookStoreMenuKey, Path menuItemsPath, BookStoreMenuItem item) {
      redisJson.arrAppend(bookStoreMenuKey, menuItemsPath, item);
    }

    @Override
    public void removeFromMenu(String bookStoreMenuKey, Class<BookStoreMenuItem> bookStoreMenuItemClass, Path menuItemsPath, Long index){
      redisJson.arrPop(bookStoreMenuKey, bookStoreMenuItemClass, menuItemsPath, index);
    }
}
