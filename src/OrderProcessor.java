public class OrderProcessor {
    private final OrderCalculator orderCalculator;
    private final OrderPlacer orderPlacer;
    private final InvoiceGenerator invoiceGenerator;
    private final NotificationService notificationService;
 
    public OrderProcessor(
            OrderCalculator orderCalculator,
            OrderPlacer orderPlacer,
            InvoiceGenerator invoiceGenerator,
            NotificationService notificationService) {
        this.orderCalculator = orderCalculator;
        this.orderPlacer = orderPlacer;
        this.invoiceGenerator = invoiceGenerator;
        this.notificationService = notificationService;
    }
 
    public void process(double price, int quantity, String customerName,
                        String address, String invoiceFileName, String contactEmail) {
        orderCalculator.calculateTotal(price, quantity);
        orderPlacer.placeOrder(customerName, address);
        invoiceGenerator.generateInvoice(invoiceFileName);
        notificationService.sendEmailNotification(contactEmail);
    }
}