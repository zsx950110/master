package person.NIOtest;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
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
        ServerSocketChannel serverSocketChannel = null;
        try {
            //ctrl+alt+i 自动缩进
            fileOutputStream = new FileOutputStream("F:\\技术文件\\aaaaa.zip");
            outChannel = fileOutputStream.getChannel();
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress(9010));
            System.out.println("------------server开始监听9010-------------------");
            while (true){
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
                System.out.println("-------------server读写完成-----------------");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                serverSocketChannel.close();
                outChannel.close();
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }



    }
}
