
package person.dao;


import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import person.pojo.Comment;
import person.pojo.TestUser;

import javax.annotation.Resource;

/**
 * 用户操作的dao
 */
@Repository
public interface IUserMapper {
    void insertUser(TestUser  user);
    TestUser getUser(String id);
    void updateUser(TestUser user);
    String getIdByName(String name);
    int insertLock (@Param("thread") String thread, @Param("method") String method );
    int deleteLockByMethod(@Param("method") String method);
    String getLockByThreadAndMethod (@Param("method")String method,@Param("thread")String thread);
}
