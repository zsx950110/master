package person.zsx.transfering.Util;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

/**
 * @Author: zhangshaoxuan
 * @DateTime: 2020/6/7 20:28
 * @Description:
 */
public class KafkaUtils {
    static  Producer  kafkaProducer= null;
    static  Consumer kafkaConsumer = null;
    /**
     * 获得生产者
     * @return
     */
    public static  Producer getKafkaProducer(){
        if (kafkaProducer==null){
            synchronized (KafkaUtils.class){
                if (kafkaProducer==null){
                    kafkaProducer =   new KafkaProducer<>(getProducerProperties());
                }
            }
        }
        return kafkaProducer;

    }
    public static Consumer getKafkaConsumer(){
        if (kafkaConsumer==null){
            synchronized (KafkaUtils.class){
                if (kafkaConsumer==null){
                    kafkaConsumer =   new KafkaConsumer(getConsumerProperties());
                }
            }
        }
        return kafkaConsumer;
    }

    /**
     * 消费端的属性
     * @return
     */
    public static Properties getConsumerProperties(){
        //属性
        Properties props = new Properties();
        props.put("bootstrap.servers", "192.168.43.152:9092");
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
    /**
     * 生产端的属性封装
     */
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
