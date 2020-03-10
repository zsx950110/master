package person.zsx.transfering.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import person.zsx.transfering.TestBase;

import java.math.BigDecimal;

import static org.junit.Assert.*;

/**
 * @Author: zsx
 * @DateTime: 2020/2/24 21:21
 * @Description:
 */
public class IServiceTest extends TestBase {
@Autowired
    IService iService;
@Test
    public void testDoGathering(){
    iService.doGathering("321",new BigDecimal("20"));
}
}