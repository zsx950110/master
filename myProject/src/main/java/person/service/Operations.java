package person.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.apache.commons.collections4.map.HashedMap;
import org.apache.poi.hwpf.HWPFDocument;
import org.hibernate.*;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import person.pojo.Book;
import person.pojo.TestUser;
import person.pojo.ToDo;
import person.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

import java.io.*;
import java.util.*;

@Service

@Transactional(rollbackFor = Exception.class)
public class Operations {

    private SessionFactory sessionFactory;
    private Session session;
    private static Logger logger = LoggerFactory.getLogger(Operations.class);

    @Autowired
    public Operations(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;


    }

    public Operations() {
    }
    //重启新事务
@Transactional(propagation = Propagation.REQUIRES_NEW)
    public void insertData() {

        TestUser testUser = new TestUser();
        testUser.setId(new Date().toString());
        testUser.setName("蛐蛐儿242群二群四");
        session.save(testUser);

          /*  String hql  = "update T_myTest set FnAME='zsdsdsds' where FID =:id";

            Query query = session.createSQLQuery(hql).setParameter("id","111");



            int i  =query.executeUpdate();

            if(i>0){
                logger.info("更新成功。。。。");
                List<TestUser> list = listData();
                System.out.println(list.get(0).getName()+">>>>>>>>>>>>>名字");
            }
*/

    }

    public List<TestUser> listData() {
        this.session = sessionFactory.getCurrentSession();

        String hql = " from TestUser";

        Query query = this.session.createQuery(hql);
        List<TestUser> listUsers = query.list();
        for (TestUser listUser : listUsers) {
            System.out.println(listUser.getName());
        }
        if (logger.isInfoEnabled()) {
            logger.info("查询成功！！！");
        }
        return listUsers;
    }

