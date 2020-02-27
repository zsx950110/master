package person.zsx.transfering.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import person.zsx.transfering.dao.IMapperDao;
import person.zsx.transfering.service.IService;

import java.math.BigDecimal;

/**
 * @Author: zsx
 * @DateTime: 2020/2/23 18:49
 * @Description:
 */
@Service
public class ServiceImpl implements IService {
    @Autowired
    IMapperDao iMapperDao;
    @Override
    public boolean canTransfer(String accountNumber, BigDecimal money) {
      if (iMapperDao.getBalance(accountNumber).compareTo(money)>0){
          return  true;
       }
        return false;
    }

    @Override
    public void doTransfer(String accountNumber, BigDecimal money) {
        BigDecimal oldBalance = iMapperDao.getBalance(accountNumber);
        BigDecimal newBalance = oldBalance.subtract(money);
            iMapperDao.updateAccount(accountNumber,newBalance);
    }
}
