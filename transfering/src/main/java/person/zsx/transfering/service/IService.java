package person.zsx.transfering.service;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;

/**
 * @Author: zsx
 * @DateTime: 2020/2/23 16:52
 * @Description: 转账的接口服务类
 */

public interface IService {
    /**
    * @Author: Zhang Shaoxuan
    * @Description:   转账操作，测试lcn分布式事务是否生效
    * @DateTime: 2020/3/9 14:57
    * @Params:
    * @Return
    */
    String transfer(String accountNumber, BigDecimal money);
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
    /**
     * @Author: zsx
     * @Description: tcc try  转账的try阶段直接调用转账方法，判断余额，如果不能转直接抛异常
     * @DateTime: 2020/3/7 18:46
     * @Params:
     * @Return
     */
    void tryUpdate(String accountNumber, BigDecimal money);
    /**
     * @Author: Zhang Shaoxuan
     * @Description: tcc confirm，转账confirm什么都不做
     * @DateTime: 2020/3/7 18:47
     * @Params:
     * @Return
     */
    void confirmUpdate(String accountNumber, BigDecimal money);
    /**
     * @Author: Zhang Shaoxuan
     * @Description: tcc cancel   try失败后要取消转账
     * @DateTime: 2020/3/7 18:47
     * @Params:
     * @Return
     */
    void cancelUpdate(String accountNumber, BigDecimal money);
}
