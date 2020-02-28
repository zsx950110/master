package person.NIOtest;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

/**
 * @Author: zhangshaoxuan
 * @DateTime: 2020/2/27 17:36
 * @Description: nio的客户端
 */
public class NIOClient {
    public static void main(String[] args) {
        String path =  "D:\\sofa_workspace-20181224.zip";
      //  String path1 =  "F:\\技术文件\\qqq.txt";

        FileInputStream inputStream = null;
        Socket socket = null;
        SocketChannel socketChannel = null;
        FileChannel fileChannel = null;
        try {
            inputStream = new FileInputStream(path);
          //   socket =  new Socket("127.0.0.1",9070);
            ////获得通道
            socketChannel  =SocketChannel.open(new InetSocketAddress("127.0.0.1",9010));
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
             //  socket.close();
               fileChannel.close();
              inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
