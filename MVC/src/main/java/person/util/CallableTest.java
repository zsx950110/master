package person.util;

import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.Callable;

/**
 *
 */
public class CallableTest implements Callable <String>{
    private  Integer count =0 ;
    private  Integer count1  =0;
    private  String name;
    private int i ;
     HashMap<String,Integer> map = new HashMap<>();

    public CallableTest(String name) {
        this.name = name;
    }

    @Override

    public String call() throws RuntimeException {
        if ("线程2".equals(name)){
            throw  new RuntimeException("线程2报错");
        }
        System.out.println("线程："+name+"初始化");
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("线程："+name+"正在执行");
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("线程："+name+"执行完成，完成时间："+new Date());
        return name;
    }
}
