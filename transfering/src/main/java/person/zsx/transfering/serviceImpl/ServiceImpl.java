package person.zsx.transfering.serviceImpl;

import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import person.zsx.transfering.dao.IMapperDao;
import person.zsx.transfering.service.IService;

import java.math.BigDecimal;

/**
 * @Author: zsx
 * @DateTime: 2020/2/23 18:49
 * @Description:
 */
@Service
public class ServiceImpl  implements IService {
    final  static Logger logger = LoggerFactory.getLogger(ServiceImpl.class);

    @Autowired
    IMapperDao iMapperDao;
   // @TxTransaction
   @LcnTransaction //分布式事务
   // @Transactional //本地事务
    @Override
    public String transfer(String accountNumber, BigDecimal money) {
        System.out.println("--------减钱开始-----------");
        if (this.canTransfer(accountNumber, money)){
            this.doTransfer(accountNumber, money);
            System.out.println("----------减钱成功------------");
            return "transfer--ok";
        }
        throw  new RuntimeException("-----减钱失败------");
    }

    @Override
    public boolean canTransfer(String accountNumber, BigDecimal money) {
      if (new BigDecimal(iMapperDao.getBalance(accountNumber)).compareTo(money)>0){
          return  true;
       }
        return false;
    }

    @Override
    public void doTransfer(String accountNumber, BigDecimal money) {
        BigDecimal oldBalance = new BigDecimal(iMapperDao.getBalance(accountNumber));
       BigDecimal newBalance = oldBalance.subtract(money);
            iMapperDao.updateAccount(accountNumber,newBalance);
    }


    //hmily tcc事务暂未实现
    @Transactional
    @Override
    public void tryUpdate(String accountNumber, BigDecimal money) {
        System.out.println("--------转账的try开始-----------");
        if (this.canTransfer(accountNumber, money)){
            this.doTransfer(accountNumber, money);
            System.out.println("----------转账成功------------");
            return;
        }
        throw  new RuntimeException("-----转账失败------");
    }

    @Override
    public void confirmUpdate(String accountNumber, BigDecimal money) {
        System.out.println("--------转账的confirm开始，什么都不做-----------");

    }

    @Override
    public void cancelUpdate(String accountNumber, BigDecimal money) {
        System.out.println("--------转账的cancel开始，将扣除的金额还回去--------------money:"+money);
        BigDecimal newBalance = new BigDecimal(iMapperDao.getBalance(accountNumber));
        BigDecimal  oldBalance = newBalance.add(money);
        iMapperDao.updateAccount(accountNumber,oldBalance);

    }
}
