package common.service;


import common.pojo.ToDo;

public interface IToDoService {
    ToDo getToDoByTaskId(String id);
}
