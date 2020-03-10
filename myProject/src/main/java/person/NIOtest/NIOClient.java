package person.NIOtest;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @Author: zhangshaoxuan
 * @DateTime: 2020/2/27 17:36
 * @Description: nio的客户端
 */
public class NIOClient {
    public static void main(String[] args) {
        NIONoBlocking();
    }
    /**
    * @Author: zsx
    * @Description: 测试内部类多线程多个socket客户端
    * @DateTime: 2020/2/28 15:58
    * @Params:
    * @Return
    */
    public  static  void  NIOBlocking(){
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i =0;i<2;i++){
            executorService.submit(new NIOClient().new threadNIO(true));
        }
        executorService.shutdown();
    }

    /**
    * @Author: zsx
    * @Description: 多线程向server发送请求
    * @DateTime: 2020/2/28 15:09
    * @Params:
    * @Return
    */
    class   threadNIO implements  Runnable{
        String path =  "F:\\技术文件\\spring-framework-4.3.23.RELEASE.zip";
         FileInputStream inputStream = null;
        SocketChannel socketChannel = null;
         FileChannel fileChannel = null;
         boolean isblocking = true;

        public threadNIO(boolean isblocking) {
            this.isblocking = isblocking;
        }

        @Override
            public void run() {
                try {
                    inputStream = new FileInputStream(path);
                    ////获得通道
                    socketChannel  =SocketChannel.open(new InetSocketAddress("127.0.0.1",9020));
                    if (!isblocking){
                        socketChannel.configureBlocking(false);
                    }
                    fileChannel = inputStream.getChannel();
                    //分配buffer
                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024*1024);
                    //读写
                    System.out.println("-------------client开始读写-----------------");
                    while(fileChannel.read(byteBuffer)!=-1){
                        //切换读写模式
                        byteBuffer.flip();
                        System.out.println(byteBuffer.toString());
                        socketChannel.write(byteBuffer);
                        byteBuffer.clear();
                    }
                    System.out.println("----------client读写完成--------------------");

                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        socketChannel.close();
                        fileChannel.close();
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
    }
    /**
    * @Author: zsx
    * @Description: 非阻塞的NIO模拟socket客户端
    * @DateTime: 2020/2/28 16:00
    * @Params:
    * @Return
    */
    public  static void NIONoBlocking(){
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i =0;i<10;i++){
            executorService.submit(new NIOClient().new threadNIO(false));
        //new NIOClient().new threadNIO(false).run();
        }
        executorService.shutdown();
    }
}
