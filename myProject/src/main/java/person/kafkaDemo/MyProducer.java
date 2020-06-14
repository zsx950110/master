package person.kafkaDemo;

import com.alibaba.fastjson.JSON;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class MyProducer {
    private String name;
    private  String pwd;

    @Override
    public String toString() {
        return "MyProducer{" +
                "name='" + name + '\'' +
                ", pwd='" + pwd + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public MyProducer(String name, String pwd) {
        this.name = name;
        this.pwd = pwd;
    }

    private static Producer <String,String> kafkaProducer = null;
    static {
        kafkaProducer = new KafkaProducer<String, String>(getProducerProperties());
    }
    //主题只能写命令提前放进去，不能代码里设置
   static String topic ="test";
    public static void main(String[] args) {
        int i=0;
        boolean flag=true;
        while (i<1000){
            MyProducer myProducer = new MyProducer("zhangsan"+i,"pwd"+i);
            //json对象
            ProducerRecord<String,String> record = new ProducerRecord<>(topic,String.valueOf(i), JSON.toJSONString(myProducer));
            kafkaProducer.send(record);
            i++;
            System.out.println("发送一个---");
        }

        kafkaProducer.close();
    }
    public static  Properties getProducerProperties(){
        //属性
        Properties props = new Properties();
        //
        props.put("bootstrap.servers", "192.168.43.152:9092");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("key.serializer", StringSerializer.class.getName());
        props.put("value.serializer", StringSerializer.class.getName());
        return  props;
    }
}
