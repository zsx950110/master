package person.zsx.mybatisdemo.Other;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import person.zsx.mybatisdemo.TestFather;
import person.zsx.mybatisdemo.pojo.ZsxProperties;

/**
 * @Author: zhangshaoxuan
 * @DateTime: 2020/3/15 12:09
 * @Description:
 */
public class OtherTest extends TestFather {

    @Autowired
    ZsxProperties zsxProperties;

@Test
    public  void getZsx(){
    System.out.println(zsxProperties.toString());
}
}
