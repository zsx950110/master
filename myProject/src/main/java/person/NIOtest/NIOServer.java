package person.NIOtest;

import org.hibernate.sql.Select;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @Author: zhangshaoxuan
 * @DateTime: 2020/2/27 17:36
 * @Description: nio的服务端
 */
public class NIOServer {
    public static void main(String[] args) {
        NIOnoblockingServer();
    }
    /**
    * @Author: zsx
    * @Description: nio阻塞型 即使用accept方法阻塞线程
    * @DateTime: 2020/2/28 16:02
    * @Params:
    * @Return
    */
    public static  void  INOblockingServer(){
        FileOutputStream fileOutputStream = null;
        FileChannel outChannel = null;
        ServerSocketChannel serverSocketChannel = null;
        try {
            //ctrl+alt+i 自动缩进
            serverSocketChannel = ServerSocketChannel.open();
            // serverSocketChannel.configureBlocking(false);
            serverSocketChannel.bind(new InetSocketAddress(9010));
            System.out.println("------------server开始监听9010-------------------");
            int i =0;
            long start = System.currentTimeMillis();
            while (i<2){
                fileOutputStream = new FileOutputStream("F:\\技术文件\\aaaaa"+i+".zip");
                outChannel = fileOutputStream.getChannel();
                SocketChannel socketChannel = serverSocketChannel.accept();
                //2.定义buffer
                ByteBuffer byteBuffer = ByteBuffer.allocate(1024*1024);
                //读
                System.out.println("---------------server开始读写--------------");
                while (socketChannel.read(byteBuffer)!=-1){
                    //改变读写标志位position和limit，切换读写模式
                    byteBuffer.flip();
                    System.out.println("------在写中-----");
                    //写
                    System.out.println(byteBuffer.toString());
                    outChannel.write(byteBuffer);
                    byteBuffer.clear();
                }
                i++;
            }
            long end = System.currentTimeMillis();
            System.out.println("总共接收了"+i+"个socket连接，用时"+(end-start)/1000+"s");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // serverSocketChannel.close();
                // outChannel.close();
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /**
    * @Author: zsx
    * @Description: nio非阻塞服务端，+selector
    * @DateTime: 2020/2/28 16:01
    * @Params:
    * @Return
    */
    public static void NIOnoblockingServer(){
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        ServerSocketChannel serverSocketChannel = null;
        Selector selector = null;
        try {
            //获得服务端通道
             serverSocketChannel = ServerSocketChannel.open();
             //绑定端口
             serverSocketChannel.bind(new InetSocketAddress(9020));
             //设置为非阻塞
             serverSocketChannel.configureBlocking(false);
             //获得选择器
            selector = Selector.open();
            /**
             * 通道注册到选择器上,指定接收监听通道事件，第二个参数，是事件，即要注册监听的事件
             *有四种 ：Connect Accept Read  Write
             */
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            int i =0;
            System.out.println("----到达while select()前端------");
            //select（）方法如果没有socket连接就会阻塞,目前一直循环不出来，不知道原因
            //select()没有连通的socket时返回0
            while(true){
                /**
                 * select()方法阻塞直到有个socket是就绪的
                 * 返回值表示当前有多少通道就绪
                 */
                int s = selector.select();
                if (s==0){
                    continue;
                }
                System.out.println("s========="+s);
                System.out.println("--------select（）方法>0-----------");
                //获取所有的键，其实是获得了这个通道监听到的所有的活跃事件
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()){
                    /**
                     * selectionKey这个对象里包括以下：
                     * 上面被注册的事件
                     * Channel
                     * Selector
                     */
                    SelectionKey selectionKey = iterator.next();
                    //Tests whether this key's channel is ready to accept a new socket
                    //     * connection.
                    if (selectionKey.isAcceptable()){
                        System.out.println("---------accept（）方法前端-----");
                       SocketChannel client =  serverSocketChannel.accept();
                       client.configureBlocking(false);
                       //拿到连接时为了读
                       client.register(selector,SelectionKey.OP_READ);
                    }else if (selectionKey.isReadable()){
                        //接收的事件放到别的线程中处理
                       //executorService.submit(new NIOServer().new processSelector(selectionKey,i));
                        new NIOServer().new processSelector(selectionKey,i).run();

                    }
                    // 10. 取消选择键(已经处理过的事件，就应该取消掉了)
                    iterator.remove();
                    i++;
                }
            }
           // executorService.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                selector.close();
                serverSocketChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    class  processSelector implements  Runnable{
        SelectionKey selectionKey;
       FileOutputStream fileOutputStream;
       FileChannel outChannel;
       SocketChannel socketChannel;
       int i =0;
        public processSelector(SelectionKey selectionKey,int i) {
            this.selectionKey = selectionKey;
            this.i = i;
        }

        @Override
        public void run() {
            try {
                fileOutputStream = new FileOutputStream("F:\\技术文件\\aaaaa"+i+".zip");
                outChannel = fileOutputStream.getChannel();
                //获得通道
                socketChannel = (SocketChannel) selectionKey.channel();
                ByteBuffer byteBuffer = ByteBuffer.allocate(1024*1024);
                System.out.println("------------------server开始读写---------");
                while (socketChannel.read(byteBuffer)>0){
                    byteBuffer.flip();
                    outChannel.write(byteBuffer);
                    byteBuffer.clear();
                }
                System.out.println("------------------server完成读写---------");

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    //这里需要等待Client的socketchannel自己断开连接再调用close（）
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    outChannel.close();
                    fileOutputStream.close();
                    //这里一定要关闭，否则select（）方法的返回值会一直大于0
                   // socketChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


        }
    }
}
