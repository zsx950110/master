package provider;

import com.alibaba.dubbo.container.Main;

import java.io.IOException;
public class MainTest {
    public static void main(String[] args) {
     /*  ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring-dubbo-provider.xml");
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();

        System.out.println("开始展示beanname");
        for (String beanDefinitionName : beanDefinitionNames) {
            System.out.println(beanDefinitionName);
        }*/
     //   Main.main(args);
        try {
            //阻塞使得provider能一直连这zk
            System.out.println("阻塞中。。。");
            System.in.read(); // press any key to exit

            System.out.println("结束阻塞。。。。");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("加载成功");
    }
}
