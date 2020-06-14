package person.test;

import org.apache.poi.hslf.blip.Bitmap;
import org.apache.tools.ant.util.DateUtils;
import org.springframework.util.StringUtils;
import person.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Transaction;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @Author: zhangshaoxuan
 * @DateTime: 2020/4/17 11:14
 * @Description:
 */
public class JedisTest {
    final static String KEY = "ordertest";
    public static void main(String[] args) throws  Exception{
        testCluster();

    }
    public static void  testCluster(){
         JedisCluster jedisCluster =  JedisUtil.getJedisCluster();
         jedisCluster.zadd("star",1,"zhangsan");
         jedisCluster.zincrby("star",1,"zhangsan");
         jedisCluster.zadd("star",3,"lisi");
         jedisCluster.zadd("star",45,"wangwu");
         Set<String> set = jedisCluster.zrange("star",0,-1);
        for (String s : set) {
            System.out.println(s);
        }
        JedisUtil.closeCluster(jedisCluster);

    }

    /**
     * 测试zset做排序
     * @param jedis
     * @return
     */
    public static void testZset(Jedis jedis){
        /*        jedis.zadd(KEY,jedis.zscore(KEY,"zhangsan")+10,"zhangsan");
        jedis.zadd(KEY,jedis.zscore(KEY,"lisi")+2,"lisi");
        jedis.zadd(KEY,jedis.zscore(KEY,"wangwu")+6,"wangwu");*/
        jedis.zadd(KEY,6,"zhaoliu");
        jedis.zadd(KEY,2,"zhouqi");
        jedis.zadd(KEY,10+3,"wuba");
        jedis.zadd(KEY,4,"zhengjiu");

        for (String s : jedis.zrevrange(KEY,0,4)) {
            System.out.println(s+"得票："+jedis.zscore(KEY,s));
        }


    }
    /**
     *测试hash,hash相當於是一个map集合，KEY是map对象的名字，field是和value是对应的key value
     */
    public static void  testhash(Jedis jedis){
        final String KEY="hash";

      /*  jedis.hset(KEY,"name","zhangsan");
        jedis.hset(KEY,"gender","女");
        jedis.hset(KEY,"age","14");
        System.out.println(jedis.hget(KEY,"gender"));*/
        //拿出整个map集合，可以用实例id和实例对象Json字符串作为field和value存储到hash中
      Map<String,String> map =  jedis.hgetAll(KEY);
        for (Map.Entry<String, String> stringStringEntry : map.entrySet()) {
            System.out.println(stringStringEntry.getKey()+"--"+stringStringEntry.getValue());
            System.out.println();
        }
        System.out.println(jedis.type(KEY));

    }
    public static   void testlist(Jedis jedis){
      List<String> list = jedis.lrange("range",0,-1);
        for (String s : list) {
            System.out.println(s);
        }
        while(jedis.llen("range")>0){
            System.out.println( jedis.lpop("range"));
        }
    }
    public static  void testString (Jedis jedis ){
    jedis.del("string");
        while (StringUtils.isEmpty(jedis.set("string","rr","NX","EX",5))){
            System.out.println(1);
            jedis.set("string","rr","NX","EX",5);
        }
        System.out.println(jedis.info());

    }
    public static void testNormal (Jedis jedis){
        long start = System.currentTimeMillis();
        for (int i=0;i<10000;i++){
            jedis.lpush("normal","value_"+i);
        }
        System.out.println("普通耗時："+String.valueOf(System.currentTimeMillis()-start));
    }
    public  static  void testPipeLine(Jedis jedis){
        Pipeline pipeline = jedis.pipelined();

        try {
            long start = System.currentTimeMillis();
            for (int i=0;i<10000;i++){
             pipeline.lpush("pipeLine","value_"+i);
            }
            pipeline.sync();
            System.out.println("管道耗時："+String.valueOf(System.currentTimeMillis()-start));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (pipeline!=null)
                try {
                    pipeline.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }

    }
    /**
    * @Author: Zhang Shaoxuan
    * @Description:  测试事务
    * @DateTime: 2020/4/18 15:33
    * @Params:
    * @Return
    */
    public  static void  testTransaction(Jedis jedis)throws Exception{
        jedis.watch("string");
        //开启事务
        Transaction transaction = jedis.multi();
        TimeUnit.SECONDS.sleep(2);
        transaction.setex("string",3,"w");
        transaction.incr("string");
        //提交
        List<Object> exec = transaction.exec();
        System.out.println(jedis.get("string"));
        for (Object o : exec) {
            if(o instanceof  Exception){
                throw  (Exception) o;
            }else{
                System.out.println(o.toString());
            }
        }
    }

    /**
     * 1请求前对key进行过滤，不是我们定义的key就不去请求redis
     *
     * @param jedis
     */
    public static String TestNull(Jedis jedis,String key){
        if(jedis.exists(key)){
            return jedis.get(key);
        }else{
                String result = jedis.get(key);
                //对空值进行缓存
            if(StringUtils.isEmpty(result)){
                jedis.setex(key,30,null) ;
            }
            return  "DB_data";
        }

        //2.对空值也进行缓存，防止短时间内大量请求因为结果为空，而落在DB上


    }
}
