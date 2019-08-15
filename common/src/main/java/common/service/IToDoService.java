package common.service;

import common.vo.ToDo;

public interface IToDoService {
    ToDo getToDoByTaskId(String id);
}
