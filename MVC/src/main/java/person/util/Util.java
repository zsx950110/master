package person.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import javax.servlet.http.HttpServletRequest;

public class Util {
    private static Configuration configuration;
    private static SessionFactory sessionFactory;
    private  static Session session;

    static {

       // configuration  = new Configuration().configure();
    //    ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();
    //     sessionFactory = configuration.buildSessionFactory(serviceRegistry);

        }
        //获得session

        public static  Session openSession(){
        session = sessionFactory.getCurrentSession();
        return session;
    }
    public  static void closeSession(Session session){

        session.close();
        sessionFactory.close();
    }

    public static String getMethodName(HttpServletRequest request){
        if(request==null){
            return Thread.currentThread().getStackTrace()[2].getMethodName();
        }
        StringBuffer sb = new StringBuffer();
        sb.append(request.getRemoteAddr() );
        sb.append(request.getServerPort());
        sb.append(Thread.currentThread().getStackTrace()[2].getMethodName()); //上一级方法执行的堆栈
        System.out.println("方法名："+sb.toString());

        return sb.toString();
    }
    public static String getThreadName(HttpServletRequest request){
        if (request==null){
            return Thread.currentThread().getName();
        }
        StringBuffer sb = new StringBuffer();
        sb.append(request.getRemoteAddr() );
        sb.append(request.getServerPort());
        sb.append(Thread.currentThread().getName());
        System.out.println("线程名："+sb.toString());
        return sb.toString();
    }
}
