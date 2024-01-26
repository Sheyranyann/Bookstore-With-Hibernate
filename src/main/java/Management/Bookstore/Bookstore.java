package Management.Bookstore;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.query.Query;
public class Bookstore {

    private final Scanner scanner = new Scanner(System.in);
    // Perform operations in Bookstore
    public void management(){

        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        while (true) {
            System.out.println("Choose the operation you want to perform");
            String operations =
                    "1. Update book details\n" +
                    "2. List books by genre\n" +
                    "3. List books by author\n" +
                    "4. Update customer information\n" +
                    "5. View customer's purchase history\n" +
                    "6. Perform new sale\n" +
                    "7. Calculate total revenue by genre\n" +
                    "8. Generate book sales report\n" +
                    "9. Generate revenue by genre report\n" +
                    "10. View all books\n" +
                    "11. View all customers\n" +
                    "0. Exit";

            System.out.println(operations);
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine();

            switch (choice) {
                    case "1":
                        System.out.println("Do you want to view the list of books?");
                        System.out.println("1. Yes");
                        System.out.println("2. No");
                        while(true) {
                            System.out.print("Enter your choice: ");
                            choice = scanner.nextLine();
                            if(choice.equals("1")) {
                                viewAllBooks(session);
                                break;
                            }
                            if(choice.equals("2")) {
                                break;
                            }
                            System.out.println("Not valid choice. Try again");
                        }
                        updateBookDetails(session);
                        break;

                    case "2":
                        listBooksByGenre(session);
                        break;

                    case "3":
                        listBooksByAuthor(session);
                        break;

                    case "4":
                        System.out.println("Do you want to view the list of customers?");
                        System.out.println("1. Yes");
                        System.out.println("2. No");
                        while(true) {
                            System.out.print("Enter your choice: ");
                            choice = scanner.nextLine();
                            if(choice.equals("1")) {
                                viewAllCustomers(session);
                                break;
                            }
                            if(choice.equals("2")) {
                                break;
                            }
                            System.out.println("Not valid choice. Try again");
                        }
                        updateCustomerInfo(session);
                        break;

                    case "5":
                        System.out.println("Do you want to view the list of customers?");
                        System.out.println("1. Yes");
                        System.out.println("2. No");
                        while(true) {
                            System.out.print("Enter your choice: ");
                            choice = scanner.nextLine();
                            if(choice.equals("1")) {
                                viewAllCustomers(session);
                                break;
                            }
                            if(choice.equals("2")) {
                                break;
                            }
                            System.out.println("Not valid choice. Try again");
                        }
                        viewPurchaseHistory(session);
                        break;

                    case "6":
                        performNewSale(session);
                        break;

                    case "7":
                        totalRevenueByGenre(session);
                        break;

                    case "8":
                        bookSalesReport(session);
                        break;

                    case "9":
                        totalRevenueReportByGenres(session);
                        break;

                    case "10":
                        viewAllBooks(session);
                        break;

                    case "11":
                        viewAllCustomers(session);
                        break;

                    case "0":
                        System.out.println("Exiting the app...");
                        session.close();
                        return;
                    default:
                        System.out.println("Not valid choice. Please try again.");
            }
        }

    }

