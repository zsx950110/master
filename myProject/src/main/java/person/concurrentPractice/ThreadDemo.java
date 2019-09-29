package person.concurrentPractice;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

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
    AtomicInteger atomicInteger = new AtomicInteger(0);
    int a = 0;
      boolean flag = false;
    public void write () throws InterruptedException {
        a = 1;
        flag = true;
        for (int i=0;i<1000;i++){

        }
        Thread.sleep(1);
        System.out.println("写完===========");
        System.out.println("写完===========");

    }
    public  void read(){
        if(flag){
            System.out.println(a+"==============a的值");
            System.out.println("读完===========");

        }
    }

    public void run() {
        try {
           // synchronized (testSynchronized){

                System.out.println("方法开始");

                for (int i =0;i<5;i++){
                     // this.getIn();

                }
         //   System.out.println(atomicInteger.get());

           // }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    /*
     * @Author zhang shaoxuan
     * @Description //TODO
     * @Date 21:22 2019/9/23
     * @Param
     * @return
     **/

    public static void main(String[] args) throws Exception  {



    }

    /*
     * @Author zhang shaoxuan
     * @Description //等待通知机制测试
     * @Date 13:13 2019/9/29
     * @Param
     * @return
     **/
    public static void waitNotifyTest(){

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
