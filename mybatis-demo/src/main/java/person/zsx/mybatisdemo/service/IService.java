package person.zsx.mybatisdemo.service;

import person.zsx.mybatisdemo.pojo.AccountB;
import person.zsx.mybatisdemo.pojo.Customer;

import java.math.BigDecimal;
import java.util.List;

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
    void doGathering(AccountB accountB);
    AccountB getAccount(String accountNumber);
    AccountB getAccount2(String accountNumber);
    void insert(AccountB accountB);
    /**
    * @Author: Zhang Shaoxuan
    * @Description: 自己手工实现的分页
    * @DateTime: 2020/3/21 15:40
    * @Params:
    * @Return
    */
    List<Customer> listCustomers(int pageNum,int pageSize);

}
