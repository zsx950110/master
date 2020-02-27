
package person.pojo;


/**
 * @description 书籍
 * @author Zhang Shaoxuan
 * @version 1.0, 2019-2-25
 * @since 1.0,  2019-2-25
 */
public class Book {
    private static  Book book;
    public  static  Book getInstance(){

        if (book==null){
            synchronized (Book.class){
                if (book==null){
                    book = new Book();
                }
            }
        }
        return  book;
    }
    public Book( ) {

    }
    Comment comment;

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public Comment getComment() {
        return comment;
    }

    @Override
    public String toString() {
        return "Book{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", publisher='" + publisher + '\'' +
                ", price=" + price +
                ", wrap='" + wrap + '\'' +
                ", star=" + star +
                ", discount=" + discount +
                '}';
    }
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private  String name;
    private  String type;
    private  String publisher;

    private  Double price ;
   private  String wrap;
   private  Integer star;
   private  Integer discount;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getWrap() {
        return wrap;
    }

    public void setWrap(String wrap) {
        this.wrap = wrap;
    }

    public Integer getStar() {
        return star;
    }

    public void setStar(Integer star) {
        this.star = star;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

}
