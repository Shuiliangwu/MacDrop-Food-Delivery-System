package cas735.msad.menumanagementsrv.adapters;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.annotation.PostConstruct;

import cas735.msad.menumanagementsrv.business.entities.FoodMenu;
import cas735.msad.menumanagementsrv.business.entities.FoodMenuItem;
import cas735.msad.menumanagementsrv.ports.FoodMenuRepository;
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
public class FoodMenuRepoClient implements FoodMenuRepository{
    
    @Autowired
    private RedisConfig redisConfig;

    private JReJSON redisJson;
    private final static String idPrefix = FoodMenu.class.getName();

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
    public <S extends FoodMenu> S save(S foodMenu) {
      String key = getKey(foodMenu);
      redisJson.set(key, foodMenu);
      log.info("Food menu created!");
      return foodMenu;
    }

    @Override
    public <S extends FoodMenu> Iterable<S> saveAll(Iterable<S> menus) {
      return StreamSupport //
          .stream(menus.spliterator(), false) //
          .map(foodMenu -> save(foodMenu)) //
          .collect(Collectors.toList());
    }

    @Override
    public Optional<FoodMenu> findById(String id) {
      try {
        log.info("Start to get the food menu");
        FoodMenu foodMenu = redisJson.get(getKey(id), FoodMenu.class);
        log.info("Got the food menu");
        return Optional.ofNullable(foodMenu);
      } catch (Exception e) {
        return Optional.empty();
      }
    }

    @Override
    public boolean existsById(String id) {
      return template.hasKey(getKey(id));
    }

    @Override
    public Iterable<FoodMenu> findAll() {
      String[] keys = redisSets().members(idPrefix).stream().toArray(String[]::new);
      return (Iterable<FoodMenu>) redisJson.mget(FoodMenu.class, keys);
    }

    @Override
    public Iterable<FoodMenu> findAllById(Iterable<String> ids) {
      String[] keys = StreamSupport.stream(ids.spliterator(), false) //
        .map(id -> getKey(id)).toArray(String[]::new);
      return (Iterable<FoodMenu>) redisJson.mget(FoodMenu.class, keys);
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
    public void delete(FoodMenu foodMenu) {
      deleteById(foodMenu.getId());
    }

    @Override
    public void deleteAll(Iterable<? extends FoodMenu> menus) {
      List<String> keys = StreamSupport //
          .stream(menus.spliterator(), false) //
          .map(foodMenu -> idPrefix + foodMenu.getId()) //
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
    public String getKey(FoodMenu foodMenu) {
      return String.format("%s:%s", idPrefix, foodMenu.getId());
    }
  
    @Override
    public String getKey(String id) {
      return String.format("%s:%s", idPrefix, id);
    }

    @Override
    public void addToMenu(String foodMenuKey, Path menuItemsPath, FoodMenuItem item) {
      redisJson.arrAppend(foodMenuKey, menuItemsPath, item);
    }

    @Override
    public void removeFromMenu(String foodMenuKey, Class<FoodMenuItem> foodMenuItemClass, Path menuItemsPath, Long index){
      redisJson.arrPop(foodMenuKey, foodMenuItemClass, menuItemsPath, index);
    }
}
