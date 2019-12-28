package person.util;

import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;

/*
 * redis分布式锁
 * */
public class RedisLock {
private static String LOCK_NAME="clusterLock";
private static int TIME_OUT = 30;
private static String SUCCESS = "OK";

    /**
     * redis分布式加锁
     * @param threadName，这里的线程名有可能会重复，应该带上ip地址+port
     * @return
     */
    public static boolean tryLock(String threadName){
        Jedis jedis =  JedisUtil.getJedis();

        //当前线程加锁，成功返回ok，不成功返回null
    String re  = jedis.set(LOCK_NAME,threadName,"NX","EX",TIME_OUT);
    //加锁不成功
        if (StringUtils.isEmpty(re)){
         //循环尝试，所有客户端都循环的话 则是竞争锁
         while(StringUtils.isEmpty(re)){
             System.out.println(threadName+"在循环中");
            re = jedis.set(LOCK_NAME,threadName,"NX","EX",TIME_OUT);
         }
        }
        //要在return之前执行， 否则会连接泄露
        JedisUtil.returnJedis(jedis);
        if(SUCCESS.equals(re)){
            System.out.println("线程"+threadName+"获得了锁");
            return true;
        }
        return false;

    }

    /**
     * 释放锁
     * @param threadName，谁占用则谁释放
     * @return
     */
    public static boolean releaseLock(String threadName){
    Jedis jedis =  JedisUtil.getJedis();

    //用Lua语言写,Lua语言是原子性的，获得键和删除键能够原子性的执行,这里先调用get方法，获得key的value，如果value与县城名字一样
    //则调用del删除掉这个key，否则返回0，eval执行改Lua脚本
    String script="if redis.call('get',KEYS[1]) == ARGV[1] then return redis.call('del',KEYS[1]) else return 0 end ";
    Long eval = (Long) jedis.eval(script, 1, LOCK_NAME, threadName);
    //要在return之前执行， 否则会连接泄露
    JedisUtil.returnJedis(jedis);
    if (eval>0){
        System.out.println("线程："+threadName+"释放了锁。。。。。");
        return true;
    }
    return  false;

}

    //释放锁
    public static boolean releaseLock1(Jedis jedis,String threadName){
        //获得锁当前持有的线程名
    String threadName1 = jedis.get(LOCK_NAME);
     //  谁的锁需要谁释放
        if (threadName.equals(threadName1)){
            //如果此时锁恰好过期，被别的客户端获得，则会错误释放掉别的锁，因此这里还是有问题，需要用Lua语言完善
            if(jedis.del(LOCK_NAME)>0){
                System.out.println("线程："+threadName+"释放了锁。。。。。");
                return true;
            };
        }
        return false;
    }
}
