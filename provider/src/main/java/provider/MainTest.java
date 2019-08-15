package provider;

import common.test.StringUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class MainTest {
    public static void main(String[] args) {
        String date1 = "2019-08-13";
       SimpleDateFormat dateFormat = new  SimpleDateFormat("yyyy/MM/dd");
       String date2 = date1.replace('-','/');
        System.out.println(date2);
        try {
            dateFormat.parse(date2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return;

     /*   ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring-dubbo-provider.xml");
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        System.out.println("开始展示beanname");
        for (String beanDefinitionName : beanDefinitionNames) {
            System.out.println(beanDefinitionName);
        }
        try {
            Thread.sleep(1000000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("加载成功");*/
    }
}
