package person.pojo;

public class TestUser {
    private String id;
    private String namess;


    @Override
    public String toString() {
        return "TestUser{" +
                "id='" + id + '\'' +
                ", name='" + namess + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNamess() {
        return namess;
    }

    public void setNamess(String name) {
        this.namess = name;
    }
}
