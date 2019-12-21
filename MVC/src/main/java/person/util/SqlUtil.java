package person.util;


import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;


/**
 *
 */
public class SqlUtil {
    private static SqlSessionFactory sqlSessionFactory;
    private static Reader read;
  static {
      try {
          read = Resources.getResourceAsReader("mybatis-cfg.xml");
      } catch (IOException e) {
          e.printStackTrace();
      }
      sqlSessionFactory = new SqlSessionFactoryBuilder().build(read);
  }
  public  static SqlSession openSession(){
     return sqlSessionFactory.openSession();
  }
  public  static  void closeSession(SqlSession sqlSession){
      if (sqlSession!=null){
          sqlSession.close();
      }
  }
}
