package person.zsx.mybatisdemo.controller;

import com.sun.javafx.collections.MappingChange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import person.zsx.mybatisdemo.pojo.AccountB;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: zhangshaoxuan
 * @DateTime: 2020/3/14 18:31
 * @Description:
 */
@RestController
@RequestMapping("/demoTest")
public class TestController {
    @Value("${zsx.name}")
    String name;
    /**
    * @Author: Zhang Shaoxuan
    * @Description: 测试@valid的校验
    * @DateTime: 2020/3/15 11:41
    * @Params:
    * @Return
    */
    @RequestMapping("/test")
    public List test(String param, @Valid AccountB accountB, BindingResult bindResult){
        System.out.println("-----"+param);
        List<Map<Integer, AccountB>> list = new ArrayList<>();
        if(bindResult.hasErrors()){
            return  bindResult.getAllErrors();
        }
        return list;
    }
    /**
    * @Author: Zhang Shaoxuan
    * @Description: 测试@value的效果
    * @DateTime: 2020/3/15 11:41
    * @Params:
    * @Return
    */
    @RequestMapping("/getValue")
    public  String getValue(){
        return this.name;
    }

}
