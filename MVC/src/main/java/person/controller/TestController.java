package person.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;
import person.service.Operations;
import person.util.JedisUtil;
import person.util.RedisLock;
import person.util.SqlLock;
import person.util.Util;
import redis.clients.jedis.Jedis;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;


@Controller
@RequestMapping("/testController")
public class TestController {
    private Class voClass;
    public TestController(){

    }
    public TestController(String className) {
        try {
            this.voClass = Thread.currentThread().getContextClassLoader().loadClass(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private Jedis jedis = null;
    private static ApplicationContext context;
    @Autowired
    private Operations operations;
    @Autowired
    SqlLock sqlLock;
    //记录前台是否是第一次请求
    private int count = 0;
    Logger log = LoggerFactory.getLogger(TestController.class);
    @ResponseBody
    @RequestMapping(value = "insertMethod")
    public String insert(HttpServletRequest request, String value, HttpServletResponse response) throws Exception {
        System.out.println( WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
        operations.insert();
        return "success";
    }
    @ResponseBody
    @RequestMapping(value = "update")
    public  synchronized String update(HttpServletRequest request, String value, HttpServletResponse response) throws Exception {
        System.out.println("进入MVC======");
        String name =request.getServerPort()+"-"+Thread.currentThread().getId();
        RedisLock.lock(name);//redis的分布式锁
        RedisLock.lock(name);//redis的分布式锁
      //  sqlLock.lock(Util.getMethodName(request),Util.getThreadName(request));
      //  sqlLock.lock(Util.getMethodName(request),Util.getThreadName(request));
        operations.update();
     //  sqlLock.releasLock(Util.getMethodName(request),Util.getThreadName(request));
        RedisLock.releaseLock(name);//redis的分布锁释放
        return "success";
    }
    @ResponseBody
    @RequestMapping(value = "test")
    public   String test(HttpServletRequest request, String value, HttpServletResponse response) throws Exception {
    //  sqlLock.lock(Util.getMethodName(request),Util.getThreadName(request));
      //  sqlLock.releasLock(Util.getMethodName(request),Util.getThreadName(request));
        System.out.println("打印结果：============"+this.voClass.toString());
        return "success";
    }


}
