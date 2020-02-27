package zsx.dubbo.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.rpc.service.EchoService;
import com.alibaba.fastjson.JSON;

import common.pojo.ToDo;
import common.service.IOperationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.annotation.Resource;
import java.util.List;
@Controller
@RequestMapping("/testController")
public class ConsumerController {

  //如果配置的服务bean，用@Reference会报null，改为resource，autowired会报错，原因未知
  @Resource
  IOperationService iOperationService;
  @ResponseBody
  @RequestMapping("/test")
    public  String test(){
    System.out.println("当前线程名称为============："+Thread.currentThread().getName());


    List<ToDo> list = iOperationService.listToDos();

    return JSON.toJSONString(Thread.currentThread().getName());
    }
  @ResponseBody
  @RequestMapping("/ajaxTest")
    public String ajaxTest(){
    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    return Thread.currentThread().getId()+Thread.currentThread().getName();
    }
}
