
package person.dao;


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
}
