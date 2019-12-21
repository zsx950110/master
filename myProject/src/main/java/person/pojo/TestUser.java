package person.pojo;

public class TestUser {
    private String id;
    private String namess;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getnamess() {
        return namess;
    }

    public void setnamess(String namess) {
        this.namess = namess;
    }

    @Override
    public String toString() {
        return "TestUser{" +
                "id='" + id + '\'' +
                ", namess='" + namess + '\'' +
                '}';
    }
}
