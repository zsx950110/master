package person.test;

import org.springframework.util.StringUtils;
import person.util.JedisUtil;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainTest {
    public static void main(String[] args) {
        Jedis jedis = JedisUtil.getJedis();
        String key = "goods";
        Map<String,String> map = new HashMap<>();
       map.put("qq","[{sfdsfsfs}]");
       map.put("ww","[{sfdswwwfsfs}]");
       map.put("ee","[{sfdserersfs}]");
       jedis.hmset(key,map);
       List<String> list = new ArrayList<String>();
       list.add("qq");
       list.add("ww");
       list.add("ee");
       String[] strings = new String[list.size()];
       strings = list.toArray(strings);
       List<String> list1 =  jedis.hmget(key,strings);
        for (String s : list1) {
            System.out.println(s);
        }
        JedisUtil.returnJedis(jedis);
    }
}
