package person.test;

import person.service.Operations;
import person.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

import java.io.IOException;
import java.util.Set;

public class MainTest {
    public static void main(String[] args) {
        Operations operations = new Operations( );
        //operations.bookNames();
    Jedis  jedis =JedisUtil.getJedis();
   /* Set<String> set  =jedis.smembers("社科文献出版社");
        for (String s : set) {
            System.out.println(s);
        }*/
  Set<String> set =  jedis.keys("*");
        Pipeline pipeline =null;
        try {
            pipeline = jedis.pipelined();
            for (String s : set) {
                pipeline.del(s);
            }
            pipeline.sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                pipeline.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        JedisUtil.returnJedis(jedis);
    }

}
