package person.zsx.transfering.service;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @Author: zsx
 * @DateTime: 2020/2/23 16:52
 * @Description: 入账的接口服务类
 */

public interface IService {

    /**
     * @Author: zsx
     * @Description: 执行存入
     * @DateTime: 2020/2/23 16:57
     * @Params: 账号，要存的金额
     * @Return
     */
    void doGathering(String accountNumber, BigDecimal money);
}
