package person.concurrentPractice;


import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.apache.dubbo.common.threadpool.support.cached.CachedThreadPool;
import org.apache.poi.ss.usermodel.DateUtil;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.*;

/*
 * @Author zhang shaoxuan
 * @Description 线程demo
 * @Date 21:23 2019/9/23
 *
 **/
public class ThreadDemo  implements  Runnable {
    private final  static String  A = "a";
    private final  static String  B = "B";
    private  static   int b=0;
    private static   volatile   int INIT_VALUE = 0;
    private final static  int MAX_VALUE = 5;
    private   static       Integer   testSynchronized = 0;
    private   static Integer  testSynchronized1 = 0;
    private static  boolean isOver = false;
    private static   AtomicInteger atomicInteger = new AtomicInteger(0);
    volatile static boolean     isput=false;
    volatile static boolean flag =true;
    //公用线程池
    private static    LinkedBlockingQueue linkedBlockingQueue = new LinkedBlockingQueue(3);
    private static  ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2,10,60,TimeUnit.SECONDS,linkedBlockingQueue);
    final static ThreadLocal<ThreadDemo> threadDemoThreadLocal = new ThreadLocal();
    private static ThreadLocal<Long> longThreadLocal = new ThreadLocal(){
        @Override
        protected Object initialValue() {
            return System.currentTimeMillis();
        }
    };
    //测试读写锁用
    ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    ReadWriteLock readWriteLock1 = new ReentrantReadWriteLock();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    //单例
    //静态属性
    private static ThreadDemo threadDemo;
    //私有构造器
    private ThreadDemo(){};
    public static ThreadDemo getInstance(){
        if (threadDemo==null){
            threadDemo  = new ThreadDemo();
        }
        return  threadDemo;

    }
    //测试threadlocal
    private String name;


    int a = 0;
     // boolean flag = false;

      /*
       * @Author zhang shaoxuan
       * @Description //测试写锁
       * @Date 20:12 2019/9/30
       * @Param
       * @return
       **/
    public void write () {
        //锁
        Lock writeLock = readWriteLock1.writeLock();
        System.out.println("线程"+Thread.currentThread().getName()+"进入写方法");

        writeLock.lock();
        try {
            System.out.println("线程"+Thread.currentThread().getName()+"获得了写锁");
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            writeLock.unlock();
            System.out.println("线程"+Thread.currentThread().getName()+"释放了写锁");

        }
    }

    /*
     * @Author zhang shaoxuan
     * @Description //测试读锁
     * @Date 20:13 2019/9/30
     * @Param
     * @return
     **/
    public  void read()  {
        Lock readLock = readWriteLock.readLock();
        readLock.lock();
        try{
            System.out.println("线程"+Thread.currentThread().getName()+"获得了==读==锁");
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            readLock.unlock();
            System.out.println("线程"+Thread.currentThread().getName()+"释放了==读==锁");
        }

    }

    public void run() {
        for (int i=0;i<10;i++){
           // ThreadDemo.getInstance().setName(Thread.currentThread().getName());
            //如果为空则设置初始化值
            if(null == threadDemoThreadLocal.get()){
                ThreadDemo threadDemo3 = new ThreadDemo();
                threadDemoThreadLocal.set(threadDemo3);
            }
            //设置名称
            threadDemoThreadLocal.get().setName(Thread.currentThread().getName());
            //打印名称
            System.out.println("当前线程名："+Thread.currentThread().getName()+"====name=="+  threadDemoThreadLocal.get().getName()+"--" +
                    "实例地址==="+threadDemoThreadLocal.get().hashCode());
          //  System.out.println("当前线程名："+Thread.currentThread().getName()+"====name=="+   ThreadDemo.getInstance().getName());
        }

        /*System.out.println("线程"  + "的初始value:" + threadDemoThreadLocal.get());

        for (int i = 0; i < 10; i++) {
           // System.out.println("线程"+Thread.currentThread().getName()+"====="+a++);
            threadDemoThreadLocal.set(threadDemoThreadLocal.get() + i);
           System.out.println("当前线程===="+Thread.currentThread().getName()+"==i==="+threadDemoThreadLocal.get());
        }*/


    }
    /*
     * @Author zhang shaoxuan
     * @Description //TODO
     * @Date 21:22 2019/9/23
     * @Param
     * @return
     **/

    public static void main(String[] args) throws Exception  {
        LockSupportTest();
    }

    /*
     * @Author zhang shaoxuan
     * @Description //lOCKsUPPORT测试
     * @Date 19:50 2019/10/12
     * @Param
     * @return
     **/
    public static void LockSupportTest(){
        Thread thread = new Thread(){
            @Override
            public void run() {
                System.out.println("进入线程并阻塞==");
                LockSupport.park();
                System.out.println("阻塞停止======");
            }
        };
        thread.start();
        try {
            System.out.println("主线程阻塞====");
            TimeUnit.SECONDS.sleep(5);
            System.out.println("主线程阻塞结束，调用unpark");
            LockSupport.unpark(thread);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /*
     * @Author zhang shaoxuan
     * @Description //calender测试
     * @Date 13:20 2019/10/12
     * @Param
     * @return
     **/
    public static  void CalenderTest(){
        //设置值
        Calendar calendar = Calendar.getInstance();
        System.out.println("不设置值的话默认使用当前系统时间===");
        System.out.println("获取时间DATE类型=="+calendar.getTime());
        System.out.println("获取时间的毫秒数类型=="+calendar.getTimeInMillis());
        System.out.println("获取日期所在周的第一天的日期,默认一周第一天是周日=="+calendar.getFirstDayOfWeek());
        //获取值
        System.out.println("获取当前日期在这一天是第几天，默认一号是1=="+calendar.get(Calendar.DAY_OF_MONTH));
        System.out.println( "获取日期年份，月份等也是如此获取==="+calendar.get(Calendar.YEAR));
        System.out.println("获取当前设定日期的月份，0-1对应1-12月，结果要加1====="+calendar.get(Calendar.MONTH)+1);//0-11对应1月到12月
        //运算值
         calendar.add(Calendar.MINUTE,10);//加十分钟之后的日期
        System.out.println("运算日期，加十分钟之后的时间==="+calendar.getTime());
        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(2019,9,12); //如果不设定具体时分秒，则以当前为准
        System.out.println(calendar1.getTime());
        //当前系统的纳秒数
        Long integer = new Long(TimeUnit.NANOSECONDS.convert(calendar.getTimeInMillis(),TimeUnit.MILLISECONDS));
        System.out.println("当前系统的纳秒数=="+String.valueOf(integer.longValue()));
        System.out.println("设定日期是否在当前日期之前，要求参数必须是Calender，否则false=="+calendar1.after(calendar));

    }

    /*
     * @Author zhang shaoxuan
     * @Description //Condition和lock做等待通知的例子
     * @Date 13:35 2019/10/11
     * @Param
     * @return
     **/
    public static void ConditionTest(){
        final ReentrantLock reentrantLock = new ReentrantLock();
        final Condition condition = reentrantLock.newCondition();
        reentrantLock.lock();

        new Thread(){
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(5);
                    flag=false;
                    reentrantLock.lock();
                    try{
                        condition.signal();
                    }finally {
                        reentrantLock.unlock();
                    }



                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
        try{
            while (flag) {
                //等待
                System.out.println("开始阻塞主线程====");
                condition.await();
                System.out.println("主线程释放===");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            reentrantLock.unlock();

        }
        System.out.println("主线程执行完毕====");

    }

    /*
     * @Author zhang shaoxuan
     * @Description //测试阻塞队列
     * @Date 10:32 2019/10/10
     * @Param
     * @return
     **/
    public static void testBlockingQueue() throws InterruptedException {
        DelayQueue onLineQueue = new DelayQueue();
        for (int i=0;i<10;i++){
       //     TimeUnit.SECONDS.sleep(1);
            onLineQueue.put(new onLine(i+1,"张三"+(i+1)));
        }
        System.out.println("offer结束==="+onLineQueue.size());
        while (true){
            TimeUnit.SECONDS.sleep(1);
            onLine onLine = (onLine) onLineQueue.take();
            System.out.println("用户姓名："+onLine.name+";开始时间==="+onLine.beginTime.getTime()+",结束时间"+onLine.endTime+",上机时长" +
                    ""+onLine.delay+"秒");
            if(onLineQueue.peek()==null){
                return;

            }

        }

    }

    /*
     * @Author zhang shaoxuan
     * @Description //delay接口实现
     * @Date 10:31 2019/10/11
     * @Param
     * @return
     **/
    private static class onLine implements  Delayed{
        Date beginTime = new Date() ;
        int delay;
        Date endTime;
        String name;

        public onLine(int delay,String name) {
            super();
            this.delay=delay;
            this.name=name;
            this.endTime = beginTime;
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(beginTime);
            calendar.add(Calendar.SECOND,delay);
            endTime = calendar.getTime();
        }

        @Override
        public long getDelay(TimeUnit d) {
            //返回负数会立刻执行.date.gettime返回的是毫秒数
            long delay1 = TimeUnit.MILLISECONDS.convert(delay,TimeUnit.SECONDS);
            return d.convert(beginTime.getTime()+delay1-System.currentTimeMillis(),TimeUnit.MILLISECONDS);
        }

        @Override
        public int compareTo(Delayed o) {

            return 0;
        }
    }
    /*
     * @Author zhang shaoxuan
     * @Description //测试single线程池
     * @Date 17:26 2019/10/9
     * @Param
     * @return
     **/
    private static void testSingledThreadPool(){
        //ScheduledExecutorService executorService = Executors.newScheduledThreadPool(3);
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i =0;i<10;i++){
            TestCallable  testRunnable = new TestCallable() ;
            //不延迟，每3s执行一次，是说每个3秒就执行一次，thisBeginTime = lastBegintime+period
         Future future =   executorService.submit(testRunnable);
            try {
                System.out.println(future.get().toString());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            //每个任务都等上一次任务完成后推迟5s开始，thisBeginTime = lastEndTime+delay
         //  executorService.scheduleWithFixedDelay(testRunnable,0,5,TimeUnit.SECONDS);

        }
        //不等待线程执行完
        //executorService.shutdown();
    }

    /*
     * @Author zhang shaoxuan
     * @Description //callable多线程内部类
     * @Date 14:55 2019/10/9
     * @Param
     * @return
     **/
    private static class TestCallable implements  Callable<Object>{

        @Override
        public Object  call() throws Exception {
           Integer i =  atomicInteger.getAndIncrement();
            TimeUnit.SECONDS.sleep( 1);
            System.out.println("call方法进行了调用。。。。"+i);
           if(i.equals(5)){
               int j  =1/0;
           }
            return i;
        }
    }
    private static class  TestRunnable extends  Thread{
        public TestRunnable(Integer integer) {
            this.integer = integer;
        }

        Integer integer ;

        @Override
        public void run() {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("执行run=====" + integer);
        }
    }



    /*
     * @Author zhang shaoxuan
     * @Description //测试submit和execute区别
     * @Date 10:19 2019/10/9
     * @Param
     * @return
     **/
    public static void testSubmitAndExecute() throws Exception {

      List<Future> list= new ArrayList<Future>();
      for(int i=0;i<10;i++){
          final int ii =i;
          TestCallable testCallable = new TestCallable();
          TestRunnable testRunnable = new TestRunnable(2);
          //提交Callble
        Future future = threadPoolExecutor.submit(testCallable);

         list.add(future);

      }
        for (Future future : list) {

            System.out.println(future.get()+"===="+future.toString());

        }
      //  TestRunnable testRunnable = new TestRunnable();
        //提交runnable
      //  Future future = threadPoolExecutor.submit(testRunnable,8);
       // System.out.println(future.get());


        threadPoolExecutor.shutdown();

    }

    /*
     * @Author zhang shaoxuan
     * @Description //测试线程池拒绝策略
     * @Date 15:20 2019/10/8
     * @Param
     * @return
     **/
    public static  void testThreadPoolRejectHandler(){
        //阻塞队列
        LinkedBlockingQueue linkedBlockingQueue = new LinkedBlockingQueue(1);
        //拒绝策略,AbortPolicy 会直接抛异常；CallerRunsPolicy会没有放到阻塞队列中的任务直接运行在main线程中，因为main方法中调用了executor，
        //DiscardOldestPolicy 丢弃掉最老的任务，直接执行阻塞之外的任务，处理的任务数量还是maxsize+blockingSize
        //DiscardPolicy 丢弃掉 不执行未进入阻塞队列中的任务
        RejectedExecutionHandler rejectedExecutionHandler = new ThreadPoolExecutor.DiscardPolicy();
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2,2,2,TimeUnit.SECONDS,linkedBlockingQueue,rejectedExecutionHandler);
        for (int i=0;i<3;i++){
            Thread thread = new Thread("thread//"+i){
                @Override
                public void run() {
                    try {
                        System.out.println("当前线程==="+Thread.currentThread().getName());
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {

                        e.printStackTrace();
                    }
                }
            };
            threadPoolExecutor.execute(thread);
        }
        try {
            Thread.sleep(5000);
            System.out.println("睡5s结束======"
            );
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //shutdown会中断workQueue阻塞队列的take方法，使得循环继续
         threadPoolExecutor.shutdown();

    }

    /*
     * @Author zhang shaoxuan
     * @Description 读写锁demo
     * @Date 20:06 2019/9/30
     *
     * @Param
     * @return
     **/
    public static void readWriteLockTest(){
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        final ThreadDemo threadDemo = new ThreadDemo();
        for (int i =0;i<10;i++){
            Thread thread = new Thread(String.valueOf(i)){
                @Override
                public void run() {
                    if ("pool-1-thread-5".equals(Thread.currentThread().getName())){
                        //写锁会将同一个ReadWriteLock对象的读和写锁都阻塞
                        threadDemo.write();
                    }else {
                        //读方法会将同一个ReadWriteLock对象的写锁给阻塞
                        threadDemo.read();
                    }

                }
            };
            executorService.execute(thread);
        }
        executorService.shutdown();

    }

    /*
     * @Author zhang shaoxuan
     * @Description //等待通知模型连接池测试
     * @Date 10:41 2019/9/30
     * @Param
     * @return
     **/
    public static void ConnectionPoolTest() throws InstantiationException, IllegalAccessException {
        final ConnectionPoolTest connectionPoolTest = new ConnectionPoolTest(10);
        //15条线程模拟连接不够用的情况
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i=0;i<15;i++){
            Thread thread = new Thread("thread=="+i){
                @Override
                public void run() {
                    ConnectionPoolTest.Connection connection =  connectionPoolTest.getConnection(4000);
                    System.out.println("线程"+Thread.currentThread().getName()+"获得的连接"+connection);

                    try {
                        TimeUnit.SECONDS.sleep(2);
                        System.out.println("线程"+Thread.currentThread().getName()+"连接使用完毕====");
                        connectionPoolTest.close(connection);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            };
            executorService.execute(thread);
        }
        executorService.shutdown();

    }

    /*
     * @Author zhang shaoxuan
     * @Description //测试threadLocal
     * @Date 16:57 2019/9/29
     * @Param
     * @return
     **/
    public static void threadLocalTest(){
       final ThreadDemo threadDemo =new  ThreadDemo();
        Thread thread1 =  new Thread(threadDemo,"thread1");
        Thread thread2 =  new Thread(threadDemo,"thread2");
        Thread thread3 =  new Thread(threadDemo,"thread3");
        thread3.start();
        thread1.start();
        thread2.start();

    }

   /*
    * @Author zhang shaoxuan
    * @Description //方法计时器
    * @Date 21:05 2019/9/29
    * @Param
    * @return
    **/
    public static Long beginCountTime(){
        //调用get方法会调用initialValue，向当前线程的ThreadLocalMap中首次存入值
      return   ThreadDemo.longThreadLocal.get();
    };
    public static void endCountTime(){
        System.out.println("方法耗时为===="+(System.currentTimeMillis()- ThreadDemo.beginCountTime()));
    }


    /*
     * @Author zhang shaoxuan
     * @Description //等待通知机制测试
     * @Date 13:13 2019/9/29
     * @Param
     * @return
     **/
    public static void waitNotifyTest() throws InterruptedException {
       final
        Thread waitT = new Thread("waitT"){
            @Override
            public void run() {
                synchronized (A){
                    try {
                        System.out.println("===进入线程开始等待===========");
                        while(INIT_VALUE==0){
                            A.wait();
                        }

                        System.out.println("========等待结束======");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }


                }
                //再次获得A的锁，测试notify方法会不会释放锁
                synchronized (A){
                    System.out.println("第二次获得A的锁，测试是否notify会释放锁=====");
                }

            }
        };
       waitT.start();
       TimeUnit.SECONDS.sleep(1);
       //notify报错则无法成功唤醒线程，线程会一直wait下去，notify和wait都要先获得对象锁，然后才能调用，否则报错
        //IlligalMOnitorStateException()
        //wait会使得当前占用对象锁的线程暂时释放掉对象的锁，且进入（timed）waiting
        // 但是notify不会,notify之后，如果对象锁还被占用，那么之前waiting状态的线程会转为blocked状态
        boolean flag = true;
        synchronized (A){
            A.notify();
            System.out.println("通知完毕=====");
           INIT_VALUE=1;

        }

       waitT.join();
        System.out.println("执行完毕========");
    }

    /*
     * @Author zhang shaoxuan
     * @Description 线程中断的测试方法
     * @Date 21:30 2019/9/28
     * @Param
     * @return
     **/
    public static  void interruptTest() throws Exception {
        //Thread.interrupt()给线程发中断信号，供被中断的线程接收，如果线程不接受并相应，则该中断无效
        //Thread.isInterrupted()接收中断信号，不清除中断标记，Thread.interrupted(这是类方法)接收信号，但是清楚中断标记
      //两个问题：1、程序抛interruptException会导致中断标记位清除与否，2、sleep方法未结束调用interrupt会怎么样
        Thread thread0 = new Thread(){
            @Override
            public void run() {
              /* try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/
                while (!this.isInterrupted()){

                    System.out.println("线程没有收到中断信号，继续执行===========");
                }
                System.out.println("线程收到中断信号，已中断并退出循环，但中断标记未清除=========");
                   /*if(isInterrupted()){
                        try {
                            //该异常手工抛出在线程runnable状态下并不会导致线程中断状态被清除为false
                            throw new InterruptedException("程序抛出中断异常，测试是否清除中断标记位");
                        } catch (InterruptedException e) {

                            e.printStackTrace();
                        }
                    }*/

                if(this.isInterrupted()){
                    System.out.println("再次调用isInterrupted(),线程中断标记果然还在=======");
                    return;
                }
                if(Thread.interrupted()){
                    System.out.println("调用了类方法Thread.interrupted(),应该已经清除了标记=======");

                }
                if(Thread.currentThread().isInterrupted()){
                    System.out.println("清除标记=====");
                }else{
                    System.out.println("最后验证发现interruted方法或者抛出中断异常确实清除了标记=======");
                }
            }

        };
        thread0.start();
            Thread.sleep(2000);
        System.out.println("主线程睡眠结束====");
       thread0.interrupt();
        thread0.join();
        System.out.println("程序执行完==========");

    }

    /*
     * @Author zhang shaoxuan
     * @Description 线程状态演示
     * @Date 10:10 2019/9/27
     * @Param
inn     **/
    public static void showThreadState() throws InterruptedException {
       Thread timewaiting  = new Thread("超时等待线程"){
           @Override
           public void run() {
               try {
                   synchronized (testSynchronized){
                       System.out.println("dfdfdfd");
                       Thread.sleep(9999999);
                   }

               } catch (Exception e) {
                   e.printStackTrace();
               }
           }
       };
       Thread blocking = new Thread("blocking线程"){
           @Override
           public void run() {
               synchronized (testSynchronized){
                   System.out.println("ddddd");
               }
           }
       };


       timewaiting.start();
       Thread.sleep(1000);
        blocking.start();
    }

    /*
     * @Author zhang shaoxuan
     * @Description 测试优先级
     * @Date 9:38 2019/9/27
     * @Param
     * @return
     **/
    public static void testThreadPriority() throws InterruptedException {
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(2);
        Thread thread = new Thread("thread"){
            @Override
            public void run() {
                System.out.println("当前线程名"+Thread.currentThread().getName()+"当前线程优先级"+Thread.currentThread().getPriority());
            }
        };
        Thread thread1 = new Thread("thread1"){
            @Override
            public void run() {
                System.out.println("当前线程名"+Thread.currentThread().getName()+"当前线程优先级"+Thread.currentThread().getPriority());
            }
        };
        //for (int i = 0;i<10;i++){

            thread.setPriority(6);
            thread1.setPriority(1);
           thread.start();
           thread1.start();

      //  }
      //  fixedThreadPool.shutdown();


    }


    /*
     * @Author zhang shaoxuan
     * @Description //原子操作练习
     * @Date 20:11 2019/9/26
     * @Param
     * @return
     **/
    public static void atomicClassTest(){
       ThreadDemo threadDemo = new ThreadDemo();
       new Thread(threadDemo).start();
       new Thread(threadDemo).start();
    }

    /*
     * @Author zhang shaoxuan
     * @Description volatile例子
     * @Date 10:23 2019/9/25
     * @Param
     * @return
     **/
    public static void testVolatile(){

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (ThreadDemo.class){
                    while (!isOver){
                        //System.out.println("isOver的值==="+isOver);
                    };
                    System.out.println("thread ----- true");
                }

            }
        });
        thread.start();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        isOver = true;

        System.out.println("main ----- true");
    }
    public static  void testSynchronized(){
        synchronized (testSynchronized1){
            for (int i =0;i<5;i++){
                testSynchronized++;
                System.out.println("testSynchronized的值为======"+testSynchronized);
            }

        }
    }


    /*
     * @Author zhang shaoxuan
     * @Description //演示volatile的使用，
     * @Date 22:19 2019/9/23
     * @Param
     * @return
     **/
    public static void volatileDemo() throws InterruptedException {
        // 读线程
        new Thread(){
            @Override
            public void run() {
                int localValue = INIT_VALUE;
                while (localValue < MAX_VALUE){

                    if (localValue != INIT_VALUE){
                        System.out.println("0局部变量为==="+localValue);

                        System.out.println("INIT_VALUE====="+INIT_VALUE);
                        System.out.println("The value update to \t" + INIT_VALUE);
                        localValue = INIT_VALUE;
                        System.out.println("1局部变量为==="+localValue);

                    }

                }
            }


        }.start();
        new Thread(){
            @Override
            public void run() {
                int localValue = INIT_VALUE;
                while (localValue < MAX_VALUE){
                    System.out.println("update value update to \t" + (++localValue));
                    INIT_VALUE = localValue;
                    System.out.println("INIT_VALUE更新为===== \t" + INIT_VALUE);
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }


        }.start();
      /*  ThreadDemo threadDemo = new ThreadDemo();

        Thread thread0 = new Thread(threadDemo,"T1");
        Thread thread1 = new Thread(threadDemo,"T2");
            thread0.start();
            thread1.start();*/



    }

    /*
     * @Author zhang shaoxuan
     * @Description 死锁demo
     * @Date 22:18 2019/9/23
     * @Param
     * @return
     **/
    public static void deadLockDemo(){
        Thread thread0 = new Thread("thread0"){
            @Override
            public void run() {
                synchronized (A){
                    System.out.println(Thread.currentThread().getName()+"获得A的锁===========");
                    //使得线程都能够运行起来
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    synchronized (B){
                        System.out.println(Thread.currentThread().getName()+"获得B的锁===========");
                    }
                }
            }
        };
        Thread thread1 = new Thread("thread1"){
            @Override
            public void run() {
                synchronized (B){
                    System.out.println(Thread.currentThread().getName()+"获得B的锁==========");
                    synchronized (A){
                        System.out.println(Thread.currentThread().getName()+"获得A的锁==========");
                    }
                }
            }
        };
        thread0.start();
        thread1.start();


    }


}
