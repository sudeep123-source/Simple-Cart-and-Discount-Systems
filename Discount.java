/**
 * Strategy interface for applying different kinds of discounts
 * to a cart's subtotal. New discount types can be added without
 * modifying existing code (Open/Closed Principle).
 */
public interface Discount {

    /**
     * Calculates the discount amount to subtract from the given subtotal.
     *
     * @param subtotal the cart subtotal before discount
     * @return the discount amount (never more than the subtotal)
     */
    double calculateDiscount(double subtotal);

    /**
     * A short human-readable description of the discount, used for display.
     */
    String getDescription();
}
