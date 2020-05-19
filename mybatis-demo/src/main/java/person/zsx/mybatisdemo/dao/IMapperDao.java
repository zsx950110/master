package person.zsx.mybatisdemo.dao;


import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import person.zsx.mybatisdemo.pojo.AccountB;
import person.zsx.mybatisdemo.pojo.Customer;

import java.math.BigDecimal;
import java.util.List;

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
    AccountB getAccount(String accountNumber);
    List<AccountB> getAccount2(@Param("accountNumber") String accountNumber);
    void  insertAccount(AccountB accountB);
    //测试一下#和$的区别
    List<AccountB> getOldTable(@Param("tableName") String tablename, @Param("id") String id);
    /**
     * @Author: zsx
     * @Description: 进行转账
     * @DateTime: 2020/2/23 16:48
     * @Params:  账号，更新的钱
     * @Return
     */
    void updateAccountB(AccountB accountB);
    Customer getCustomerById(@Param("id")String id);
    //测试choose when otherwise
    List<AccountB> listAccountbByCondition(@Param("id") String id,
                                           @Param("accountNumber") String accountNumber,
                                           @Param("balance") String balance);
    void testtrim(AccountB accountB);
    List<AccountB> testForeach( List<String>ids);
   // List<AccountB> gtlt(@Param("id1") String id1,@Param("id2") String id2);
    Customer getCustomerByAccountId(@Param("id")String id);
    Customer getCustomerByUserId(@Param("userId") String userId);
    AccountB getAccountById(@Param("id") String id);
    Customer getCustomer(@Param("userId") String userId);
    void  addCustomer(Customer customer);
    Integer countCustomer();
    List<Customer> listCustomerByPage();
}
