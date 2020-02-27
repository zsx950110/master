package common.util;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Pipeline;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/*
 * jedis连接池类
 * */
public class JedisUtil {

    private static JedisPool jedisPool;
    private static JedisPoolConfig config;
    //最大活跃连接
    private static final int MAX_ACTIVE = 5;
    private static final String ADD = "127.0.0.1";
    private static final int PORT = 6379;
    private static Jedis jedis;
    private static  Logger logger = LoggerFactory.getLogger(JedisUtil.class);

    //单例
    private JedisUtil() {
    }

    static {
        try {
            config = new JedisPoolConfig();
            //最多连接数
            config.setMaxTotal(MAX_ACTIVE);

            logger.info("连接池配置成功.........");
        } catch (Exception e) {
            logger.error("连接池配置失败！！！！");
            e.printStackTrace();
        }
    }


    //获得jedis对象,连接池为单例
    public static Jedis getJedis() {
        synchronized (JedisUtil.class) {
            if (jedisPool == null) {
                jedisPool = new JedisPool(config, ADD, PORT);
            }
        }
        return jedisPool == null ? null : jedisPool.getResource();
    }

    //将连接释放,returnResource被官方放弃，不释放，则连接很快达到最大会发生阻塞
    public static void returnJedis(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }


    //key-value封装
    public static void putValue(String key,int expireSecond,String value){
        Jedis jedis = JedisUtil.getJedis();
        try {
            jedis.setex(key, expireSecond,value);
        } catch (Exception e) {
            logger.error("字符串存储出错"+e.getMessage(),e);
        } finally {
            //关闭连接
            JedisUtil.returnJedis(jedis);
        }
    }

    //存map
    public void putMap(String key, Map<String,String> map){
        Jedis jedis = JedisUtil.getJedis();
        try {
            jedis.hmset(key,map);
        } catch (Exception e) {
            logger.error("map存储出错"+e.getMessage(),e);
        } finally {
            JedisUtil.returnJedis(jedis);
        }

    }
    //存list集合
    public static void putList(String key, List<String> list){
        Jedis jedis = JedisUtil.getJedis();
        Pipeline pipeline= null;
        try {
             pipeline = jedis.pipelined();
            for (String s : list) {
                pipeline.lpush(key,s);
            }
            pipeline.sync();
        } catch (Exception e) {
            logger.error("集合存储失败"+e.getMessage(),e);
        } finally {
            try {
                pipeline.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            JedisUtil.returnJedis(jedis);
        }
    }
    //设置有效时间
    public void expire (String key,long expiredTime){
        Jedis jedis = JedisUtil.getJedis();
        try {
            jedis.expireAt(key,expiredTime);
        } catch (Exception e) {
            logger.error("过期时间设置出错"+e.getMessage(),e);
        } finally {
            JedisUtil.returnJedis(jedis);
        }
    }



}
