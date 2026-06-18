import java.util.Map;
import java.util.HashMap;

/**
 * Applies a discount based on a predefined coupon code.
 * Demonstrates a slightly more realistic e-commerce discount flow,
 * where codes map to percentage discounts and may have a minimum
 * cart value requirement.
 */
public class CouponDiscount implements Discount {

    private static final Map<String, Double> COUPON_PERCENTAGES = new HashMap<>();
    private static final Map<String, Double> COUPON_MIN_SPEND = new HashMap<>();

    static {
        COUPON_PERCENTAGES.put("SAVE10", 10.0);
        COUPON_PERCENTAGES.put("SAVE20", 20.0);
        COUPON_PERCENTAGES.put("WELCOME50", 50.0);

        COUPON_MIN_SPEND.put("SAVE10", 0.0);
        COUPON_MIN_SPEND.put("SAVE20", 500.0);
        COUPON_MIN_SPEND.put("WELCOME50", 1000.0);
    }

    private final String code;

    public CouponDiscount(String code) {
        if (code == null || code.trim().isEmpty()) {
            throw new IllegalArgumentException("Coupon code cannot be empty.");
        }
        String normalized = code.trim().toUpperCase();
        if (!COUPON_PERCENTAGES.containsKey(normalized)) {
            throw new IllegalArgumentException("Invalid coupon code: " + code);
        }
        this.code = normalized;
    }

    @Override
    public double calculateDiscount(double subtotal) {
        double minSpend = COUPON_MIN_SPEND.get(code);
        if (subtotal < minSpend) {
            throw new IllegalStateException(
                String.format("Coupon '%s' requires a minimum cart value of Rs.%.2f", code, minSpend)
            );
        }
        double percentage = COUPON_PERCENTAGES.get(code);
        return subtotal * (percentage / 100.0);
    }

    @Override
    public String getDescription() {
        return String.format("Coupon '%s' (%.0f%% off)", code, COUPON_PERCENTAGES.get(code));
    }

    public static boolean isValidCode(String code) {
        return code != null && COUPON_PERCENTAGES.containsKey(code.trim().toUpperCase());
    }

    public static String getAvailableCodes() {
        StringBuilder sb = new StringBuilder();
        for (String c : COUPON_PERCENTAGES.keySet()) {
            sb.append(String.format("%-12s -> %.0f%% off (min spend Rs.%.2f)%n",
                c, COUPON_PERCENTAGES.get(c), COUPON_MIN_SPEND.get(c)));
        }
        return sb.toString();
    }
}
