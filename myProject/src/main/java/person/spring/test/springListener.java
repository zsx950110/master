package person.spring.test;import org.springframework.aop.framework.AopContext;import org.springframework.context.ApplicationContext;import org.springframework.context.ApplicationEvent;import org.springframework.context.ApplicationListener;import org.springframework.context.support.ClassPathXmlApplicationContext;//spring监听器public class springListener implements ApplicationListener {    public  void print(){        System.out.println("监听开始============");    }    @Override    public void onApplicationEvent(ApplicationEvent applicationEvent) {      this.print();        System.out.println(applicationEvent.getSource().toString());    }    //自定义监听器    class myEvent extends   ApplicationEvent{        public myEvent(String source) {            super(source);        }    }    public static void main(String[] args) {        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("test.xml");            applicationContext.publishEvent(new springListener().new myEvent("开始发布了====="));    }}