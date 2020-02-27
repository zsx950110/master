package provider.serviceImpl;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.fastjson.JSON;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import common.pojo.Book;
import common.pojo.ToDo;
import common.service.IOperationService;
import common.util.JedisUtil;
import org.apache.commons.dbcp.BasicDataSource;
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
import java.sql.SQLOutput;
import java.util.*;
//如果要加version的话,调用的时候也要加version,否则会报错找不到调用的服务
//@Service( group = "useful" )
@Transactional(rollbackFor = Exception.class)
public class Operations implements IOperationService {

    private SessionFactory sessionFactory;
    private Session session;
    private static Logger logger = LoggerFactory.getLogger(Operations.class);

    @Autowired
    public Operations(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;


    }

    public Operations() {
    }


    /*查找todo
     * */
    public  List<ToDo> listToDos() {
        System.out.println("当前线程名=========="+Thread.currentThread().getName());
        //获得RPC上下文
        boolean isProviderSide = RpcContext.getContext().isProviderSide();//是否是提供方
        System.out.println("=======是否是提供方：======"+isProviderSide);

        System.out.println(RpcContext.getContext().getLocalAddress());
        System.out.println(RpcContext.getContext().getRemotePort());
        System.out.println(RpcContext.getContext().getRemoteAddress());
       Object[]objects =  RpcContext.getContext().getArguments();
       if (RpcContext.getContext().getArguments()!=null){
           for (Object object : objects) {
               System.out.println(object.toString());
           }
       };

        System.out.println("进入服务提供者方法，开始调用listToDos》》》》》》》》");
       // this.session = Util.openSession();
       this.session = this.sessionFactory.openSession();

        Query query = session.createQuery("from ToDo");

        List<ToDo> todos = query.list();
      //  Util.closeSession(this.session);
        /*if (todos.size() > 0) {
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
*/
        return todos;
    }



    //先保存图书造数据
    public void saveBook() {

        this.session = sessionFactory.getCurrentSession();
        //书分类
        String[] types = new String[]{"java", "python", "Linux", "PHP", "IOS", "汇编", "JavaScript", "mysql", "oracle"};
        //出版社
        String[] publishers = new String[]{"商务印书馆 ", "科学出版社 ", "国家图书馆出版社 ", "中央编译出版社 ", "译林出版社 ", "北京科学技术出版社 ", "社科文献出版社", "中央编译出版社 ", "生活·读书·新知三联书店 ","中国金融出版社"};
        //价格直接给一个随机数范围
        String[] wrappers = new String[]{"平装","精装","软精装","简装"};
        //评分1-5随机
        //折扣1-10随机数
       String names= this.bookNames();
        //每一本书保存时都按照查询条件去保存
      List<Book> books = new ArrayList<Book>();
            for (int i =0; i<names.length()-4;i++){
                Book book = new Book();
                //名字
                Random randomName  =new Random();
                int r = randomName.nextInt(names.length()-4);
                book.setName(names.substring(r,r+4));
                //出版方
                Random randomP  =new Random();
                book.setPublisher(publishers[randomP.nextInt(publishers.length)]);

                //评分
                Random randomStar  =new Random();
                //0-4，加1变为1-5
                book.setStar(randomStar.nextInt(5)+1);

                //价格
                Random randomPrice  =new Random();
                book.setPrice(randomPrice.nextDouble());

                //类型
                Random randomType  =new Random();
                book.setType(types[randomType.nextInt(types.length)]);

                //包装
                Random randomWrap  =new Random();
                book.setWrap(wrappers[randomWrap.nextInt(wrappers.length)]);

                book.setId(String.valueOf(System.currentTimeMillis()));
                //折扣
                Random randomDiscount  =new Random();
                book.setDiscount(randomDiscount.nextInt(10)+1);

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
    public void redisSave(List<Book>books){

        Jedis jedis =JedisUtil.getJedis();
        //批量操作使用管道
        Pipeline pipeline = jedis.pipelined();
        try {
            for (Book book : books) {
                pipeline.sadd(book.getPublisher(),book.getName());
                pipeline.zadd("star",book.getStar(),book.getName());
                pipeline.zadd("price",book.getPrice(),book.getName());
                pipeline.sadd(book.getType(),book.getName());
                pipeline.sadd(book.getWrap(),book.getName());
                pipeline.zadd("discount",book.getDiscount(),book.getName());
            }
            pipeline.sync();
        } catch (Exception e) {
            logger.error("",e);
        } finally {
            try {
                pipeline.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public String bookNames(){
        //读取文件
        String path = "F:\\test.txt";
        File file = new File(path);
        FileInputStream is  =null;
        //读取的字符
        StringBuffer sb = new StringBuffer();
        //返回的结果
        try {
            is = new FileInputStream(file);
            //读
            BufferedReader reader = new BufferedReader(new InputStreamReader(is,"UTF-8"));
            String temp;
           while ((temp=reader.readLine())!=null){
                  if(!StringUtils.isEmpty(temp)){
                   sb.append(temp.trim());
               }

           }


        } catch (IOException e) {
            logger.error("",e);
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return  sb.toString();
    }
}
