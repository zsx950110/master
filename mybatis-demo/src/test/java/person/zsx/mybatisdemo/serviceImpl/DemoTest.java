package person.zsx.mybatisdemo.serviceImpl;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.github.pagehelper.Page;
import jdk.nashorn.internal.parser.JSONParser;
import net.minidev.json.JSONUtil;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import person.zsx.mybatisdemo.TestFather;
import person.zsx.mybatisdemo.controller.TestController;
import person.zsx.mybatisdemo.dao.IMapperDao;
import person.zsx.mybatisdemo.pojo.AccountB;
import person.zsx.mybatisdemo.pojo.Customer;
import person.zsx.mybatisdemo.pojo.ZsxProperties;
import person.zsx.mybatisdemo.service.IService;
import sun.applet.Main;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

/**
 * @Author: zhangshaoxuan
 * @DateTime: 2020/3/13 13:21
 * @Description:
 */
public class DemoTest extends TestFather {
    @Autowired
    IService iService;
    @Autowired
    IMapperDao iMapperDao;
    @org.junit.Test
    public void getAccount() {
        //使用别名
       // AccountB accountB = iService.getAccount("321");
       // System.out.println(accountB.toString());
        //不使用别名时，要配置驼峰映射，否则对象的属性为null
        List<AccountB> accountB1s = iMapperDao.getAccount2("123");
        for (AccountB accountB1 : accountB1s) {
            System.out.println(accountB1.toString());
        }
    }
    @org.junit.Test
    public void insertAccount(){
        AccountB accountB = new AccountB();
        accountB.setAccountNumber("555");
        accountB.setBalance(555);
        accountB.setCustomerName("赵四");
        iService.insert(accountB);
    }
    @Test
    public void test(){
      List<AccountB> accountBs = iMapperDao.listAccountbByCondition("11","222 ","200");
        for (AccountB accountB : accountBs) {
            System.out.println(accountB.toString());
        }
    }
    @Test
    public  void testTrim() {

        //一对一级联测试
      //  System.out.println(iMapperDao.getAccountById("111"));
        //一对多级联测试
        //System.out.println(JSONObject.toJSONString(iMapperDao.getCustomer("1")));
        List<String> strings = Arrays.asList("a","b","c","d","e","f","g","h","i","j","k","l","m","e");
        Random random = new Random();

        for(int i =0;i<100;i++){
            StringBuffer stringBuffer = new StringBuffer();
            int j = random.nextInt(strings.size()-4);
            for (int q=j;q<j+4;q++) {
                stringBuffer.append(strings.get(q));
            }
            Customer customer = new Customer();
            customer.setName(stringBuffer.toString());
            iMapperDao.addCustomer(customer);
            stringBuffer=null;
        }
    }
    @Test
    public  void testPage(){
        List<Customer> list =  iService.listCustomers(13,10);
        System.out.println("-------------test运行结果："+Arrays.toString(
               list.toArray()));
        System.out.println("pageSize======"+((Page)list).getPageNum());
    }
    @Test
    public void testMain(){
    }

}