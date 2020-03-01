package person.zsx.transfering.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import person.zsx.transfering.service.IService;

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
    IService iService;
    //做服务调用
    @Autowired
    RestTemplate restTemplate;
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

    @RequestMapping("/gs")
    public String getService(){
      return   restTemplate.getForObject("http://transfer-service/transferController/transfer/10000",String.class);
    }
}
