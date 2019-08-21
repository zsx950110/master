package dubbo.test;

import com.alibaba.dubbo.config.annotation.Reference;
import common.service.IToDoService;
import common.vo.ToDo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-dubbo-consumer.xml")
public class consumerTest {
    @Reference
     IToDoService iToDoService;

  public static void main(String[] args) {
       ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring-dubbo-consumer.xml");
       System.out.println("RPC开始。。。。");
      // iToDoService=(IToDoService) applicationContext.getBean("toDoService");
       System.out.println("RPC ending>>>>>>>>>>>>>>>");
       //ToDo toDo  =iToDoService.getToDoByTaskId("1");
     //  System.out.println(toDo.toString());
   }
   @Test
    public  void mainTest() {
       // ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring-dubbo-consumer.xml");
        System.out.println("RPC开始。。。。");
        // =(IToDoService) applicationContext.getBean("toDoService");
        System.out.println("RPC ending>>>>>>>>>>>>>>>");
        ToDo toDo  =iToDoService.getToDoByTaskId("1");
        System.out.println(toDo.toString());

    }
}
