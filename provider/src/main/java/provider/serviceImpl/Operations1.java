package provider.serviceImpl;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.sun.xml.bind.v2.TODO;
import common.pojo.Book;
import common.pojo.ToDo;
import common.service.IOperationService;
import common.util.JedisUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

@Service(version = "1.0.0")
@Transactional(rollbackFor = Exception.class)
public class Operations1 implements IOperationService {

    private SessionFactory sessionFactory;
    private Session session;
    private static Logger logger = LoggerFactory.getLogger(Operations1.class);

    @Autowired
    public Operations1(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;


    }

    public Operations1() {
    }


    @Override
    public String bookNames() {
        return null;
    }

    @Override
    public void redisSave(List<Book> books) {

    }

    @Override
    public void saveBook() {

    }

    /*查找todo
     * */
    @Override
    public  List<ToDo> listToDos() {
        List<ToDo> list = new ArrayList<>();
      return list;
    }


}
