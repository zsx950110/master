package person.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 模板方法，父类方法定义要调用的方法以及方法调用顺序
 */
public class TemplateTest {
    //根据给定的模式和费率进行计算
abstract class  CalculateTemplate{
   protected void calculate(double rate,String mode){
        beforeCalculate();
        getMode();
        getRate();
        doCalculate(rate, mode);
    }
    //钩子方法
    protected void beforeCalculate(){}
    protected abstract String getMode();
    protected abstract double getRate();
    private double doCalculate(double rate,String mode){
        System.out.println("模板："+mode+"的比率为："+rate);
       return rate*10;
    }
    //钩子
    void afterCalculate(){};
}
class calculateA extends  CalculateTemplate {
    @Override
    protected String getMode() {
        return "A";
    }
    @Override
    protected double getRate() {
        return 0.23;
    }
}
 class calculateB extends  CalculateTemplate {
        @Override
        protected String getMode() {
            return "B";
        }
        @Override
        protected double getRate() {
            return 0.45;
        }
    }
    public static void main(String[] args) {
    //具体使用时确定使用哪个模板实现类
        CalculateTemplate calculateTemplate = new TemplateTest().new calculateA();
        //计算
        calculateTemplate.calculate(calculateTemplate.getRate(),calculateTemplate.getMode());
    }
}
