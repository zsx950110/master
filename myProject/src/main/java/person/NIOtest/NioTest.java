package person.NIOtest;


import java.io.*;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @Author: zsx
 * @DateTime: 2020/2/25 12:57
 * @Description:
 */
public class NioTest {
    public static void main(String[] args) {
      //  scatterAndGatheringTest();
        copyWithNIO();
    }

    /**
     * @Author: zsx
     * @Description: 测试buffer缓冲区
     * @DateTime: 2020/2/25 13:36
     * @Params: [args]
     * @Return void
     */
    public static void testBuffer() {
        //分配缓存区的大小
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024 * 1024);
        //添加数据到缓存区
        byteBuffer.put("zhangshaoxuan".getBytes());
        System.out.println("---------------添加数据之后-------------");
        System.out.println("capacity---" + byteBuffer.capacity());
        System.out.println("limit---" + byteBuffer.limit());
        System.out.println("position---" + byteBuffer.position());
        //   System.out.println("mark---"+byteBuffer.mark());
        //使用flip（）切换为读模式
        byteBuffer.flip();
        //创建limit大小的数组，一次性读完
        System.out.println("--------------使用flip（）切换为读模式之后-------------");

        System.out.println("limit---" + byteBuffer.limit());
        System.out.println("position---" + byteBuffer.position());
        byte[] bs = new byte[byteBuffer.limit() - 4];
        byteBuffer.get(bs);
        System.out.println("读到的字符-----" + new String(bs, 0, bs.length));
        System.out.println("limit---" + byteBuffer.limit());
        System.out.println("position---" + byteBuffer.position());
        System.out.println("-------------读完之后直接写--------------");
        byteBuffer.put("san".getBytes());
        System.out.println("limit---" + byteBuffer.limit());
        System.out.println("position---" + byteBuffer.position());
        System.out.println("--------------使用clear方法后-----------");
        byteBuffer.clear();
        System.out.println("limit---" + byteBuffer.limit());
        System.out.println("position---" + byteBuffer.position());


    }

    /**
     * @Author: zsx
     * @Description: 获得通道的落地方法
     * @DateTime: 2020/2/25 15:18
     * @Params:
     * @Return
     */
    public static void getChannel() {

        try {
            //本地IO
            FileInputStream fileInputStream = new FileInputStream("");

            FileChannel channel = fileInputStream.getChannel();
            RandomAccessFile randomAccessFile = new RandomAccessFile("", "");
            FileChannel channel1 = randomAccessFile.getChannel();
            //网络IO
            Socket socket = new Socket();
            SocketChannel channel2 = socket.getChannel();
            ServerSocket serverSocket = new ServerSocket();
            ServerSocketChannel channel3 = serverSocket.getChannel();
            DatagramSocket datagramSocket = new DatagramSocket();
            DatagramChannel channel4 = datagramSocket.getChannel();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @Author: zsx
     * @Description: 普通io复制
     * @DateTime: 2020/2/25 15:38
     * @Params:
     * @Return
     */
    public static void copyWithIO() {
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            String path = "D:\\sofa_workspace2019-2-10.zip";
            String path1 = "F:\\技术文件\\sofa_workspace2019-2-10.zip";

            fileInputStream = new FileInputStream(path);
            fileOutputStream = new FileOutputStream(path1);
            byte[] temp = new byte[1024 * 1024];
            int length = 0;
            long start = System.currentTimeMillis();
            while ((length = fileInputStream.read(temp)) > 0) {
                fileOutputStream.write(temp);
            }
            long end = System.currentTimeMillis();
            System.out.println("普通IO读取文件大小为：" + new File(path).length() / 1024 + "KB---用时：" + (end - start) / 1000 + "s");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fileInputStream.close();
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @Author: zsx
     * @Description: nio复制文件, 以下写法经测试，与IO的效率差不多，无太大差别，都设置了数组缓冲，读到数组中
     * 然后在写
     * @DateTime: 2020/2/25 15:38
     * @Params:
     * @Return
     */
    public static void copyWithNIO() {
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;
        FileChannel channel = null;
        FileChannel channel1 = null;
        String path = "D:\\sofa_workspace2019-2-10.zip";
        String path1 = "F:\\技术文件\\sofa_workspace2019-2-10.zip";
        try {
            fileInputStream = new FileInputStream(path);
            fileOutputStream = new FileOutputStream(path1);
            //获得通道
            channel = fileInputStream.getChannel();
            channel1 = fileOutputStream.getChannel();
            //分配缓冲区
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024 * 1024);
            long start = System.currentTimeMillis();
            //开始读
            while (channel.read(byteBuffer) > -1) {
                //切换读写模式，将position和limit置于合适的位置
                byteBuffer.flip();
                //开始写
                channel1.write(byteBuffer);
                //重置limit和position标志位
                byteBuffer.clear();
            }
            long end = System.currentTimeMillis();
            System.out.println("NIO读取文件大小为：" + new File(path).length() / 1024 + "KB---用时：" + (end - start) / 1000 + "s");


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {

        } finally {
            try {
                channel1.close();
                fileOutputStream.close();
                channel.close();
                fileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * @Author: zsx
     * @Description: 另一种NIO的使用方式，transferto,效率是IO在设置最适当数组的情况下的效率的2倍
     * @DateTime: 2020/2/25 16:37
     * @Params:
     * @Return
     */
    public static void copyWithNIO1() {
        String path = "D:\\sofa_workspace2019-2-10.zip";
        String path1 = "F:\\技术文件\\sofa_workspace2019-2-10.zip";
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;
        FileChannel inChannel = null;
        FileChannel outChannel = null;
        try {
            fileInputStream = new FileInputStream(path);
            fileOutputStream = new FileOutputStream(path1);
            //获得channel
            inChannel = fileInputStream.getChannel();
            outChannel = fileOutputStream.getChannel();
            //使用transferto方法
            long start = System.currentTimeMillis();
            inChannel.transferTo(0, inChannel.size(), outChannel);
            long end = System.currentTimeMillis();
            System.out.println("NIO通过transferto读取文件大小为：" + new File(path).length() / 1024 + "KB---用时：" + (end - start) / 1000 + "s");

            outChannel.close();
            inChannel.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }

    }

    /**
     * @Author: zsx
     * @Description: 分散读取和聚集写入
     * @DateTime: 2020/2/27 14:42
     * @Params:
     * @Return
     */
    public static void scatterAndGatheringTest() {
        String path = "D:\\sofa_workspace2019-2-10.zip";
        String path1 = "F:\\技术文件\\sofa_workspace2019-2-10.zip";
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;
        FileChannel inChannel = null;
        FileChannel outChannel = null;
        try {
            fileInputStream = new FileInputStream(path);
            fileOutputStream = new FileOutputStream(path1);
            //获得通道
            inChannel = fileInputStream.getChannel();
            outChannel = fileOutputStream.getChannel();
            //定义分散度数组
            ByteBuffer[] byteBuffers = new ByteBuffer[]{ByteBuffer.allocate(1024 * 1024), ByteBuffer.allocate(1024 * 1024)};
            long start = System.currentTimeMillis();
            while(inChannel.read(byteBuffers)>-1){
               for (ByteBuffer byteBuffer:byteBuffers){
                   byteBuffer.flip();
                   outChannel.write(byteBuffer);
                   byteBuffer.clear();
               }
            };
            long end = System.currentTimeMillis();
            System.out.println("耗时======"+(end-start)/1000);
        } catch (Exception e) {

        } finally {
        }


    }

}
