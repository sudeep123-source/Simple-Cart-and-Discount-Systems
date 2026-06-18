/**
 * Applies a percentage-based discount (e.g. 10% off) to the subtotal.
 */
public class PercentageDiscount implements Discount {

    private final double percentage;

    public PercentageDiscount(double percentage) {
        if (percentage < 0 || percentage > 100) {
            throw new IllegalArgumentException("Percentage discount must be between 0 and 100.");
        }
        this.percentage = percentage;
    }

    @Override
    public double calculateDiscount(double subtotal) {
        return subtotal * (percentage / 100.0);
    }

    @Override
    public String getDescription() {
        return String.format("%.1f%% off", percentage);
    }
}
