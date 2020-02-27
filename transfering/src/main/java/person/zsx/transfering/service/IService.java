package person.zsx.transfering.service;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @Author: zsx
 * @DateTime: 2020/2/23 16:52
 * @Description: 转账的接口服务类
 */

public interface IService {
    /**
     * @Author: zsx
     * @Description:  获得余额，判断是否够转账
     * @DateTime: 2020/2/23 16:54
     * @Params: 账户；要转账的金额
     * @Return
     */
    boolean canTransfer(String accountNumber, BigDecimal money);
    /**
     * @Author: zsx
     * @Description: 执行转账操作，将账户余额减少
     * @DateTime: 2020/2/23 16:57
     * @Params: 账号，要转的金额
     * @Return
     */
    void doTransfer(String accountNumber, BigDecimal money);
}
