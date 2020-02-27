package zsx.dubbo.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;



@Transactional
@RequestMapping("/testController")
public class TestController {
    private Jedis jedis = null;
    @Reference
  //  private IOperationService operations;
    //记录前台是否是第一次请求
    private int count = 0;
    Logger log = LoggerFactory.getLogger(TestController.class);




    }

