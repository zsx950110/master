package person.kafkaDemo;

import com.alibaba.fastjson.JSON;
import kafka.server.KafkaApis;
import kafka.server.ReplicaManager;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;

public class MyConsumer {
    //主题只能写命令提前放进去，不能代码里设置
   static String topic ="zsx1";
    public static void main(String[] args) {
        KafkaApis k=null;
        ReplicaManager r= null;
        Instant first = Instant.now();
// wait some time while something happens
        Instant second = Instant.now();
        Duration duration = Duration.between(first, second);
        //属性
        KafkaConsumer<String,String> kafkaConsumer  = new KafkaConsumer<String, String>(getConsumerProperties());
        kafkaConsumer.subscribe(Arrays.asList(topic)); //subscribe方法接受的是一個Topic列表作為參數，所以需要先創建一個主題列表。方法的參數也可以使用正則表達式匹配多個主題，比如傳入"mytopic.*"進行匹配。
        while(true){
           System.out.println("=====进入while======");
            //必须使用Duration这个参数接口，不能使用弃用接口，否则无法获得生产的数据
            ConsumerRecords<String,String> consumerRecord = kafkaConsumer.poll(Duration.ofSeconds(1));
            System.out.println("数量====="+consumerRecord.count());
            //System.out.println("=====消费者record："+consumerRecord.count());
            for (ConsumerRecord<String, String> record : consumerRecord) {
               String key =  record.key();
               String value = record.value();
                System.out.println("=====key："+key+"======value："+value);
            }

        }



    }
    public static  Properties getConsumerProperties(){
        //属性
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "group");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        props.put("max.poll.records", 1000);
        props.put("auto.offset.reset", "earliest");
        props.put("key.deserializer", StringDeserializer.class.getName());
        props.put("value.deserializer", StringDeserializer.class.getName());

        return  props;
    }

}
