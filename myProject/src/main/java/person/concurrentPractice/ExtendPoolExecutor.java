package person.concurrentPractice;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.*;

/*
 * @Author zhang shaoxuan
 * @Description 线程demo
 * @Date 21:23 2019/9/23
 *
 **/
public class ExtendPoolExecutor extends  ThreadPoolExecutor {
    /**
     * 必须要复写构造器

     */
    public ExtendPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    /**
     * 重写该方法
     * @param r
     * @param t
     */
    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        System.out.println("报错");
    }
}
