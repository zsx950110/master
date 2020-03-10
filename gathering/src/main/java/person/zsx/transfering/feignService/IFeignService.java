package person.zsx.transfering.feignService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: zhangshaoxuan
 * @DateTime: 2020/3/1 22:25
 * @Description: feign远程调用的接口，fallback指明断路器的fallback在哪里
 */

//这个注解是feign请求用的
    //fallback是feign做断路器用的，对应的类是该接口的实现类,,fallback =IFeignServiceImpl.class
@FeignClient(value="transfer-service",fallback =IFeignServiceImpl.class)
public interface IFeignService {
    //提供者中的路径写在服务接口上,hmily的tcc分布式事务暂未成功
 //  @Hmily
    @RequestMapping("/transferController/transfer/{accountNumber}/{money}")
   String transfer(@PathVariable String accountNumber,@PathVariable String money);
    @RequestMapping("/transferController/transferWithoutTcc/{accountNumber}/{money}")
    String transfer1(@PathVariable String accountNumber,@PathVariable String money);
}
