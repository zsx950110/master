
package person.pojo;
import javax.xml.crypto.Data;
import java.util.Date;


/**
 * @description评论redis测试pojo
 * @author Zhang Shaoxuan
 * @version 1.0, 2019-2-25
 * @since 1.0, 2019-2-25
 */
public class Comment {
private  String id;
private  String comment;
private  Integer star;
private String userId;
private Date commentDate;

    public Date getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(Date commentDate) {
        this.commentDate = commentDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getStar() {
        return star;
    }

    public void setStar(Integer star) {
        this.star = star;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", comment='" + comment + '\'' +
                ", star='" + star + '\'' +
                '}';
    }
}
