package person.service;

import com.alibaba.fastjson.JSON;
import org.apache.commons.collections4.map.HashedMap;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import person.dao.IUserMapper;
import person.pojo.TestUser;
import person.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

import javax.annotation.Resource;
import java.io.*;
import java.util.*;

@Service

@Transactional(rollbackFor = Exception.class)
public class Operations {
    @Resource
    IUserMapper iUserMapper;
    Logger logger = LoggerFactory.getLogger(Operations.class);
    public Operations() {
    }

    //参数的key是zset中的key，即筛选条件，value是对应的最大值和最小值
    public List<String> listNumberCondition(Map<String,List<Map<String,Integer>>> rangesMap){
        if(!CollectionUtils.isEmpty(rangesMap)){
            Jedis jedis = JedisUtil.getJedis();
            Set<String> temp = new HashSet<>();
            //外层循环的key其实只有一个
            for (String s : rangesMap.keySet()) {
                List<Map<String,Integer>> ranges  =rangesMap.get(s);
                //对同一类查询条件的不同范围值进行遍历
                if(!CollectionUtils.isEmpty(ranges)){
                    for (Map<String, Integer> map : ranges) {
                        int min = map.get("min");
                        int max = map.get("max");
                        //同一种查询条件不同范围是互斥的，不会覆盖
                        temp.addAll(jedis.zrangeByScore(s,String.valueOf(min),String.valueOf(max)));
                    }
                }

            }
            JedisUtil.returnJedis(jedis);
            return  new ArrayList<>(temp);
        }else{
            return  new ArrayList<>();
        }


    }


    public void  insert() throws Exception{
       logger.info("进入insert方法。。。。。");
        TestUser testUser = new TestUser();
        testUser.setNamess("张三");
        testUser.setId("11111");

            iUserMapper.insertUser(testUser);


    }
    public synchronized void  update() throws Exception{
        logger.info("进入update方法。。。。。");
        TestUser testUser = new TestUser();
        testUser.setNamess("张三");
        //在原有的基础上加1
        String id=iUserMapper.getIdByName("张三");
        String id2 = String.valueOf(Integer.parseInt(id)+1);
        testUser.setId(id2);
        iUserMapper.updateUser(testUser);
    }
}
