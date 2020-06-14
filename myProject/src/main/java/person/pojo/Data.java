
package person.pojo;

import org.hibernate.type.StringClobType;

//设计乐观锁用到的数据
public class Data {
private   Long version  = System.currentTimeMillis() ;
private Integer string = 0;

    @Override
    public String toString() {
        return "Data{" +
                "version=" + version +
                ", string='" + string + '\'' +
                '}';
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version  ;
    }

    public Integer getString() {
        return string;
    }

    public void setString(Integer string) {
        this.string = string;
    }
}
