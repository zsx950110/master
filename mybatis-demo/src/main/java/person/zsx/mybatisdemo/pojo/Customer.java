package person.zsx.mybatisdemo.pojo;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * @Author: zhangshaoxuan
 * @DateTime: 2020/3/15 12:04
 * @Description: customer实体类
 */

public class Customer  {
 private String userId;
 private String name;
    //一个客户会有多个账户
    List<AccountB> accountBs ;
    public List<AccountB> getAccountBs() {
        return accountBs;
    }

    public void setAccountBs(List<AccountB> accountBs) {
        this.accountBs = accountBs;
    }



    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }



    @Override
    public String toString() {
        return "Customer{" +
                "id='" + userId + '\'' +
                ", name='" + name + '\'' +
             //   Arrays.toString(accountBs.toArray())+
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
