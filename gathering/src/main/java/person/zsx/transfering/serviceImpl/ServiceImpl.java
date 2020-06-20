package person.zsx.transfering.serviceImpl;

//import com.codingapi.tx.annotation.TxTransaction;

import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import person.zsx.transfering.dao.IMapperDao;
import person.zsx.transfering.feignService.IFeignService;
import person.zsx.transfering.service.IService;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * @Author: zsx
 * @DateTime: 2020/2/23 18:49
 * @Description:
 */
@Service
public class ServiceImpl implements IService {
    final  static Logger logger = LoggerFactory.getLogger(ServiceImpl.class);
    @Autowired
    IMapperDao iMapperDao;
   @Resource
    IFeignService iFeignService;
    @Override
    public void doGathering(String accountNumber, BigDecimal money) {

        iMapperDao.updateAccountB(accountNumber,money);

    }
    /**
    * @Author: Zhang Shaoxuan
    * @Description:  采用lcn两阶段提交测试分布式事务能否成功
     * 在该方法中直接调用所有业务方法
    * @DateTime: 2020/3/9 13:33
    * @Params:
    * @Return
    */
    @LcnTransaction//分布式事务
    //@Transactional //本地事务
    @Override
    public String gathering(String accountNumberFrom,String accountNumberTo, String money) {

        //加钱
        logger.info("---------------开始调用加钱服务----------");
        this.doGathering(accountNumberTo,new BigDecimal(money));
        logger.info("---------------加钱服务调用结束-----------");
        //转钱
        logger.info("------开始调用转账服务---------");
        String resultT = iFeignService.transfer(accountNumberFrom,money);
        logger.info("---------------转账服务调用结束--------");
        return "execute result :"+ resultT;
    }


    //@Transactional
   // @Override
    public String tryUpdate(String accountNumber, BigDecimal money) {
        logger.info("------入账的try，该阶段什么都不做，调用扣款的服务-----");
        return iFeignService.transfer(accountNumber,money.toString());
    }

    //@Transactional
  //  @Override
    public void confirmUpdate(String accountNumber, BigDecimal money) {
        logger.info("--------入账的confirm---------------");
       // this.doGathering(accountNumber,money);
    }

  //  @Override
    public void cancelUpdate(String accountNumber, BigDecimal money) {
        logger.info("--------入账的cancel，该阶段什么都不做--------");
    }
}
