package person.zsx.transfering.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import person.zsx.transfering.service.IService;

import java.math.BigDecimal;

/**
 * @Author: zsx
 * @DateTime: 2020/2/23 16:38
 * @Description:  转账控制器
 */
@RestController
@RequestMapping("/transferController")
public class Controller {
    @Autowired
    IService iService;
    //@Value("${server.port}")
    int port=0;
    /**
     * @Author: zsx
     * @Description: 转账请求
     * @DateTime: 2020/2/23 18:02
     * @Params:
     * @Return
     */
    @RequestMapping(value = "/transfer/{money}")
    public String transfer(@PathVariable String money){
        BigDecimal bigDecimal= new BigDecimal(money);
        int i = Integer.parseInt(money);
        if(iService.canTransfer("123",bigDecimal)){
            iService.doTransfer("123",bigDecimal);
            return "transfering success"+port;
        }
        return "transfering failure"+port;
    }
    /**
    * @Author: Zhang Shaoxuan
    * @Description:  lcn 分布式事务测试
    * @DateTime: 2020/3/9 16:19
    * @Params:
    * @Return
    */
    @RequestMapping(value = "/transfer/{accountNumber}/{money}")
    public String transfer(@PathVariable String accountNumber,@PathVariable String money){
        System.out.println("-----------------远程请求到减钱的服务-----------");
        BigDecimal bigDecimal= new BigDecimal(money);
        return  iService.transfer(accountNumber,bigDecimal);

    }

    /**
    * @Author: Zhang Shaoxuan
    * @Description: 不使用tcc时，测试不会回滚的场景
    * @DateTime: 2020/3/7 21:38
    * @Params:
    * @Return
    */
    @RequestMapping(value = "/transferWithoutTcc/{accountNumber}/{money}")
    public String transfer1(@PathVariable String accountNumber,@PathVariable String money){
        BigDecimal bigDecimal= new BigDecimal(money);
        if (iService.canTransfer(accountNumber,bigDecimal)){
            iService.doTransfer(accountNumber,bigDecimal);
            return "success";
        }
        throw  new RuntimeException("----withoutTcc转账失败------");
    }
}