    public void deleteData(String id) {
        this.session = sessionFactory.getCurrentSession();
        String hql = "delete from ToDo where id =:id";
        //  String hql  = "update T_myTest set FNAME='存储' where FID =:id";
        Query query = session.createQuery(hql).setParameter("id", id);


        // query=null;

        try {
            int i = query.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

            this.insertData();

    }

    /*查找todo
     * */
    public List<ToDo> listToDos() {
        this.session = this.sessionFactory.getCurrentSession();
        Query query = session.createQuery("from ToDo");

        List<ToDo> todos = query.list();
        if (todos.size() > 0) {
            Jedis jedis = JedisUtil.getJedis();
            //将整个list转为json字符串
            String jsonList = JSON.toJSONString(todos);
            jedis.set("todosListJsonString", jsonList);
            HashMap<String, String> map = new HashMap<String, String>();
            for (ToDo todo : todos) {
                map.put(todo.getTaskId(), JSON.toJSONString(todo));
                //同时存taskids
                jedis.lpush("taskLists", todo.getTaskId());
            }
            jedis.hmset("todoList", map);
            JedisUtil.returnJedis(jedis);
        }

        return todos;
    }

    //获取所有taskids
    public List<String> listTaskIds() {
        this.session = this.sessionFactory.getCurrentSession();
        Query query = session.createQuery("select taskId from ToDo");
        List<String> list = query.list();
        Jedis jedis = JedisUtil.getJedis();
        for (String s : list) {
            jedis.lpush("taskLists", s);
        }
        return list;
    }

    //jedis测试
    public static void main(String[] args) {
        Jedis jedis = JedisUtil.getJedis();
        System.out.println("测试字符串》》》》》");
        jedis.set("name", "zsx");
        System.out.println(jedis.get("name"));

        System.out.println("测试list集合》》》》》》》》");
        jedis.lpush("list1", "zsx", "ws", "ls");
        List<String> lists = jedis.lrange("list1", 0, -1);
        for (String list : lists) {
            System.out.println(list);
        }
        jedis.rpush("list1", "right1");
        System.out.println("lrange" + jedis.lrange("list1", 0, -1));
        System.out.println(jedis.lindex("list1", 3));
        System.out.println("长度" + jedis.llen("list1"));
        System.out.println("测试sortedset》》》》》》");
        jedis.zadd("sortset", 1, "qq");
        jedis.zadd("sortset", 1.2, "ww");
        jedis.zadd("sortset", 0.1, "ee");
        System.out.println(jedis.zrange("sortset", 0, -1));
        System.out.println("测试hhash》》》》》");
        jedis.hset("hashtest", "name", "zsx");
        jedis.hset("hashtest", "password", "950110xn");
        System.out.println("hget测试》》》》" + jedis.hget("hashtest", "name"));
        System.out.println(jedis.hgetAll("hashtest"));
    }

    public static String insql(String column, List<String> values) {
        StringBuffer stringBuffer = new StringBuffer(column);
        stringBuffer.append("  in (");
        int count = 0;
        String string = "";
        for (String value : values) {
            stringBuffer.append("? ,");
            if (count > 950) {
                stringBuffer.append(")  or " + column + " in (");
                count = 0;
            }
            //如果是最后一个

            if (count == values.size() - 1) {
                string = stringBuffer.toString();
                string = string.substring(0, string.length() - 1);
            }
            count++;
        }
        string = string + " )";
        return string;
    }

    public List<Object[]> listDbaObject(int pagesize, int pagenum) {
        int all = pagenum * pagesize;
        int li = all - pagesize;
     /*   String sql = "select * from(\n" +
                "select owner, object_name, subobject_name, \n" +
                        "object_id, data_object_id, object_type, created,\n" +
                        " last_ddl_time, timestamp, status, temporary, generated,\n" +
                        "  secondary, namespace, edition_name,rownum as rownum_ from dba_1 \n" +
                        "  where rownum<:all ) where rownum_  >= :allBe and rownum_ <= :all";*/
        String sql = "select owner, object_name, subobject_name, \n" +
                "object_id, data_object_id, object_type, created,\n" +
                " last_ddl_time, timestamp, status, temporary, generated,\n" +
                "  secondary, namespace, edition_name,rownum as rownum_ from dba_2";
        this.session = sessionFactory.getCurrentSession();
        Query query = session.createSQLQuery(sql);//.setParameter("all",all).setParameter("allBe",li);
        System.out.println("》》》》》数据库查询前" + System.currentTimeMillis());
        List<Object[]> list = query.list();
        System.out.println("》》》》》》数据库查询后" + System.currentTimeMillis());
        saveToJedis(list);

        return list;
    }

    private void saveToJedis(List<Object[]> list) {
        Jedis jedis = JedisUtil.getJedis();
        for (Object[] objects : list) {
            jedis.lpush("dbaList", JSON.toJSONString(objects));
        }
        JedisUtil.returnJedis(jedis);
    }

    public void viewPage(int pageSize, int pageNum) {
        Jedis jedis = JedisUtil.getJedis();
        int f = (pageNum - 1) * pageSize;
        int r = f + pageSize;
        System.out.println("》》》》》》Redis之前" + System.currentTimeMillis());

        List<String> list = jedis.lrange("dbaList", 0, -1);
        System.out.println("》》》》》》Redis之后" + System.currentTimeMillis());
        System.out.println("list的长度为" + list.size());
        System.out.println("dbaList的长度为" + jedis.llen("dbaList"));
       /* for (String s : list) {
            List<Object> objects = JSON.parseArray(s,Object.class);
            System.out.println(objects.get(0));
        }*/
        JedisUtil.returnJedis(jedis);

    }

    //购物车增加
    public void shoppingCartAdd(String userId, String bookId) {

        Jedis jedis = JedisUtil.getJedis();
        boolean notExists = false;//StringUtils.isEmpty(jedis.hget(userId, bookId));
        try {
            if (notExists) {
                Map<String, String> map = new HashMap<>();
                map.put(bookId, "1");
                jedis.hmset(userId, map);
            } else {
                jedis.hincrBy(userId, bookId, 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JedisUtil.returnJedis(jedis);
        }


    }

    //购物车商品减少
    public void shoppingCardDecrease(String userId, String bookId) {
        Jedis jedis = JedisUtil.getJedis();
        //判断是否存在
        String get = jedis.hget(userId, bookId);
        boolean isExists = false;//StringUtils.isEmpty(get);
        if (!isExists) {
            boolean isZero = Integer.valueOf(get) <= 0;
            try {
                if (isZero) {
                    jedis.hdel(userId, bookId);
                } else {
                    jedis.hincrBy(userId, bookId, -1);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                JedisUtil.returnJedis(jedis);
            }

        }


    }

    //先保存图书造数据
    public void saveBook() {

        this.session = sessionFactory.getCurrentSession();
        //书分类
        String[] types = new String[]{"java", "python", "Linux", "PHP", "IOS", "汇编", "JavaScript", "mysql", "oracle"};
        //出版社
        String[] publishers = new String[]{"商务印书馆 ", "科学出版社 ", "国家图书馆出版社 ", "中央编译出版社 ", "译林出版社 ", "北京科学技术出版社 ", "社科文献出版社", "中央编译出版社 ", "生活·读书·新知三联书店 ", "中国金融出版社"};
        //价格直接给一个随机数范围
        String[] wrappers = new String[]{"平装", "精装", "软精装", "简装"};
        //评分1-5随机
        //折扣1-10随机数
        String names = this.bookNames();
        //每一本书保存时都按照查询条件去保存
        List<Book> books = new ArrayList<>();
        for (int i = 0; i < names.length() - 4; i++) {
            Book book = new Book();
            //名字
            Random randomName = new Random();
            int r = randomName.nextInt(names.length() - 4);
            book.setName(names.substring(r, r + 4));
            //出版方
            Random randomP = new Random();
            book.setPublisher(publishers[randomP.nextInt(publishers.length)]);

            //评分
            Random randomStar = new Random();
            //0-4，加1变为1-5
            book.setStar(randomStar.nextInt(5) + 1);

            //价格
            Random randomPrice = new Random();
            book.setPrice(randomPrice.nextDouble());

            //类型
            Random randomType = new Random();
            book.setType(types[randomType.nextInt(types.length)]);

            //包装
            Random randomWrap = new Random();
            book.setWrap(wrappers[randomWrap.nextInt(wrappers.length)]);

            book.setId(String.valueOf(System.currentTimeMillis()));
            //折扣
            Random randomDiscount = new Random();
            book.setDiscount(randomDiscount.nextInt(10) + 1);

            books.add(book);
            try {
                this.session.save(book);

            } catch (Exception e) {
                continue;
            }
        }
        //存redis
        this.redisSave(books);


    }

    public void redisSave(List<Book> books) {

        Jedis jedis = JedisUtil.getJedis();
        //批量操作使用管道
        Pipeline pipeline = jedis.pipelined();
        try {
            for (Book book : books) {
                pipeline.sadd(book.getPublisher(), book.getName());
                pipeline.zadd("star", book.getStar(), book.getName());
                pipeline.zadd("price", book.getPrice(), book.getName());
                pipeline.sadd(book.getType(), book.getName());
                pipeline.sadd(book.getWrap(), book.getName());
                pipeline.zadd("discount", book.getDiscount(), book.getName());
            }
            pipeline.sync();
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            try {
                pipeline.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //根据查询条件返回书名
    public List<String> searchBookByCondition(List<String> type, List<String> publisher,
                                            List<Map<String,Integer>> price, List<String> wrap, List<Map<String,Integer>>  star, List<Map<String,Integer>>  discount) {
        //返回
        List<String> books = new ArrayList<>();
        //单条件查并集，之后求交集
        books = this.intersection(books,listUnion(type));
        books = this.intersection(books,listUnion(publisher));
        books = this.intersection(books,listUnion(wrap));
        //价格
        Map<String, List<Map<String,Integer>>>priceMap = new HashedMap<>();
        priceMap.put("price",price);
       books  = this.intersection(books,this.listNumberCondition(priceMap));
        //星星
        Map<String, List<Map<String,Integer>>>starMap = new HashedMap<>();
        starMap.put("star",star);
        books  = this.intersection(books,this.listNumberCondition(starMap));
        //折扣
        Map<String, List<Map<String,Integer>>>discountMap = new HashedMap<>();
        discountMap.put("discount",discount);
        books  = this.intersection(books,this.listNumberCondition(discountMap));
        return books;
    }
    //求交集
    public List<String> intersection(List<String> books,List<String> parts){
        if(parts.isEmpty()||books.isEmpty()){
            books.addAll(parts);
        }else{
            books.retainAll(parts);
        }
        return  books;
    }

    //统一查询条件求并集
    public List<String> listUnion(List<String>conditions){
        if(!CollectionUtils.isEmpty(conditions)){
            Jedis jedis = JedisUtil.getJedis();
            //用于中间存储结果
            Set<String> temp = new HashSet<>();
            //list转为数组，用于union的参数
            String[] strings = new String[conditions.size()];
            strings= conditions.toArray(strings);
            temp = jedis.sunion(strings);
            JedisUtil.returnJedis(jedis);
            return new ArrayList<>(temp);
        }else {
            return new ArrayList<>();
        }

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

    //用来随机生成书名
    public String bookNames() {
        //读取文件
        String path = "F:\\test.txt";
        File file = new File(path);
        FileInputStream is = null;
        //读取的字符
        StringBuffer sb = new StringBuffer();
        //返回的结果
        try {
            is = new FileInputStream(file);
            //读
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String temp;
            while ((temp = reader.readLine()) != null) {
                 /* if(!StringUtils.isEmpty(temp)){
                   sb.append(temp.trim());
               }*/

            }


        } catch (IOException e) {
            logger.error("", e);
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
