package Management.Bookstore;
import jakarta.persistence.*;

@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "CustomerID")
    private Long customerID;
    @Column(name = "Name", nullable = false)
    private String name;
    @Column(name = "Email", nullable = false, unique = true)
    private String email;
    @Column(name = "Phone", nullable = false)
    private String phone;

    public Customer() {}
    public Customer(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }
    public Long getCustomerID() {
        return customerID;
    }
    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
    public String getPhone() {
        return phone;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

}