    // Change value of selected column for row (specified by book ID)
    private void updateBookDetails(Session session){
        try {
            System.out.print("Enter BookID to update: ");
            Long bookId = Long.parseLong(scanner.nextLine());
            Book book = session.get(Book.class, bookId);
            if (book == null) {
                System.out.println("Not valid book ID.");
                return;
            }

            String columnToChange = "";
            String change = "";
            System.out.println("Which column do you want to update?");
            System.out.println("1. Title");
            System.out.println("2. Author");
            System.out.println("3. Genre");
            System.out.println("4. Price");
            System.out.println("5. Quantity in stock");
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    columnToChange = "title";
                    System.out.print("Enter the new title: ");
                    change = scanner.nextLine();
                    break;
                case "2":
                    columnToChange = "author";
                    System.out.print("Enter the new author: ");
                    change = scanner.nextLine();
                    break;
                case "3":
                    columnToChange = "genre";
                    System.out.print("Enter the new genre: ");
                    change = scanner.nextLine();
                    break;
                case "4":
                    columnToChange = "price";
                    System.out.print("Enter the new price: ");
                    change = scanner.nextLine();
                    break;
                case "5":
                    columnToChange = "quantityInStock";
                    System.out.print("Enter the new quantity in stock: ");
                    change = scanner.nextLine();
                    break;
                default:
                    System.out.println("Invalid choice.");
                    return;
            }

            Transaction transaction = session.beginTransaction();
            try {
                switch (columnToChange) {
                    case "title":
                        book.setTitle(change);
                    case "author":
                        book.setAuthor(change);
                    case "genre":
                        book.setGenre(change);
                        break;
                    case "price":
                        book.setPrice(new BigDecimal(change));
                        break;
                    case "quantityInStock":
                        book.setQuantityInStock(Integer.parseInt(change));
                        break;
                }
                session.persist(book);
                session.flush();
                transaction.commit();
                System.out.println("Book details are updated");
            } catch (NumberFormatException e) {
                transaction.rollback();
                System.out.println("Invalid input type.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input type.");
        }
    }


