import java.util.ArrayList;
import java.util.List;

/**
 * Represents a shopping cart. Manages the collection of items,
 * computes subtotal/final amount, and tracks an applied discount.
 */
public class Cart {

    private final List<Item> items;
    private Discount appliedDiscount;

    public Cart() {
        this.items = new ArrayList<>();
        this.appliedDiscount = null;
    }

    /**
     * Adds an item to the cart. If an item with the same name already
     * exists, its quantity is increased instead of creating a duplicate line.
     */
    public void addItem(String name, double price, int quantity) {
        for (Item existing : items) {
            if (existing.getName().equalsIgnoreCase(name.trim())) {
                existing.increaseQuantity(quantity);
                return;
            }
        }
        items.add(new Item(name, price, quantity));
    }

    /**
     * Removes an item from the cart by name.
     *
     * @return true if an item was found and removed, false otherwise
     */
    public boolean removeItem(String name) {
        return items.removeIf(item -> item.getName().equalsIgnoreCase(name.trim()));
    }

    public List<Item> getItems() {
        return items;
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    /**
     * Subtotal of all items in the cart before any discount.
     */
    public double calculateSubtotal() {
        double subtotal = 0.0;
        for (Item item : items) {
            subtotal += item.getLineTotal();
        }
        return subtotal;
    }

    public void applyDiscount(Discount discount) {
        if (isEmpty()) {
            throw new IllegalStateException("Cannot apply a discount to an empty cart.");
        }
        // Validate eagerly (e.g. coupon minimum-spend rules) so invalid
        // discounts are rejected immediately rather than silently accepted
        // and only failing later when the final amount is calculated.
        discount.calculateDiscount(calculateSubtotal());
        this.appliedDiscount = discount;
    }

    public void clearDiscount() {
        this.appliedDiscount = null;
    }

    public Discount getAppliedDiscount() {
        return appliedDiscount;
    }

    public double getDiscountAmount() {
        if (appliedDiscount == null) {
            return 0.0;
        }
        return appliedDiscount.calculateDiscount(calculateSubtotal());
    }

    /**
     * Final payable amount after subtracting the discount from the subtotal.
     * Guaranteed to never be negative.
     */
    public double calculateFinalAmount() {
        double subtotal = calculateSubtotal();
        double discount = getDiscountAmount();
        double finalAmount = subtotal - discount;
        return Math.max(finalAmount, 0.0);
    }

    public void clear() {
        items.clear();
        appliedDiscount = null;
    }
}
