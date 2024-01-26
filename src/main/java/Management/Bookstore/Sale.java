package Management.Bookstore;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "sales")
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SaleID", nullable = false)
    private Long saleID;
    @ManyToOne
    @JoinColumn(name = "BookID", nullable = false)
    private Book book;
    @ManyToOne
    @JoinColumn(name = "CustomerID", nullable = false)
    private Customer customer;
    @Column(name = "DateOfSale", nullable = false)
    private Date dateOfSale;
    @Column(name = "QuantitySold", nullable = false)
    private Integer quantitySold;
    @Column(name = "TotalPrice", nullable = false)
    private BigDecimal totalPrice;

    public Sale() {}
    public Sale(Book book, Customer customer, Date dateOfSale, Integer quantitySold, BigDecimal totalPrice) {
        this.book = book;
        this.customer = customer;
        this.dateOfSale = dateOfSale;
        this.quantitySold = quantitySold;
        this.totalPrice = totalPrice;
    }

    public Long getSaleID() {
        return saleID;
    }
    public Long getBookID() {
        return book.getBookID();
    }
    public Long getCustomerID() {
        return customer.getCustomerID();
    }
    public Date getDateOfSale() {
        return dateOfSale;
    }
    public Integer getQuantitySold() {
        return quantitySold;
    }
    public BigDecimal getTotalPrice() {
        return totalPrice;
    }
    public void setBookID(Book book) {
        this.book = book;
    }
    public void setCustomerID(Customer customer) {
        this.customer = customer;
    }
    public void setDateOfSale(Date dateOfSale) {
        this.dateOfSale = dateOfSale;
    }
    public void setQuantitySold(Integer quantitySold) {
        this.quantitySold = quantitySold;
    }
    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}
