package person.zsx.mybatisdemo.pojo;

import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author: zsx
 * @DateTime: 2020/2/23 16:41
 * @Description:  账户B，用于转入钱
 */
public class AccountB    implements Serializable  {
    String customerName;
    @NotNull(message = "id不能为空")
    private String id;
    @Range(min = 0,max = 100,message = "balance最大值为100")
    private String accountNumber;
    @Range(min = 0,max = 100,message = "balance值需要在0-100之间")
    private Integer balance;
    @Pattern(regexp = "[^(fuck)]",message = "customName中有敏感词汇")
    private  Customer customer;

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "========AccountB{" +
                "id='" + id + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", balance=" + balance +
            //   ", customer='" + customer.toString() + '\'' +
                '}';
    }
}
