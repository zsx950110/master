package person.service;

import com.alibaba.fastjson.JSON;
import org.apache.commons.collections4.map.HashedMap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import person.pojo.ToDo;
import person.util.JedisUtil;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:application-context.xml")

public class OperationsTest {
    @Autowired
    Operations operations;

    @Test
    public void insertData() {
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread() {
                @Override
                public void run() {
                    operations.insertData();
                }
            };
            thread.start();
        }

    }

    @Test
    public void deleteTest() {
        operations.deleteData("19061615231447000831");

    }

    @Test
    public void basicJedisTest() {
        Jedis jedis = JedisUtil.getJedis();
        jedis.set("name", "zsx");
        System.out.println("eeeeeeeeeeeeee");
        System.out.println(jedis.get("name"));
        jedis.lrange("name", 0, -1);
        //测试list集合
        jedis.lpush("list1", "zsx", "ws", "ls");

    }

    final static HashMap<String, String> map = new HashMap<>();
    final static ThreadLocal<String> threadLocal = new ThreadLocal<>();
    static Integer integer = 0;
    static ThreadLocal<Integer> threadLocal2 = new ThreadLocal<>();
    //四种线程池
    //固定数量的线程池
    static ExecutorService fixedThreadPool1 = Executors.newFixedThreadPool(10);
    static volatile int test = 0;

    @Test
    public void listDba() {

        operations.listDbaObject(1000, 270);
    }

    @Test
    public void view() {

        operations.viewPage(1000, 270);
    }
    static ExecutorService fixedThreadPool22 = Executors.newFixedThreadPool(10);

    public static void main(String[] args) {
       String date1 = "2019-08-13";
      // SimpleDateFormat dateFormat = new  SimpleDateFormat("yyyy/MM/dd");



        }

    private  static void vote (int score){
        Jedis jedis = JedisUtil.getJedis();
        jedis.zincrby("article",3,"I806");
        JedisUtil.returnJedis(jedis);
    }
    //文章投票模拟
    private static void voteTime(){
        Jedis jedis = JedisUtil.getJedis();

    }
    //发布文章模拟
    @Test
    public  void  publish(){
        ToDo toDo = new ToDo();
        toDo.setAcceptTime(new Date());
        toDo.setAccount("23");

        Jedis jedis = JedisUtil.getJedis();
        map.put("taskId", JSON.toJSONString(toDo));
        //publish为关键字，map中key 为每一个id，value为对象的json值
        jedis.hmset("publish",map);
        //当前时间时间戳+
            long time = System.currentTimeMillis()+7*24*3600*1000;
        jedis.expireAt("article",time);
        System.out.println(">>>>>>>>"+(jedis.ttl("article")-System.currentTimeMillis())/(24*3600*1000));
        JedisUtil.returnJedis(jedis);
    }

    private static void normalList(String key,List<String>list){
        Jedis jedis = JedisUtil.getJedis();
        //随机产生一个1000以内的数字
        Random random = new Random();
        for (int i =0;i<list.size();i++){
            int ran  = random.nextInt(1000);
            jedis.zadd(key,ran,list.get(i));
        }
        JedisUtil.returnJedis(jedis);
    }
    //分组
    private  static  void group(){
        Jedis jedis = JedisUtil.getJedis();
        Map<String,String> map = new HashMap<>();
        //设置分组
        map.put("RRW","人人网");
        map.put("DDW","叮咚网");
        jedis.hmset("group",map);
        //拿出article中，当score大于800则放入RRW
        Set<String> list1 = jedis.zrevrange("article",0,-1);
        Set<String> set = jedis.hkeys("group");
        for (String ss : set) {
            for (String s : list1) {
                if(Double.valueOf(jedis.zscore("article",s))>800){
                    jedis.lpush(ss,s);
                }
            }
        }

        JedisUtil.returnJedis(jedis);
    }


    //模拟多条件查询通过redis返回提高查询效率
    @Test
    public void bookNames(){
        List<String> type = new ArrayList<>();
        type.add("java");
        type.add("python");
        List<String> publishers = new ArrayList<>();
        publishers.add("商务印书馆");
        publishers.add("国家图书馆出版社");
        List<String> wrap = new ArrayList<>();
        wrap.add("平装");
        wrap.add("精装");
        List<Map<String,Integer>> star = new ArrayList<>();
        //星星
        Map<String,Integer>  starMap1= new HashedMap<>();
        starMap1.put("max",3);
        starMap1.put("min",1);
        star.add(starMap1);
        //折扣
        List<Map<String,Integer>> discount = new ArrayList<>();
        Map<String,Integer>  discountMap= new HashedMap<>();
        discountMap.put("max",5);
        discountMap.put("min",3);
        Map<String,Integer>  discountMap2= new HashedMap<>();
        discountMap2.put("max",10);
        discountMap2.put("min",7);
        discount.add(discountMap2);
       List<String> names  = operations.searchBookByCondition(type,publishers,null,wrap,star,discount);
        System.out.println("查询出的总数为："+names.size());
        for (String name : names) {
            System.out.println(name);
        }


    }


}

