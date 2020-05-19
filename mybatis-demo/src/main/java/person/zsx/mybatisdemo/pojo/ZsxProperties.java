package person.zsx.mybatisdemo.pojo;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: zhangshaoxuan
 * @DateTime: 2020/3/15 12:04
 * @Description: 读取配置文件中以zsx开头属性值
 */
@Component
@ConfigurationProperties(prefix = "zsx")
public class ZsxProperties {
    String name;
    String gender;

    @Override
    public String toString() {
        return "ZsxProperties{" +
                "name='" + name + '\'' +
                ", gender=" + gender +
                '}';
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
