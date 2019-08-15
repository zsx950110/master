package provider.serviceImpl;

import common.service.IToDoService;
import common.vo.ToDo;

public class ToDoServiceImpl implements IToDoService {
    public ToDo getToDoByTaskId(String id) {
        ToDo toDo = new ToDo();
        toDo.setAccount("23");
        toDo.setId("1212");
        return toDo;
    }
}
