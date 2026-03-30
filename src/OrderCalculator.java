// 1. Separate Interfaces (ISP)
public interface OrderCalculator {
    void calculateTotal(double price, int quantity);
}