/**
 * Represents a single item that can be added to the cart.
 * Encapsulates name, unit price, and quantity, with validation
 * to ensure the item always remains in a valid state.
 */
public class Item {

    private final String name;
    private final double price;
    private int quantity;

    public Item(String name, double price, int quantity) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Item name cannot be empty.");
        }
        if (price < 0) {
            throw new IllegalArgumentException("Item price cannot be negative.");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Item quantity must be greater than zero.");
        }
        this.name = name.trim();
        this.price = price;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void increaseQuantity(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount to increase must be positive.");
        }
        this.quantity += amount;
    }

    /**
     * Total cost contributed by this line item (price * quantity).
     */
    public double getLineTotal() {
        return price * quantity;
    }

    @Override
    public String toString() {
        return String.format("%-15s x%-3d @ Rs.%-8.2f = Rs.%.2f", name, quantity, price, getLineTotal());
    }
}
