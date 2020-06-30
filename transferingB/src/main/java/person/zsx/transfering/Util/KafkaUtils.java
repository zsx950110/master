package person.zsx.transfering.Util;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
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
        /**
         * 连接broker用，同producer端配置，不用连太多broker，只需要连接其中几个，
         * 使得某个broker挂掉后仍能连上集群，必输
         */
        props.put("bootstrap.servers", "192.168.43.152:9092");
        /**
         * 消费组名称必输，一般指定一个有业务含义的名称
         */
        props.put("group.id", "accountB");
        props.put("enable.auto.commit", "false");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        props.put("max.poll.interval.ms", "5000");
        props.put("max.poll.records", 10);
        props.put("auto.offset.reset", "earliest");
        /**
         * 解序列化，使用的类要与producer端使用的相对应，必输
         */
        props.put("key.deserializer", StringDeserializer.class.getName());
        //同上 必输
        props.put("value.deserializer", StringDeserializer.class.getName());

        return  props;
    }
    /**
     * 生产端的属性封装
     */
    public static  Properties getProducerProperties(){
        //属性
        Properties props = new Properties();
        /**
         *  参数说明： props.put("bootstrap.servers", "192.168.43.152:9092,192.168.43.152:9093");
         *  该参数指定连接的broker，如果集群中broker很多的话，只需要指定部分即可，指定多个的原因是因为怕其中一个挂了之后
         *  producer客户端还能通过其他的ip:port找到集群
         * 必输
         */
        props.put("bootstrap.servers", "192.168.43.152:9092");
        props.put("retries", 0);
       // props.put("batch.size", 16384);
        /**
         * 参数是为消息做序列化用的，任何消息都必须是字节数组才能发送到broker上，
         * 将一个String字符串转成字节数组
         * 必输
         */
        props.put("key.serializer", StringSerializer.class.getName());
        /**
         * 必输
         */
        props.put("value.serializer", StringSerializer.class.getName());
      //  props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 106);
        props.put(ProducerConfig.MAX_BLOCK_MS_CONFIG, 100000);
        return  props;
    }
}
