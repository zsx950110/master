package person.NIOtest;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @Author: zhangshaoxuan
 * @DateTime: 2020/2/27 17:36
 * @Description: nio的服务端
 */
public class NIOServer {
    public static void main(String[] args) {
        FileOutputStream fileOutputStream = null;
        FileChannel outChannel = null;
        try {
             fileOutputStream = new FileOutputStream("F:\\技术文件\\aaaaa.pdf");
            ServerSocket serverSocket = new ServerSocket(9090);
            //1获取通道
            ServerSocketChannel serverSocketChannel = serverSocket.getChannel();
             outChannel = fileOutputStream.getChannel();
            //2.定义buffer
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024*1024);
            SocketChannel accept = serverSocketChannel.accept();
            //读
            while (accept.read(byteBuffer)>-1){
                //写
                outChannel.write(byteBuffer);
            }
            ;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                outChannel.close();
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }



    }
}
