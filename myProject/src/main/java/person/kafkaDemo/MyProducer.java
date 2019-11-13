package person.kafkaDemo;

import com.alibaba.fastjson.JSON;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class MyProducer {
    private String name;
    private  String pwd;

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

    private static KafkaProducer<String,String> kafkaProducer = null;
    //主题只能写命令提前放进去，不能代码里设置
   static String topic ="zsx1";
    public static void main(String[] args) {

        kafkaProducer = new KafkaProducer<String, String>(getProducerProperties());
        int i=0;
        boolean flag=true;
        while (i<1000){
            MyProducer myProducer = new MyProducer("zhangsan"+i,"pwd"+i);
            //json对象
            ProducerRecord<String,String> record = new ProducerRecord<>(topic,String.valueOf(i), JSON.toJSONString(myProducer));
            kafkaProducer.send(record);
            i++;
        }

        kafkaProducer.close();
    }
    public static  Properties getProducerProperties(){
        //属性
        Properties producerPro = new Properties();
        producerPro.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");
        producerPro.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        producerPro.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class);
        return  producerPro;
    }

}
