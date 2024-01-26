package Management.Bookstore;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "BookID", nullable = false)
    private Long bookID;
    @Column(name = "Title", nullable = false)
    private String title;
    @Column(name = "Author", nullable = false)
    private String author;
    @Column(name = "Genre", nullable = false)
    private String genre;
    @Column(name = "Price", nullable = false)
    private BigDecimal price;
    @Column(name = "QuantityInStock", nullable = false)
    private Integer quantityInStock;

    public Book() {}
    public Book(String title, String author, String genre, BigDecimal price, Integer quantityInStock) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.price = price;
        this.quantityInStock = quantityInStock;
    }

    public Long getBookID() {
        return bookID;
    }
    public String getTitle() {
        return title;
    }
    public String getAuthor() {
        return author;
    }
    public String getGenre() {
        return genre;
    }
    public BigDecimal getPrice() {
        return price;
    }
    public Integer getQuantityInStock() {
        return quantityInStock;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public void setGenre(String genre) {
        this.genre = genre;
    }
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    public void setQuantityInStock(Integer quantityInStock) {
        this.quantityInStock = quantityInStock;
    }
}