    // Change value of selected column for row (specified by customer ID)
    private void updateCustomerInfo(Session session) {
        try {
            System.out.print("Enter CustomerID to update: ");
            Long customerID = Long.parseLong(scanner.nextLine());

            Customer customer = session.get(Customer.class, customerID);

            if (customer == null) {
                System.out.println("Not valid customer ID");
                return;
            }
            String change = "";
            System.out.println("Which column do you want to update?");
            System.out.println("1. Name");
            System.out.println("2. Email");
            System.out.println("3. Phone");
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    System.out.print("Enter the new name: ");
                    change = scanner.nextLine();
                    customer.setName(change);
                    break;
                case "2":
                    System.out.print("Enter the new email: ");
                    change = scanner.nextLine();
                    customer.setEmail(change);
                    break;
                case "3":
                    System.out.print("Enter the new phone: ");
                    change = scanner.nextLine();
                    customer.setPhone(change);
                    break;
                default:
                    System.out.println("Invalid choice.");
                    return;
            }

            Transaction transaction = session.beginTransaction();
            session.persist(customer);
            session.flush();
            transaction.commit();
            System.out.println("Customer information is updated");
        } catch (NumberFormatException e) {
            System.out.println("Invalid input type.");
        } catch (HibernateException e) {
            if (e.getCause() instanceof ConstraintViolationException) {
                System.out.println("Error: The new email already exists. Please choose a unique email.");
            } else {
                e.printStackTrace();
            }
        }
    }

    // Show all books with given genre
    private void listBooksByGenre(Session session) {
        System.out.print("Enter genre to list books: ");
        String genre = scanner.nextLine();
        String hql = "FROM Book WHERE genre = :genre";
        Query<Book> query = session.createQuery(hql, Book.class);
        query.setParameter("genre", genre);
        List<Book> books = query.getResultList();
        if (books.isEmpty()) {
            System.out.println("No books found for the given genre.");
        } else {
            listBooks(books);
        }
    }

    // Show all books with given author
    private void listBooksByAuthor(Session session) {
        System.out.print("Enter author to list books: ");
        String author = scanner.nextLine();
        String hql = "FROM Book WHERE author = :author";
        Query<Book> query = session.createQuery(hql, Book.class);
        query.setParameter("author", author);
        List<Book> books = query.getResultList();
        if (books.isEmpty()) {
            System.out.println("No books found for the given author.");
        } else {
            listBooks(books);
        }
    }
    // Show information about given list of books
    private void listBooks(List<Book> books) {
        System.out.printf("%-10s | %-30s | %-20s | %-20s | %-10s | %-15s%n",
                "Book ID", "Title", "Author", "Genre", "Price", "Quantity in Stock");
        System.out.println("----------------------------------------------------------------------------------------------------------------------------");
        for (Book book : books) {
            System.out.printf("%-10d | %-30s | %-20s | %-20s | %-10.2f | %-15d%n",
                    book.getBookID(), book.getTitle(), book.getAuthor(), book.getGenre(), book.getPrice(), book.getQuantityInStock());
            System.out.println();
        }
    }

    // Show information about given list of customers
    private void listCustomers(List<Customer> customers) {
        System.out.printf("%-15s | %-25s | %-60s | %-15s%n",
                "Customer ID", "Name", "Email", "Phone");
        System.out.println("--------------------------------------------------------------------------------------------------------------------------");
        for (Customer customer : customers) {
            System.out.printf("%-15d | %-25s | %-60s | %-15s%n",
                    customer.getCustomerID(), customer.getName(), customer.getEmail(), customer.getPhone());
        }
    }

    // View purchase history for customer
    private void viewPurchaseHistory(Session session) {
        System.out.print("Enter CustomerID to view his/her purchase history: ");
        Long customerID = 0L;
        try {
            customerID = Long.parseLong(scanner.nextLine());

            if (session.get(Customer.class, customerID) == null) {
                System.out.println("Not Valid customer id");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Not valid input");
            return;
        }

        String hql = "SELECT b.bookID, b.title, b.author, b.genre, b.price, s.dateOfSale, s.quantitySold, s.totalPrice " +
                "FROM Sale s JOIN s.book b " +
                "WHERE s.customer.customerID = :customerID";

        Query<Object[]> query = session.createQuery(hql, Object[].class);
        query.setParameter("customerID", customerID);
        List<Object[]> result = query.list();

        System.out.printf("%-8s %-30s %-20s %-20s %-8s %-10s %-12s %-12s\n",
                "BookID", "Title", "Author", "Genre", "Price", "DateOfSale", "QuantitySold", "TotalPrice");
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------");
        for (Object[] row : result) {
            Long bookId = (Long) row[0];
            String title = (String) row[1];
            String author = (String) row[2];
            String genre = (String) row[3];
            BigDecimal price = (BigDecimal) row[4];
            java.util.Date dateOfSale = (java.util.Date) row[5];
            Integer quantitySold = (Integer) row[6];
            BigDecimal totalPrice = (BigDecimal) row[7];
            System.out.printf("%-8d %-30s %-20s %-20s %-8.2f %-10s %-12d %-12.2f\n",
                    bookId, title, author, genre, price, dateOfSale, quantitySold, totalPrice);
        }
    }



    // Add new row to Sales table
    private void performNewSale(Session session) {
        long bookID = 0L;
        long customerID = 0L;
        int quantitySold = 0;
        try {
            System.out.print("Enter BookID: ");
            bookID = Long.parseLong(scanner.nextLine());
            System.out.print("Enter CustomerID: ");
            customerID = Long.parseLong(scanner.nextLine());
            System.out.print("Enter quantity sold: ");
            quantitySold = Integer.parseInt(scanner.nextLine());
            if (!hasEnoughQuantity(session, bookID, quantitySold)) {
                System.out.println("Not enough books to sell");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Not valid input");
            return;
        }
        Book book = session.get(Book.class, bookID);
        Customer customer = session.get(Customer.class, customerID);
        if (book == null) {
            System.out.println("Invalid book ID");
            return;
        }
        if (customer == null) {
            System.out.println("Invalid customer ID");
            return;
        }
        Sale sale = new Sale(book, customer, Date.valueOf(LocalDate.now()), quantitySold, BigDecimal.ZERO);
        BigDecimal totalPrice = book.getPrice().multiply(BigDecimal.valueOf(quantitySold));
        sale.setTotalPrice(totalPrice);
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.persist(sale);
            session.flush();
            transaction.commit();
            System.out.println("Sale recorded successfully.");
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }





    // Total revenue for given genre of book
    private void totalRevenueByGenre(Session session) {
        System.out.print("Enter genre: ");
        String genre = scanner.nextLine();
        String hql = "SELECT COALESCE(SUM(s.totalPrice), 0) " +
                "FROM Sale s " +
                "WHERE s.book.genre = :genre";
        Query<BigDecimal> query = session.createQuery(hql, BigDecimal.class);
        query.setParameter("genre", genre);
        BigDecimal totalRevenue = query.uniqueResult();
        System.out.printf("%-20s %-10s\n", "Genre", "TotalRevenue");
        System.out.println("----------------------------------------");
        System.out.printf("%-20s %-10.2f\n", genre, totalRevenue);

    }

    // View Sale report of book (specified by book ID)
    private void bookSalesReport(Session session) {
        String hql = "SELECT s.saleID, b.title AS bookTitle, c.name AS customerName, " +
                "s.dateOfSale, s.quantitySold, s.totalPrice FROM Sale s " +
                "JOIN s.book b " +
                "JOIN s.customer c";

        Query<Object[]> query = session.createQuery(hql, Object[].class);
        List<Object[]> result = query.list();
        System.out.printf("%-10s %-30s %-25s %-15s %-15s %-15s\n",
                "SaleID", "Book Title", "Customer Name", "Date of Sale", "Quantity Sold", "Total Price");
        System.out.println("----------------------------------------------------------------------------------------------------------------");
        for (Object[] row : result) {
            Long saleID = (Long) row[0];
            String bookTitle = (String) row[1];
            String customerName = (String) row[2];
            java.util.Date dateOfSale = (java.util.Date) row[3];
            Integer quantitySold = (Integer) row[4];
            BigDecimal totalPrice = (BigDecimal) row[5];
            System.out.printf("%-10d %-30s %-25s %-15s %-15d %-15.2f\n",
                    saleID, bookTitle, customerName, dateOfSale, quantitySold, totalPrice.doubleValue());
        }
    }

    // total revenue for all genres in book store
    private void totalRevenueReportByGenres(Session session) {
        String hql = "SELECT b.genre, COALESCE(SUM(s.totalPrice), 0) AS totalRevenue " +
                "FROM Book b LEFT JOIN Sale s ON b.bookID = s.book.bookID " +
                "GROUP BY b.genre";

        Query<Object[]> query = session.createQuery(hql, Object[].class);
        List<Object[]> result = query.list();
        System.out.printf("%-20s %-10s\n", "Genre", "TotalRevenue");
        System.out.println("----------------------------------------");
        for (Object[] row : result) {
            String genre = (String) row[0];
            BigDecimal totalRevenue = (BigDecimal) row[1];
            System.out.printf("%-20s %-10.2f\n", genre, totalRevenue.doubleValue());
        }
    }

    // Show information about all books
    public void viewAllBooks(Session session) {
        String hql = "FROM Book";
        Query<Book> query = session.createQuery(hql, Book.class);
        List<Book> books = query.getResultList();
        if (books.isEmpty()) {
            System.out.println("No books found.");
        } else {
            listBooks(books);
        }

    }
    // Show information about given all customers
    public void viewAllCustomers(Session session) {
        String hql = "FROM Customer";
        Query<Customer> query = session.createQuery(hql, Customer.class);
        List<Customer> customers = query.getResultList();
        if(customers.isEmpty()) {
            System.out.println("No customers found");
        } else {
            listCustomers(customers);
        }
    }


    // Check whether there is enough quantity of books to buy
    private boolean hasEnoughQuantity(Session session, Long bookID, int quantity) {
        String hql = "SELECT b.quantityInStock FROM Book b WHERE b.bookID = :bookID";
        Query<Integer> query = session.createQuery(hql, Integer.class);
        query.setParameter("bookID", bookID);
        try {
            Integer quantityInStock = query.uniqueResult();
            return quantityInStock != null && quantityInStock >= quantity;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

}

