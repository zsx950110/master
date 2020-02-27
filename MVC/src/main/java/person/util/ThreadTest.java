package person.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 *
 */
public class ThreadTest implements Runnable{
    private  Integer count =0 ;
    private  Integer count1  =0;
    private  String name;
    private  volatile Integer  i =0;
     HashMap<String,Integer> map = new HashMap<>();

    public ThreadTest(String name) {
        this.name = name;

    }

    @Override
    public  void run() {
         /*  if("线程1".equals(this.name)){
               try {
                   Thread.sleep(1000);
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
           }*/
           if (i==2){
               throw  new RuntimeException("flag 为false，报错");
           }
           System.out.println("线程："+name+"初始化");

        //   flag = false;
           System.out.println("线程："+name+"正在执行");
           try {
               Thread.sleep(100);
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
           System.out.println("线程："+name+"执行完成，完成时间："+new Date());
           // this.i++;
           // System.out.println("》》》》》》》》》》i的值为："+i);
            i++;
           System.out.println("》》》》》》》》》》i的值为："+i);
    }
    public void  displayMap(){
        Set<Map.Entry<String,Integer>> entrySet=map.entrySet();
        for (Map.Entry<String,Integer>  entry :entrySet ) {
            System.out.println("线程为："+entry.getKey());
        }
    }
}
