package service;

import pojo.Book;
import pojo.ToDo;

import java.util.List;

//公共接口
public interface IOperationService {
    String bookNames();
    void redisSave(List<Book> books);
    void saveBook();
    List<ToDo>  listToDos();
}
