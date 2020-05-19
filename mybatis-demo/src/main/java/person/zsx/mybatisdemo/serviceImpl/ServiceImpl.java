package person.zsx.mybatisdemo.serviceImpl;

//import com.codingapi.tx.annotation.TxTransaction;

import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import person.zsx.mybatisdemo.dao.IMapperDao;
import person.zsx.mybatisdemo.pojo.AccountB;
import person.zsx.mybatisdemo.pojo.Customer;
import person.zsx.mybatisdemo.service.IService;

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

    @Override
    public void doGathering(AccountB accountB) {
        System.out.println("-----------入库前-------------");;
        iMapperDao.updateAccountB(accountB);
        System.out.println("-------------入库后------------");
    }

    @Override
    public AccountB getAccount(String accountNumber) {
        //使用别名
          return iMapperDao.getAccount(accountNumber);


    }

    @Override
    public AccountB getAccount2(String accountNumber) {
        //不使用别名
        return null;//iMapperDao.getAccount2(accountNumber);
    }

    @Override
    public void insert(AccountB accountB) {
        iMapperDao.insertAccount(accountB);
    }

    @Override
    public List<Customer> listCustomers(int pageNum, int pageSize) {

        int countAll = iMapperDao.countCustomer();
        int pageAll = (countAll/pageSize)+1;
        System.out.println("----------------总页数为："+pageAll);
/*        if(pageAll<=pageNum){
            pageNum = pageAll;
        }*/
//PageHelper.startPage(pageNum,pageSize);
        PageHelper.startPage(pageNum,pageSize,true,true,true);
        return iMapperDao.listCustomerByPage();
    }


}
