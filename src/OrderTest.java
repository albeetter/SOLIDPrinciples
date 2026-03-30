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