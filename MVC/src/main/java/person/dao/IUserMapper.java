
package person.dao;


import person.pojo.Comment;
import person.pojo.TestUser;

/**
 * 用户操作的dao
 */
public interface IUserMapper {
    void insertUser(TestUser  user);
    TestUser getUser(String id);

}
