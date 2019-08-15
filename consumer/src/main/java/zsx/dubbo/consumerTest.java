package zsx.dubbo;

import common.service.IToDoService;
import common.test.StringUtil;
import common.vo.ToDo;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Iterator;

public class consumerTest {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring-dubbo-consumer.xml");
        System.out.println("RPC开始。。。。");
        IToDoService iToDoService =(IToDoService) applicationContext.getBean("todoService");
        ToDo toDo  =iToDoService.getToDoByTaskId("1");
        System.out.println(toDo.toString());

    }
}
