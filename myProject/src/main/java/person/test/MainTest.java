package person.test;import java.io.*;import java.util.Properties;public class MainTest {    public static void main(String[] args) throws IOException {        //tomcat连接jndi https://www.cnblogs.com/dgwblog/p/9669086.html        //https://www.cnblogs.com/super-yu/p/8622463.html        //https://blog.csdn.net/zbajie001/article/details/78437647       //读properties文件        //方法1，getResourceAsStream这个拿的是项目相对路径(即从项目的跟路径算起)，跟路径得看编译后的项目路径        System.out.println("获得当前项目跟路径："+Thread.currentThread().getContextClassLoader().getResource("").getPath());       InputStream inputStream0 =Thread.currentThread().getContextClassLoader().getResourceAsStream("\\xml\\dbConfig.properties");        Properties properties0 = new Properties();        properties0.load(new InputStreamReader(inputStream0));        System.out.println(properties0.getProperty("driverClassName"));        //方法2，相对路径，即编译后要加载的文件相对于当前类的路径，可以用相对路径符号加载        InputStream inputStream =MainTest.class.getResourceAsStream("..\\..\\xml\\dbConfig.properties");        Properties properties = new Properties();        properties.load(new InputStreamReader(inputStream));        System.out.println(properties.getProperty("driverClassName"));        //方法3 通过文件流的绝对路径（物理路径）加载，可以通过以下加载绝对路径文件        System.out.println("获得当前类的类路径："+MainTest.class.getResource("").getPath());        System.out.println("当前项目的根路径"+MainTest.class.getResource("/").getPath());       InputStream inputStream1 = new BufferedInputStream(new FileInputStream(new File(MainTest.class.getResource("/").getPath()+"xml/dbConfig.properties")));       properties.load(new InputStreamReader(inputStream1));        System.out.println(properties.getProperty("driverClassName"));        //方法4 需要资源放在系统的静态资源根路径，如resource路径        InputStream inputStream2 = ClassLoader.getSystemResourceAsStream("xml/dbConfig.properties");        properties.load(new InputStreamReader(inputStream2));        System.out.println(properties.getProperty("username"));        System.out.println("获得当前类的类路径："+MainTest.class.getResource("").getPath());        System.out.println("当前项目的根路径"+MainTest.class.getResource("/").getPath());    }}