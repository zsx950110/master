package person.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.support.hsf.HSFJSONUtils;
import com.alibaba.fastjson.util.IOUtils;
import com.sun.xml.bind.v2.TODO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.HandlerInterceptor;
import person.pojo.Book;
import person.pojo.Comment;
import person.pojo.TestUser;
import person.pojo.ToDo;
import person.service.Operations;
import person.test.WrapperTest;
import person.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

import static org.springframework.util.StringUtils.*;


//@Controller

@Transactional
@RequestMapping("/testController")
public class TestController {
    private Jedis jedis = null;
    @Autowired
    private Operations operations;
    //记录前台是否是第一次请求
    private int count = 0;
    Logger log = LoggerFactory.getLogger(TestController.class);

    @RequestMapping(value = "insertMethod")
    public String insert(HttpServletRequest request, HttpServletResponse response) {

        operations.insertData();
        return "success";
    }

    @ResponseBody
    @RequestMapping(value = "listUsers")

    public String listUsers(HttpServletRequest request, HttpServletResponse response) {
        //  operations = null;

  /*        Jedis jedis = JedisUtil.getJedis();
          String str = jedis.get("countTest");
          if(str==null){
              throw  new NullPointerException();
          }*/
        //JedisUtil.returnJedis(jedis);

        return JSON.toJSONString(count);
    }

    @RequestMapping(value = "/delete/{id}")
    public String deletedata(@PathVariable String id) {

        try {
            operations.deleteData(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "deleteSuccess";
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

    @RequestMapping("/addComment/{comment}&{taskId}&{userId}")
    @ResponseBody
    //增加评论
    public void addComentOnToDo(@PathVariable String comment, @PathVariable String taskId, @PathVariable String userId, HttpServletRequest request, HttpServletResponse response) {
        Comment comment1 = new Comment();
        comment1.setComment(comment);
        comment1.setId(taskId + System.currentTimeMillis());
        comment1.setUserId(userId);
        comment1.setStar(0);
        String json = JSON.toJSONString(comment1);
        Jedis jedis = JedisUtil.getJedis();
        jedis.lpush("pl-" + taskId, json);
        JedisUtil.returnJedis(jedis);
    }

    //评论列表
    @RequestMapping("/listComment/{taskId}")
    @ResponseBody
    public String listComment(@PathVariable String taskId) {
        Jedis jedis = JedisUtil.getJedis();
        List<String> commentJsons = jedis.lrange("pl-" + taskId, 0, -1);
        //评论显示前多少条
        List<Comment> listResult = new ArrayList<>();
        for (String commentJson : commentJsons) {
            Comment comment = JSON.parseObject(commentJson).toJavaObject(Comment.class);
            listResult.add(comment);
        }
        JedisUtil.returnJedis(jedis);
        return JSON.toJSONString(listResult);
    }

    //点赞,通过id找到对应的todo的redis集合，集合找对应id的评论
    @RequestMapping("/star/{taskId}&{id}")
    public void changeStar(@PathVariable String taskId, @PathVariable String id) {
        Jedis jedis = JedisUtil.getJedis();
        List<String> jedisJson = jedis.lrange("pl-" + taskId, 0, -1);
        for (int i = 0; i < jedisJson.size(); i++) {
            Comment comment = JSON.parseObject(jedisJson.get(i)).toJavaObject(Comment.class);
            if (comment.getId().equals(id)) {
                comment.setStar(comment.getStar() + 1);
                jedis.lset("pl-" + taskId, i, JSON.toJSONString(comment));
                break;
            }
        }
        JedisUtil.returnJedis(jedis);

    }

    @ResponseBody
    @RequestMapping("/expireKey")
    public String expireKey() {
        jedis = judgeNull();
        Set<String> keysSet = jedis.keys("*");
        Iterator<String> iterator = keysSet.iterator();
        //    List<Map<String,String>> ttlKeys  =new ArrayList<>();
        Map<String, String> ttlKeys = new HashMap<>();
        while (iterator.hasNext()) {
            String key = iterator.next();
            // jedis.expire(key,8*60*60);
            ttlKeys.put(key, jedis.ttl(key) / 60 + "min");


        }
        //返回的是json对象，而非数组，数组应该是new一个个map进list
        return JSON.toJSONString(ttlKeys);


    }

    @ResponseBody
    @RequestMapping("/brows")
    public void brows(HttpServletRequest request) {
        request.getCookies();
        String sessionId = request.getRequestedSessionId();
        String Agent = request.getHeader("User-Agent");
        jedis = judgeNull();
        jedis.hset("brower", Agent, sessionId);

    }

    @ResponseBody
    @RequestMapping("/showBrower")
    public String showBrower(HttpServletRequest request) {
        jedis = judgeNull();
        Map<String, String> map = jedis.hgetAll("brower");
        return JSON.toJSONString(map);

    }

    @ResponseBody
    @RequestMapping("/listDbaObjects/{pageSize}/{pageNum}")
    public String listDbaObjects(HttpServletRequest request, @PathVariable String pageSize, @PathVariable String pageNum) {

            pageSize = "10";

        System.out.println("in");


        List<Object[]> list = operations.listDbaObject(Integer.valueOf(pageSize), Integer.valueOf(pageNum));
        return JSON.toJSONString(list);

    }

    private Jedis judgeNull() {
        if (this.jedis == null) {
            this.jedis = JedisUtil.getJedis();
        }
        return this.jedis;
    }

    //增加
    @ResponseBody
    @RequestMapping("/shoppingCardAdd/{userId}/{bookId}")
    public String shoppingCardAdd(HttpServletRequest request, @PathVariable String userId, @PathVariable String bookId) {
        operations.shoppingCartAdd(userId, bookId);
        return JSON.toJSONString("");

    }

    //减少
    @ResponseBody
    @RequestMapping("/shoppingCardDecrease/{userId}/{bookId}")
    public String shoppingCardDecrease(HttpServletRequest request, @PathVariable String userId, @PathVariable String bookId) {
        operations.shoppingCardDecrease(userId, bookId);
        return JSON.toJSONString("");

    }


}
