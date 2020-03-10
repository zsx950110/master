package person.zsx.transfering.serviceImpl;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import person.zsx.transfering.TestFather;
import person.zsx.transfering.dao.IMapperDao;
import person.zsx.transfering.service.IService;

import java.math.BigDecimal;

import static org.junit.Assert.*;

/**
 * @Author: zsx
 * @DateTime: 2020/2/23 22:56
 * @Description:
 */
public class ServiceImplTest extends TestFather {
@Autowired
    ServiceImpl iService;
@Test
    public void testCanTransfer(){
    System.out.println(iService.canTransfer("123",new BigDecimal("20")));
}
@Test
    public void  testDoTransfer(){
    iService.doTransfer("123",new BigDecimal("20.33"));
    }
}