package person.zsx.transfering;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author: zsx
 * @DateTime: 2020/2/24 21:15
 * @Description:
 */
@RunWith(SpringRunner.class)
@Component
@SpringBootTest
public class TestBase {
    @Before
    public void before(){
        System.out.println("-------------"+this.getClass().getName()+"测试开始----------");
    }
    @After
    public void after(){
        System.out.println("-------------"+this.getClass().getName()+"测试结束----------");
    }
}
