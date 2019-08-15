package person.util;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

/**
 *密码简单转换类
 */
public class ConvertData extends PropertyPlaceholderConfigurer {
    private  final String PASSWORD="password";
    private  final String PASSWORD_VALUE="acs";

    @Override
    protected String convertProperty(String propertyName, String propertyValue){

        System.out.println("属性："+propertyName+"--属性值："+propertyValue);
        if (PASSWORD.equals(propertyName)){
            propertyValue=this.PASSWORD_VALUE;
            System.out.println("返回值为"+propertyValue);
        }
    return  propertyValue;
    }

}
