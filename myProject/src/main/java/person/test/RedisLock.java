package person.test;

import org.springframework.util.StringUtils;
import person.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RedisLock {
    public  static final  String  LOCK_NAME="lock";
    //key不存在的时候再设置
    public  static  final String SET_IF_NOT_EXISTS="NX";
    public static final String EXPIRE="EX";
    public  static  final String SUCCESS="OK";
    //100s过期
    public  static final long  TIME=100;
    //尝试获得锁的时间
    public static final long ABTAIN_TIME=5000;
    static Integer testValue = 0;
    static ExecutorService fixedThreadPool1 = Executors.newFixedThreadPool(2);
    //测试
    public static void main(String[] args) {

        for (int i =0;i<10;i++){
            Thread runnable  =new Thread(){
                @Override
                public void run() {
                    change(Thread.currentThread().getName());
                }
            };
            Thread runnable1 =new Thread(){
                @Override
                public void run() {
                    change(Thread.currentThread().getName());
                }
            };
            fixedThreadPool1.execute(runnable);
            fixedThreadPool1.execute(runnable1);
            System.out.println("线程是否存活"+runnable.isAlive());

        }
     fixedThreadPool1.shutdown();


    }
    //多个线程并发调一个方法
    static void change(String name){
        Jedis  jedis= JedisUtil.getJedis();
        if (tryLock(jedis,name)){
            System.out.println("当前线程为："+name);
            for (int i =0;i<10;i++){
                testValue=testValue+1;
                System.out.println(testValue);
            }
            //使用完释放

            boolean b = releaseLock(jedis,name);
            if(b){
                System.out.println("释放。，。。。");
                JedisUtil.returnJedis(jedis);
            }else{
                System.out.println("未释放");
            }
        }


    }
//加锁
    public static   boolean tryLock(Jedis jedis,String threadName){

        String re = jedis.set(LOCK_NAME,threadName,"NX","EX",TIME);
        if(StringUtils.isEmpty(re)){
            //如果没有获得则持续获得
            while (StringUtils.isEmpty(re)){
                re = jedis.set(LOCK_NAME,threadName,"NX","EX",TIME);
                System.out.println("循环中："+threadName+"re"+re);
            }
        }
        if(SUCCESS.equals(re)){
            System.out.println("线程："+threadName+"获得了锁》》》》》");
            return true;
        }
        return  false;
    }
    //释放锁
    public static boolean releaseLock(Jedis jedis,String threadName){
        //获得锁当前持有的线程名
        String threadName1 = jedis.get(LOCK_NAME);
        //谁的锁需要谁释放
        if (threadName.equals(threadName1)){
            //如果此时锁恰好过期，被别的客户端获得，则会错误释放掉别的锁，因此这里还是有问题，需要用Lua语言完善
            if(jedis.del(LOCK_NAME)>0){
                System.out.println("线程："+threadName+"释放了锁。。。。。");
                return true;
            };
        }
        //其他情况则为没有释放
        return  false;
    }
}
