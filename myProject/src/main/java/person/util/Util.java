package person.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;


import org.hibernate.cfg.Configuration ;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

public class Util {
    private static Configuration configuration;
    private static SessionFactory sessionFactory;
    private  static Session session;

    static {

        configuration  = new Configuration().configure();
        ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();
         sessionFactory = configuration.buildSessionFactory(serviceRegistry);

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

}
