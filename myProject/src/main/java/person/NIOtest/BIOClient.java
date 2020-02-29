package person.NIOtest;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * @Author: zhangshaoxuan
 * @DateTime: 2020/2/27 16:51
 * @Description: bio的socket客户端测试
 */
public class BIOClient {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        OutputStream outputStream = null;
        InputStream inputStream = null;
        Socket socket = null;
        try {
                socket = new Socket("127.0.0.1",8090);
                System.out.println("------请输入文件地址---------:");
                String temp =  scanner.nextLine();
                inputStream = new FileInputStream(temp) ;
                outputStream = socket.getOutputStream();
                byte[] bytes = new byte[1024];
                while(inputStream.read(bytes)>-1){
                    outputStream.write(bytes);
                }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
