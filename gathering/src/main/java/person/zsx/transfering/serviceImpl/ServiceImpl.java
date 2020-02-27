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
    public void doGathering(String accountNumber, BigDecimal money) {
        iMapperDao.updateAccountB(accountNumber,money);
    }
}
