package person.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import person.dao.IUserMapper;
import person.service.Operations;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.TimeUnit;

/*
 * 基于数据库的分布式锁
 * */
@Service("sqlLock")
public class SqlLock {
    private static  Logger logger = LoggerFactory.getLogger(Operations.class);
    @Autowired
    private  IUserMapper userMapper;
    /**
     * 加锁
     *
     * @param method，方法为ip+端口+全路径名+方法名
     * @param thread
     * @return
     */
    public  boolean lock(String method, String thread) {
        //如果已经拥有锁了 ，则直接返回true，否则去获取锁
        return this.currentThreadIsOwner(method,thread)|| trylock(method,thread);
    }

    /**
     * 加锁的数据库操作
     * @param method
     * @param thread
     * @return
     */
    public  boolean trylock(String method, String thread) {
        boolean isFailure = true;
        while (isFailure) {
            try {
                userMapper.insertLock(thread,method);
                isFailure = false;
                logger.info("线程{}加锁成功==========",thread);
            } catch (Exception e) {
                logger.error("锁{}被其他线程占用,当前线程{}无法获取",method,thread);
                isFailure = true;
            }
        }
        return true;
    }

    /**
     *  释放锁
     * @param method
     * @param thread
     * @return
     */
    public  boolean releasLock(String method,String thread){
        boolean isFailure = true;
        while (isFailure) {
            try {
              userMapper.deleteLockByMethod(method);
                isFailure = false;
                logger.info("线程{}锁释放成功===========",thread);
            } catch (Exception e) {
                logger.error("锁{}释放失败,释放锁的线程为{}",method,thread);
                isFailure = true;
            }
        }
        return true;
    }

    /**
     *  判断当前线程是否已经拥有了当前方法的锁
     * @param method
     * @param thread
     * @return
     */
    private boolean currentThreadIsOwner(String method,String thread){
        String id = userMapper.getLockByThreadAndMethod(method,thread);
      return !StringUtils.isEmpty(id);
    }
}
