package person.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.hibernate.*;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import person.pojo.TestUser;
import person.pojo.ToDo;
import person.util.JedisUtil;
import redis.clients.jedis.Jedis;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service

@Transactional(rollbackFor = Exception.class)
public class Operations {

    private SessionFactory sessionFactory;
    private Session session;
    private Logger logger = LoggerFactory.getLogger(Operations.class);

    @Autowired
    public Operations(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;


    }


    public void insertData() {

        TestUser testUser = new TestUser();
        testUser.setId(new Date().toString());
        testUser.setName("李1333s四");
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
        String hql = "delete from TestUser where id =:id";
        //  String hql  = "update T_myTest set FNAME='存储' where FID =:id";
        Query query = session.createQuery(hql).setParameter("id", "111");
        System.out.println(listData().toString());

        // query=null;

        try {
            int i = query.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        insertData();

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

    public List<Object[]> listDbaObject(int pagesize,int pagenum) {
        int all = pagenum*pagesize;
        int li = all-pagesize;
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
        System.out.println("》》》》》数据库查询前"+System.currentTimeMillis());
        List<Object[]> list = query.list();
        System.out.println("》》》》》》数据库查询后"+System.currentTimeMillis());
       saveToJedis(list);

        return list;
    }
    private void saveToJedis(List<Object[]> list){
        Jedis jedis = JedisUtil.getJedis();
        for (Object[] objects : list) {
            jedis.lpush("dbaList",JSON.toJSONString(objects));
        }
        JedisUtil.returnJedis(jedis);
    }
    public void  viewPage(int pageSize,int pageNum){
        Jedis jedis = JedisUtil.getJedis();
        int f = (pageNum-1)*pageSize;
        int r = f+pageSize;
        System.out.println("》》》》》》Redis之前"+System.currentTimeMillis());

        List<String> list =  jedis.lrange("dbaList",0,-1);
        System.out.println("》》》》》》Redis之后"+System.currentTimeMillis());
        System.out.println("list的长度为"+list.size());
        System.out.println("dbaList的长度为"+jedis.llen("dbaList"));
       /* for (String s : list) {
            List<Object> objects = JSON.parseArray(s,Object.class);
            System.out.println(objects.get(0));
        }*/
        JedisUtil.returnJedis(jedis);

    }
//购物车增加
    public void shoppingCartAdd(String userId,String bookId){

        Jedis jedis = JedisUtil.getJedis();
        boolean notExists = StringUtils.isEmpty(jedis.hget(userId,bookId));
        try {
            if (notExists){
                Map<String,String> map = new HashMap<>();
                map.put(bookId,"1");
                jedis.hmset(userId,map);
            }else {
                jedis.hincrBy(userId,bookId,1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JedisUtil.returnJedis(jedis);
        }


    }
    //购物车商品减少
    public void shoppingCardDecrease(String userId,String bookId){
        Jedis jedis = JedisUtil.getJedis();
        //判断是否存在
        String get = jedis.hget(userId,bookId);
        boolean isExists = StringUtils.isEmpty(get);
        if (!isExists){
            boolean isZero = Integer.valueOf(get)<=0;
            try {
                if (isZero){
                    jedis.hdel(userId,bookId);
                }else {
                    jedis.hincrBy(userId,bookId,-1);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                JedisUtil.returnJedis(jedis);
            }

        }


    }
    //展示购物车
    public Map<String, String> showCart(String userId){
        Jedis jedis = JedisUtil.getJedis();
       Map<String,String> map = new HashMap<>();
        try {
           map = jedis.hgetAll(userId);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JedisUtil.returnJedis(jedis);
        }
        return map;
    }
}
