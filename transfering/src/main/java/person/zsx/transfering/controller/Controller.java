package person.zsx.transfering.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
            return "transfering success";
        }
        return "transfering failure";
    }
}
