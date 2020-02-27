package person.zsx.transfering.dao;


import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

/**
 * @Author: zsx
 * @DateTime: 2020/2/23 15:41
 * @Description:  数据库操作接口类
 */
@Repository
public interface IMapperDao {
    /**
     * @Author: zsx
     * @Description: 获得账户余额，用于检验能否转账
     * @DateTime: 2020/2/23 16:47
     * @Params: 账号
     * @Return  账户余额
     */
    BigDecimal getBalance(String accountNumber);
    /**
     * @Author: zsx
     * @Description: 进行转账
     * @DateTime: 2020/2/23 16:48
     * @Params:  账号，更新的钱
     * @Return
     */
    void updateAccountB(@Param("accountNumber") String accountNumber,
                       @Param("money") BigDecimal money);

}
