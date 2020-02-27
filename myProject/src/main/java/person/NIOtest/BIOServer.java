package person.NIOtest;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Author: zhangshaoxuan
 * @DateTime: 2020/2/27 16:51
 * @Description: bio服务端测试
 */
public class BIOServer {
    public static void main(String[] args) {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            ServerSocket serverSocket = new ServerSocket(8090);
            Socket socket = serverSocket.accept();
            inputStream = socket.getInputStream();
            //FileInputStream fileInputStream = new FileInputStream(inputStream);
            byte [] bytes = new byte[1024];
            String path ="F:\\技术文件\\aaaaa.pdf";
            outputStream = new FileOutputStream(path);
           while ( inputStream.read(bytes) >-1){
              outputStream.write(bytes);
           }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }

    }
}
