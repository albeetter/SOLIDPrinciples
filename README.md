# SOLID Principles Refactoring: Order Management System
## Problem Description
This project demonstrates the refactoring of a legacy Order Management System to adhere to SOLID Object-Oriented Design Principles.
In the initial implementation, the system relied on a single, monolithic interface (Order) and a single concrete class (OrderAction) to handle every step of processing an order. This included calculating totals, placing the order, generating invoice PDFs, and sending email notifications. The original code explicitly noted that certain steps—like generating an invoice or sending an email—were optional and might not be needed for all orders, yet the interface forced their implementation.

### SOLID Principles Violated
The original code violated several key SOLID principles:
#### Single Responsibility Principle (SRP): The OrderAction class had too many reasons to change. It was responsible for mathematical calculations, system placements, file generation, and email notifications.
#### Interface Segregation Principle (ISP): The Order interface was a "fat" interface. It forced any implementing class to provide logic for methods like generateInvoice and sendEmailNotification even if the specific order type did not require them.
#### Dependency Inversion Principle (DIP): The client (OrderTest) directly instantiated the concrete OrderAction class rather than relying on high-level abstractions to coordinate the workflow.
### How We Refactored the Code
To resolve these violations and create a highly cohesive, loosely coupled system, we applied the following structural changes:
#### Applied ISP (Interface Splitting): The monolithic Order interface was split into four distinct, highly-focused interfaces: PriceCalculator, OrderPlacer, InvoiceGenerator, and EmailNotifier.
#### Applied SRP (Class Splitting): The OrderAction class was broken down into four separate concrete classes. Each class now has only one responsibility.
#### Applied DIP & Orchestration (OrderManager): We introduced an OrderManager class to orchestrate the workflow. It uses Constructor Injection to accept the interfaces as dependencies. It also handles optional steps by checking for null dependencies, ensuring the system doesn't crash if an invoice or email isn't required.

### Code Comparison
#### Original Code (Violates SOLID)
```
public interface Order {

  void calculateTotal(double price, int quantity);

  void placeOrder(String customerName, String address);

  void generateInvoice(String fileName);

  void sendEmailNotification(String email);
}

public class OrderAction implements Order {

  @Override
  public void calculateTotal(double price, int quantity) {
    double total = price * quantity;
    System.out.println("Order total: $" + total);
  }

  @Override
  public void placeOrder(String customerName, String address) {
    // Simulate placing order in a system
    System.out.println("Order placed for " + customerName + " at " + address);
  }

  @Override
  public void generateInvoice(String fileName) {
    // Simulate generating invoice file
    System.out.println("Invoice generated: " + fileName);
  }

  @Override
  public void sendEmailNotification(String email) {
    // Simulate sending email notification
    System.out.println("Email notification sent to: " + email);
  }
}

public class OrderTest {

  public static void main(String[] args) {
    Order order = new OrderAction();
    order.calculateTotal(10.0, 2);
    order.placeOrder("John Doe", "123 Main St");

    // These methods might not be needed for all orders
    order.generateInvoice("order_123.pdf");
    order.sendEmailNotification("johndoe@example.com");
  }
}
```
#### Refactored Code (Adheres to SOLID)
```
// 1. Separate Interfaces (ISP)
public interface OrderCalculator {
    void calculateTotal(double price, int quantity);
}

public interface OrderPlacer {
    void placeOrder(String customerName, String address);
}

public interface InvoiceGenerator {
    void generateInvoice(String fileName);
}

public interface NotificationService {
    void sendEmailNotification(String email);
}

// 2. Specialized Implementations (SRP)
public class OrderManager implements OrderCalculator, OrderPlacer {
    @Override
    public void calculateTotal(double price, int quantity) {
        double total = price * quantity;
        System.out.println("Order total: $" + total);
    }

    @Override
    public void placeOrder(String customerName, String address) {
        System.out.println("Order placed for " + customerName + " at " + address);
    }
}

public class InvoiceService implements InvoiceGenerator {
    @Override
    public void generateInvoice(String fileName) {
        System.out.println("Invoice generated: " + fileName);
    }
}

public class EmailService implements NotificationService {
    @Override
    public void sendEmailNotification(String email) {
        System.out.println("Email notification sent to: " + email);
    }
}

public class OrderTest {
    public static void main(String[] args) {
        OrderManager order = new OrderManager();
        InvoiceService invoice = new InvoiceService();
        EmailService email = new EmailService();

        order.calculateTotal(10.0, 2);
        order.placeOrder("John Doe", "123 Main St");
        
        // Now these are separate, optional concerns
        invoice.generateInvoice("order_123.pdf");
        email.sendEmailNotification("johndoe@example.com");
    }
}
```

### UML Diagram
<img src="https://github.com/albeetter/SOLIDPrinciples/blob/master/ClassDiagramSOLIDPrinciple.png" alt="classdiagram">




