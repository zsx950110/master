package person.util;

import java.util.HashMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 *
 */
public class Producer implements Runnable {
  private volatile boolean isRunning = true;
  BlockingQueue<String> blockingQueue;
  private  String name;
     HashMap<String,Integer> map = new HashMap<>();
    public Producer(BlockingQueue blockingQueue,String name) {
        this.blockingQueue = blockingQueue;
        this.name = name;
    }
    @Override
    public void run() {
    //while (isRunning){
        System.out.println(this.name+"开始生产。。。");
        try {
            Thread.sleep(500);
            //开始生产，2秒内生产了则为true，否则为false
             boolean isr  =blockingQueue.offer(this.name,2, TimeUnit.SECONDS);
            if(!isr){
                System.out.println("放入数据失败"+this.name);
               // stop();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        } finally {
            System.out.println(this.name+"已经生产完毕。。。");
        }
  //  }
    }
    public  void stop(){
        isRunning=false;
    }
}
