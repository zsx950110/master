package person.concurrentPractice;


import com.sun.corba.se.spi.orbutil.threadpool.ThreadPool;

import java.util.LinkedList;

/*
 * @Author zhang shaoxuan
 * @Description 等待超时模型模拟数据库连接池
 * @Date 21:23 2019/9/23
 *
 **/
public class ConnectionPoolTest {


//双向链表存放连接
private LinkedList<Connection> connectionLinkedList = new LinkedList<>();

    /*
     * @Author zhang shaoxuan
     * @Description 构造方法存放连接
     * @Date 9:38 2019/9/30
     * @Param
     * @return
     **/
    public ConnectionPoolTest(Integer initialMaxConnection) throws IllegalAccessException, InstantiationException {
        for (int i = 0 ;i<initialMaxConnection;i++){
            //获取实例存放进去
            connectionLinkedList.addLast(new Connection());
        }
    }

    /*
     * @Author zhang shaoxuan
     * @Description //获得连接
     * @Date 9:47 2019/9/30
     * @Param 请求超时的毫秒数
     * @return
     **/
    public Connection getConnection(long millis) {
        //完全等待
        if (millis<=0){
            synchronized (connectionLinkedList){
                while (connectionLinkedList.isEmpty()){
                    try {
                        connectionLinkedList.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            return connectionLinkedList.pop();

        }else{
            //超时等待
            //开始时间
            Long begin =System.currentTimeMillis();

            synchronized (connectionLinkedList){
                //未超时，且链表为空，则等待
                while ((System.currentTimeMillis()-begin)<millis&&connectionLinkedList.isEmpty()){
                    try {
                        System.out.println("当前线程为=="+Thread.currentThread().getName()+"进入等待队列===等待时长="+millis);
                        connectionLinkedList.wait(millis);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
                System.out.println("当前线程为=="+Thread.currentThread().getName()+"退出等待队列====");

                //出了循环如果依然为空，则说明是超时
                if (connectionLinkedList.isEmpty()){
                    System.out.println(Thread.currentThread().getName()+"===超时等待");
                    return  null;
                }else{
                    //否则返回并弹出第一个
                    return connectionLinkedList.pop();
                }
            }

        }


    }
    public void close(Connection connection){
        if (connection!=null){
            connectionLinkedList.addLast(connection);
            synchronized (connectionLinkedList){
                //通知所有的等待的线程，当前有连接可以获得
                connectionLinkedList.notifyAll();
                System.out.println(Thread.currentThread().getName()+"释放连接并已通知===");
            }
        }
    }
    //模拟连接类
    public class  Connection {

    }
}
