package dubbo.test;




import com.alibaba.dubbo.config.annotation.Reference;
import common.pojo.ToDo;
import common.service.IOperationService;
import common.service.IToDoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:springmvc-servlet.xml")
public class consumerTest {

    @Reference
    IOperationService iOperationService;
  public static void main(String[] args) {
       ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:springmvc-servlet.xml");
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
       // ToDo toDo  =iToDoService.getToDoByTaskId("1");
       List<ToDo> list = iOperationService.listToDos();
       System.out.println(list.get(0).getSubject());
    //    System.out.println(toDo.toString());

    }
}
