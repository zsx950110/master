package person.zsx.transfering;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

    @RunWith(SpringRunner.class)
    @SpringBootTest
    @Component
    public class TestFather {

        @Before
        public void init(){
            System.out.println("-------------"+this.getClass().getName()+"开始测试了------------");
        }
        @After
        public  void  after(){ System.out.println("--------------"+this.getClass().getName()+"测试结束了---------");
        }
    }
