package controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pojo.ToDo;
import redis.clients.jedis.Jedis;
import service.IOperationService;
import util.JedisUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller

@Transactional
@RequestMapping("/testController")
public class TestController {
    private Jedis jedis = null;

    private IOperationService operations;
    //记录前台是否是第一次请求
    private int count = 0;
    Logger log = LoggerFactory.getLogger(TestController.class);


    @ResponseBody
    @RequestMapping(value = "listUsers")

    public String listUsers(HttpServletRequest request, HttpServletResponse response) {

        return JSON.toJSONString(count);
    }


    @RequestMapping("/listToDo")
    @ResponseBody
    public String listToDo(HttpServletRequest request, HttpServletResponse response) {
        System.out.println(System.currentTimeMillis());

        List<ToDo> toDos = null;
        //如果是第一次请求则执行
        if (count == 0) {
            long l1 = System.currentTimeMillis();

            toDos = operations.listToDos();
            long l2 = System.currentTimeMillis();
            log.info("用时》》》》》" + (l2 - l1) / 1000);
            //count计数
            count = 1;
            //第一次请求进来这个不为空 则返回,并且不再执行后面的动作
            return JSONObject.toJSONString(toDos);
        }

        response.setCharacterEncoding("GBK");
        response.setContentType("text/xml;charset=GBK");
        long l1 = System.currentTimeMillis();
        Jedis jedis = JedisUtil.getJedis();
        //获得所有的任务id
     /*   List<String> taskIdLists = jedis.lrange("taskLists",0,-1);
        //返回集合
        List<ToDo> toDoList = new ArrayList<>();
        for (String taskIdList : taskIdLists) {
            //遍历所有任务id去获得todo
           String jsonString =  jedis.hget("todoList",taskIdList);
           //转化
                JSONObject jsonObject = JSONObject.parseObject(jsonString);

                ToDo toDo = JSON.toJavaObject(jsonObject,ToDo.class);
                //添加
                toDoList.add(toDo);

        }

        long l2 = System.currentTimeMillis();
        log.info("用时》》》》》"+(l2-l1)/1000);
        return  JSONObject.toJSONString(toDoList);*/
        String jsonList = jedis.get("todosListJsonString");
        return jsonList;


    }




}
