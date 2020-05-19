package person.zsx.transfering.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import person.zsx.transfering.feignService.IFeignService;
import person.zsx.transfering.feignService.IFeignServiceImpl;
import person.zsx.transfering.service.IService;
import person.zsx.transfering.serviceImpl.ServiceImpl;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * @Author: zsx
 * @DateTime: 2020/2/23 16:38
 * @Description:  转入控制器
 */
@RestController
    @RequestMapping("gatheringController")
public class Controller {
    @Autowired
    ServiceImpl iService;
   @Autowired
   IFeignService iFeignService;
    //做服务调用
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    LoadBalancerClient loadBalancerClient;
    /**
     * @Author: zsx
     * @Description: 转入请求
     * @DateTime: 2020/2/23 18:02
     * @Params:
     * @Return
     */
    @RequestMapping("/gathering/{money}")
    public String gather(@PathVariable String money){
       iService.doGathering("222",new BigDecimal(money));
        return "gathering success";
    }
/**
* @Author: zsx
* @Description: 测试ribbon调用服务，服务端暂时只能通过controller里调用service，再请求controller来调用服务
* @DateTime: 2020/3/5 23:03
* @Params:
* @Return
*/
   @RequestMapping("/gs")
   //ribbon使用断路器，一旦出异常，或者超时行为，就会调用fallback方法
   @HystrixCommand(fallbackMethod = "fallback")
    public String getService(){
       System.out.println("------请求已进来---------，准备进行RPC");
      return   restTemplate.getForObject("http://transfer-service/transferController/transfer/19",String.class);
    }
    private String fallback(){
       return "ribbon断路器打开";
    }
    /**
    * @Author: zsx
    * @Description: 测试feign的代码，以及分布式事务框架的的调用
    * @DateTime: 2020/3/5 23:05
    * @Params:
    * @Return
    */
    @RequestMapping("/feign/{accountNumberFrom}/{accountNumberTo}/{money}")
    public String getFeignService(@PathVariable String accountNumberFrom,@PathVariable String accountNumberTo,@PathVariable String money){
        return  iService.gathering(accountNumberFrom,accountNumberTo,money);
    }
    /**
    * @Author: Zhang Shaoxuan
    * @Description:  在没有分布式事务中测试异常情况下不会发生回滚的现象
    * @DateTime: 2020/3/7 21:32
    * @Params:
    * @Return
    */
    @RequestMapping("/withouttcc/{money}")
    public String gatheringWithoutTcc(@PathVariable  String money){
        iService.doGathering("321",new BigDecimal(money));
        return iFeignService.transfer("123",money);

    }
}
