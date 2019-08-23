package provider;

import com.alibaba.dubbo.container.Main;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import provider.serviceImpl.Operations;

import java.io.IOException;

public class MainTest {
    public static void main(String[] args) {
       ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:application-context.xml");
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
       Operations operations  =(Operations)applicationContext.getBean("operations");

       operations.listToDos();
        System.out.println("开始展示beanname");
        for (String beanDefinitionName : beanDefinitionNames) {
            System.out.println(beanDefinitionName);
        }
        System.out.println("开始调用main。。。。。");
        //      Main.main(args);
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
