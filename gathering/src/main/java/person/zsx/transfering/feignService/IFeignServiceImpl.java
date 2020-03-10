package person.zsx.transfering.feignService;

import org.springframework.stereotype.Component;

/**
 * @Author: zhangshaoxuan
 * @DateTime: 2020/3/6 12:17
 * @Description: feign实现类
 */
@Component
public class IFeignServiceImpl  implements IFeignService {
    @Override
    public String transfer(String money,String accountNumber) {
        return "feign中的hystrix断路器开关开启：" +money;
    }

    @Override
    public String transfer1(String accountNumber, String money) {
        return null;
    }
}
