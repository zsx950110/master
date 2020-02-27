package zsx.dubbo;

import com.sun.xml.internal.bind.v2.TODO;

import common.pojo.ToDo;
import common.service.IOperationService;
import common.service.IToDoService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.util.List;


public class ConsumerOperation {


    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring-dubbo-consumer.xml");
        System.out.println("RPC开始。。。。");
      IOperationService iOperationService=(IOperationService) applicationContext.getBean("operationService");
       // IToDoService iToDoService=(IToDoService) applicationContext.getBean("toDoService");
       // ToDo todo  =iToDoService.getToDoByTaskId("3232");
    //    System.out.println(todo.toString());
        System.out.println("RPC ending>>>>>>>>>>>>>>>");
       List<ToDo> list = iOperationService.listToDos();
          System.out.println("第一个元素》》》》》》》》》》"+list.get(0).getSubject());
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
