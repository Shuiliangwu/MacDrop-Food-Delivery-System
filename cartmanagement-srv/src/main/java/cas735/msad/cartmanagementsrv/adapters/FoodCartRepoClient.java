package cas735.msad.cartmanagementsrv.adapters;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.annotation.PostConstruct;

import cas735.msad.cartmanagementsrv.business.entities.FoodCart;
import cas735.msad.cartmanagementsrv.business.entities.FoodCartItem;
import cas735.msad.cartmanagementsrv.ports.FoodCartRepository;
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
public class FoodCartRepoClient implements FoodCartRepository{

    @Autowired
    private RedisConfig redisConfig;

    private JReJSON redisJson;
    private final static String idPrefix = FoodCart.class.getName();

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
    public <S extends FoodCart> S save(S foodCart) {
      String key = getKey(foodCart);
      redisJson.set(key, foodCart);
      log.info("Food cart created!");
      return foodCart;
    }
  
    @Override
    public <S extends FoodCart> Iterable<S> saveAll(Iterable<S> carts) {
      return StreamSupport //
          .stream(carts.spliterator(), false) //
          .map(foodCart -> save(foodCart)) //
          .collect(Collectors.toList());
    }
  
    @Override
    public Optional<FoodCart> findById(String id) {
      try {
        log.info("Start to get the food cart");
        FoodCart foodCart = redisJson.get(getKey(id), FoodCart.class);
        log.info("Got the food cart");
        return Optional.ofNullable(foodCart);
      } catch (Exception e) {
        return Optional.empty();
      }
    }
  
    @Override
    public boolean existsById(String id) {
      return template.hasKey(getKey(id));
    }
  
    @Override
    public Iterable<FoodCart> findAll() {
      String[] keys = redisSets().members(idPrefix).stream().toArray(String[]::new);
      return (Iterable<FoodCart>) redisJson.mget(FoodCart.class, keys);
    }
  
    @Override
    public Iterable<FoodCart> findAllById(Iterable<String> ids) {
      String[] keys = StreamSupport.stream(ids.spliterator(), false) //
        .map(id -> getKey(id)).toArray(String[]::new);
      return (Iterable<FoodCart>) redisJson.mget(FoodCart.class, keys);
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
    public void delete(FoodCart foodCart) {
      deleteById(foodCart.getId());
    }
  
    @Override
    public void deleteAll(Iterable<? extends FoodCart> carts) {
      List<String> keys = StreamSupport //
          .stream(carts.spliterator(), false) //
          .map(foodCart -> idPrefix + foodCart.getId()) //
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
    public String getKey(FoodCart foodCart) {
      return String.format("%s:%s", idPrefix, foodCart.getId());
    }
  
    @Override
    public String getKey(String id) {
      return String.format("%s:%s", idPrefix, id);
    }

    @Override
    public void addToCart(String foodCartKey, Path cartItemsPath, FoodCartItem item) {
      redisJson.arrAppend(foodCartKey, cartItemsPath, item);
    }

    @Override
    public void removeFromCart(String foodCartKey, Class<FoodCartItem> foodCartItemClass, Path cartItemsPath, Long index) {
      redisJson.arrPop(foodCartKey, foodCartItemClass, cartItemsPath, index);
    }
}
