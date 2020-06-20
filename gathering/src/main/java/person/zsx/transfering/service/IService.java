package person.zsx.transfering.service;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

/**
 * @Author: zsx
 * @DateTime: 2020/2/23 16:52
 * @Description: 入账的接口服务类
 */

public interface IService   {

    /**
     * @Author: zsx
     * @Description: 执行存入
     * @DateTime: 2020/2/23 16:57
     * @Params: 账号，要存的金额
     * @Return
     */
    void doGathering(String accountNumber, BigDecimal money);
    /**
    * @Author: Zhang Shaoxuan
    * @Description: 转账的正式操作
    * @DateTime: 2020/3/7 20:59
    * @Params:
    * @Return
    */
    String  gathering(String accountNumber,String accountNumberTo,String money);
    /**
    * @Author: zsx
    * @Description: tcc try  入账的try阶段什么都不做
    * @DateTime: 2020/3/7 18:46
    * @Params:
    * @Return
    */
  //  String tryUpdate(String accountNumber, BigDecimal money);
   /**
   * @Author: Zhang Shaoxuan
   * @Description: tcc confirm
   * @DateTime: 2020/3/7 18:47
   * @Params:
   * @Return
   */
  //  void confirmUpdate(String accountNumber, BigDecimal money);
    /**
    * @Author: Zhang Shaoxuan
    * @Description: tcc cancel
    * @DateTime: 2020/3/7 18:47
    * @Params:
    * @Return
    */
   // void cancelUpdate(String accountNumber, BigDecimal money);



}
