package provider.serviceImpl;

//import com.alibaba.dubbo.config.annotation.Service;
import common.pojo.ToDo;
import common.service.IToDoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//@Service //dubbo的service，做两件事 创建bean ，以接口暴露，相当于原来配置的bean和暴露
public class ToDoServiceImpl implements IToDoService {
    Logger logger = LoggerFactory.getLogger(ToDoServiceImpl.class);
    public ToDo getToDoByTaskId(String id) {
        ToDo toDo = new ToDo();
        toDo.setAccount("23");
        toDo.setId("1212");
        System.out.println("打印：这里是服务提供者实现类》》》》》》》》》》");
        toDo.setSubject("服务提供者实现类");
        return toDo;
    }
}
