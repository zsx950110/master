package person.zsx.mybatisdemo.controller;

import com.alibaba.fastjson.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import person.zsx.mybatisdemo.TestFather;
import person.zsx.mybatisdemo.pojo.AccountB;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

/**
 * @Author: zhangshaoxuan
 * @DateTime: 2020/3/15 12:08
 * @Description:
 */
public class ControllerTest extends TestFather {
    private MockMvc mockMvc;
    @Autowired
    WebApplicationContext webApplicationContext; //注入web环境
    @Before
    public void init() {
        super.init();
        //拿到web上下文，其中就有spring管理的控制器了，这个参数需要整个web的上下文
        mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
        //MockMvcBuilders.standaloneSetup(new TestController()).build(); //这样指定一组控制器
    }

    @org.junit.Test
    public void testController() throws  Exception{
        //mockMvc要在测试之前@before中设置好
        AccountB accountB = new AccountB();
        accountB.setCustomerName("fuckyou");
        accountB.setBalance(200);
        accountB.setAccountNumber("300");

        mockMvc.perform(MockMvcRequestBuilders.get("/demoTest/test")//post和get请求
                .param("param","this is junitTest")
                .param("accountB", JSONObject.toJSONString(accountB))
                .param("accountNumber","300")
        ) //参数，可多个param方法，通过reqeust获取
                .andDo(print()) //print(）打印整个过程到控制台;
                // .andExpect(MockMvcResultMatchers.content().
                //  string(Matchers.containsString("this")))
                .andReturn();
    }
    @Test
    public void testGetValue() throws Exception {
        this.performBase("/demoTest/getValue");
    }

    /**
     * @Author: Zhang Shaoxuan
     * @Description: 没有参数的请求复用类
     * @DateTime: 2020/3/15 11:51
     * @Params:
     * @Return
     */
    private MvcResult performBase(String url)throws Exception{
        return mockMvc.perform(MockMvcRequestBuilders.post(url)).
                andDo(print()).andReturn();
    }
}
