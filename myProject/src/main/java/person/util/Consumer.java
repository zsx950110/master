package person.util;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import java.util.HashMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 *消费者
 */
public class Consumer implements Runnable {
  private volatile boolean isRunning = true;
  BlockingQueue<String> blockingQueue;
     HashMap<String,Integer> map = new HashMap<>();
    public Consumer(BlockingQueue blockingQueue) {
        this.blockingQueue = blockingQueue;
    }
    @Override
    public void run() {
    while (isRunning){
        System.out.println(Thread.currentThread().getName()+"开始消费。。。");
        try {
            Thread.sleep(500);
            //开始消费，有则直接返回队首结果，没有就阻塞两秒，超过两秒则返回null，
            String data  =blockingQueue.poll(2,TimeUnit.SECONDS);
            if(data!=null){
                System.out.println("消费的数据为："+data);
            }else{
                // 超过2s还没数据，认为所有生产线程都已经退出，自动退出消费线程。
                isRunning=false;
                System.out.println("已经没有可以消费的了");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        } finally {
            System.out.println(Thread.currentThread().getName()+"已经消费完毕。。。");
        }
    }
    }
}
