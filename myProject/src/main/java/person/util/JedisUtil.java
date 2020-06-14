package person.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.*;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/*
 * jedis连接池类
 * */
public class JedisUtil {

    private static JedisPool jedisPool;
    private static JedisPoolConfig config;
    //最大活跃连接
    private static final int MAX_ACTIVE = 5;
    private static final String ADD = "192.168.43.152";
    private static final int PORT = 6379;
    private static Jedis jedis;
    private static JedisCluster jedisCluster;
    private static Logger logger = LoggerFactory.getLogger(JedisUtil.class);
    //集群连接
    private static Set<HostAndPort> hostAndPortSet = new HashSet<>();

    static {
        //由于有六个集群这里添加六个HostAndPort对象
        hostAndPortSet.add(new HostAndPort("192.168.43.152", 6001));
        hostAndPortSet.add(new HostAndPort("192.168.43.152", 6002));
        hostAndPortSet.add(new HostAndPort("192.168.43.152", 7001));
        hostAndPortSet.add(new HostAndPort("192.168.43.152", 7002));
        hostAndPortSet.add(new HostAndPort("192.168.43.152", 8001));
        hostAndPortSet.add(new HostAndPort("192.168.43.152", 8002));

    }

    //单例
    private JedisUtil() {
    }

    static {
        try {

            config = new JedisPoolConfig();
            //最多连接数
            config.setMaxTotal(MAX_ACTIVE);

            logger.info("连接池配置成功.........");
        } catch (Exception e) {
            logger.error("连接池配置失败！！！！");
            e.printStackTrace();
        }
    }

    public static JedisCluster getJedisCluster() {
        if (jedisCluster == null) {
            synchronized (JedisUtil.class) {
                if (jedisCluster == null) {
                    return new JedisCluster(hostAndPortSet,config);
                }
            }
        }
        return jedisCluster;
    }
    /**
    * @Author: Zhang Shaoxuan
    * @Description:  集群关闭
    * @DateTime: g 23:06
    * @Params:
    * @Return
    */
    public static void closeCluster(JedisCluster jedisCluster) {
        if (jedisCluster != null) {
            try {
                jedisCluster.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //获得jedis对象,连接池为单例
    public static Jedis getJedis() {
        synchronized (JedisUtil.class) {
            if (jedisPool == null) {
                jedisPool = new JedisPool(config, ADD, PORT);
            }
        }
        return jedisPool == null ? null : jedisPool.getResource();
    }

    //将连接释放,returnResource被官方放弃，不释放，则连接很快达到最大会发生阻塞
    public static void returnJedis(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }


    }
}
