
package person.pojo;
import java.util.Date;


/**
 * @description dba_objects
 * @author Zhang Shaoxuan
 * @version 1.0, 2019-2-25
 * @since 1.0, 2019-2-25
 */
public class DbaObject {

  private  String owner;

    @Override
    public String toString() {
        return "DbaObject{" +
                "owner='" + owner + '\'' +
                ", object_name='" + object_name + '\'' +
                ", subobject_name='" + subobject_name + '\'' +
                ", object_id=" + object_id +
                ", namespace='" + namespace + '\'' +
                ", edition_name='" + edition_name + '\'' +
                ", secondary='" + secondary + '\'' +
                ", last_ddl_time=" + last_ddl_time +
                ", object_type='" + object_type + '\'' +
                '}';
    }

    private  String object_name;
  private  String subobject_name;
  private  Integer object_id;
  private  String namespace;
  private  String edition_name;
  private  String secondary;
  private  Date last_ddl_time;
  private  String object_type;

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getObject_name() {
        return object_name;
    }

    public void setObject_name(String object_name) {
        this.object_name = object_name;
    }

    public String getSubobject_name() {
        return subobject_name;
    }

    public void setSubobject_name(String subobject_name) {
        this.subobject_name = subobject_name;
    }

    public Integer getObject_id() {
        return object_id;
    }

    public void setObject_id(Integer object_id) {
        this.object_id = object_id;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getEdition_name() {
        return edition_name;
    }

    public void setEdition_name(String edition_name) {
        this.edition_name = edition_name;
    }

    public String getSecondary() {
        return secondary;
    }

    public void setSecondary(String secondary) {
        this.secondary = secondary;
    }

    public Date getLast_ddl_time() {
        return last_ddl_time;
    }

    public void setLast_ddl_time(Date last_ddl_time) {
        this.last_ddl_time = last_ddl_time;
    }

    public String getObject_type() {
        return object_type;
    }

    public void setObject_type(String object_type) {
        this.object_type = object_type;
    }
}
