/**
 * Applies a flat (fixed amount) discount to the subtotal.
 * The discount is automatically capped so the final amount never goes negative.
 */
public class FlatDiscount implements Discount {

    private final double amount;

    public FlatDiscount(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Flat discount amount cannot be negative.");
        }
        this.amount = amount;
    }

    @Override
    public double calculateDiscount(double subtotal) {
        return Math.min(amount, subtotal);
    }

    @Override
    public String getDescription() {
        return String.format("Flat Rs.%.2f off", amount);
    }
}
