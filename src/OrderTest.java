public class OrderTest {
    public static void main(String[] args) {
 
        // Concrete implementations created once at the composition root
        OrderCalculator calculator = new OrderManager();
        OrderPlacer placer = new OrderManager();
        InvoiceGenerator invoice = new InvoiceService();
        NotificationService notification = new EmailService();
 
        // OrderProcessor only knows about interfaces — not concrete classes
        OrderProcessor processor = new OrderProcessor(calculator, placer, invoice, notification);
 
        processor.process(
            10.0,
            2,
            "John Doe",
            "123 Main St",
            "order_123.pdf",
            "johndoe@example.com"
        );
    }
}
 