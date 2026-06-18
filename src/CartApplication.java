import java.util.Scanner;
import java.util.InputMismatchException;

/**
 * Entry point for the Simple Cart and Discount System.
 * Provides an interactive, menu-driven command-line interface that lets
 * the user add items, view the cart, apply a discount, and see the
 * final payable amount.
 */
public class CartApplication {

    private final Cart cart;
    private final Scanner scanner;

    public CartApplication() {
        this.cart = new Cart();
        this.scanner = new Scanner(System.in);
    }

    public static void main(String[] args) {
        CartApplication app = new CartApplication();
        app.run();
    }

    public void run() {
        printWelcome();
        boolean running = true;

        while (running) {
            printMenu();
            int choice = readMenuChoice();

            switch (choice) {
                case 1 -> handleAddItem();
                case 2 -> handleRemoveItem();
                case 3 -> handleViewCart();
                case 4 -> handleApplyDiscount();
                case 5 -> handleViewFinalAmount();
                case 6 -> handleClearCart();
                case 0 -> {
                    System.out.println("\nThank you for shopping with us. Goodbye!");
                    running = false;
                }
                default -> System.out.println("Invalid option. Please choose a number from the menu.");
            }
        }

        scanner.close();
    }

    private void printWelcome() {
        System.out.println("==========================================");
        System.out.println("   SIMPLE CART AND DISCOUNT SYSTEM");
        System.out.println("==========================================");
    }

    private void printMenu() {
        System.out.println("\n------------------------------------------");
        System.out.println("1. Add item to cart");
        System.out.println("2. Remove item from cart");
        System.out.println("3. View cart");
        System.out.println("4. Apply discount");
        System.out.println("5. View final amount");
        System.out.println("6. Clear cart");
        System.out.println("0. Exit");
        System.out.println("------------------------------------------");
        System.out.print("Enter your choice: ");
    }

    private int readMenuChoice() {
        try {
            int choice = Integer.parseInt(scanner.nextLine().trim());
            return choice;
        } catch (NumberFormatException e) {
            return -1; // triggers "invalid option" in the switch default case
        }
    }

    private void handleAddItem() {
        try {
            System.out.print("Enter item name: ");
            String name = scanner.nextLine().trim();
            if (name.isEmpty()) {
                System.out.println("Error: Item name cannot be empty.");
                return;
            }

            double price = readDouble("Enter item price (Rs.): ");
            if (price < 0) {
                System.out.println("Error: Price cannot be negative.");
                return;
            }

            int quantity = readInt("Enter quantity: ");
            if (quantity <= 0) {
                System.out.println("Error: Quantity must be greater than zero.");
                return;
            }

            cart.addItem(name, price, quantity);
            System.out.printf("Added: %s x%d @ Rs.%.2f%n", name, quantity, price);

        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void handleRemoveItem() {
        if (cart.isEmpty()) {
            System.out.println("Your cart is already empty.");
            return;
        }
        System.out.print("Enter item name to remove: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("Error: Item name cannot be empty.");
            return;
        }
        boolean removed = cart.removeItem(name);
        if (removed) {
            System.out.println("Removed '" + name + "' from cart.");
        } else {
            System.out.println("Item '" + name + "' not found in cart.");
        }
    }

    private void handleViewCart() {
        if (cart.isEmpty()) {
            System.out.println("Your cart is empty.");
            return;
        }
        System.out.println("\n--- YOUR CART ---");
        for (Item item : cart.getItems()) {
            System.out.println(item);
        }
        System.out.printf("Subtotal: Rs.%.2f%n", cart.calculateSubtotal());

        if (cart.getAppliedDiscount() != null) {
            System.out.println("Applied discount: " + cart.getAppliedDiscount().getDescription());
        }
    }

    private void handleApplyDiscount() {
        if (cart.isEmpty()) {
            System.out.println("Error: Cannot apply a discount to an empty cart. Add items first.");
            return;
        }

        System.out.println("\nChoose discount type:");
        System.out.println("1. Percentage discount (e.g. 10% off)");
        System.out.println("2. Flat amount discount (e.g. Rs.100 off)");
        System.out.println("3. Coupon code");
        System.out.println("4. Cancel");
        System.out.print("Enter choice: ");

        int choice = readMenuChoice();

        try {
            switch (choice) {
                case 1 -> {
                    double percent = readDouble("Enter discount percentage (0-100): ");
                    cart.applyDiscount(new PercentageDiscount(percent));
                    System.out.println("Applied: " + cart.getAppliedDiscount().getDescription());
                }
                case 2 -> {
                    double amount = readDouble("Enter flat discount amount (Rs.): ");
                    cart.applyDiscount(new FlatDiscount(amount));
                    System.out.println("Applied: " + cart.getAppliedDiscount().getDescription());
                }
                case 3 -> {
                    System.out.println("Available coupon codes:");
                    System.out.print(CouponDiscount.getAvailableCodes());
                    System.out.print("Enter coupon code: ");
                    String code = scanner.nextLine().trim();
                    cart.applyDiscount(new CouponDiscount(code));
                    System.out.println("Applied: " + cart.getAppliedDiscount().getDescription());
                }
                case 4 -> System.out.println("Discount cancelled.");
                default -> System.out.println("Invalid option. No discount applied.");
            }
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void handleViewFinalAmount() {
        if (cart.isEmpty()) {
            System.out.println("Your cart is empty. Add items to see the final amount.");
            return;
        }

        double subtotal = cart.calculateSubtotal();
        double discount = cart.getDiscountAmount();
        double finalAmount = cart.calculateFinalAmount();

        System.out.println("\n--- ORDER SUMMARY ---");
        System.out.printf("Subtotal:        Rs.%.2f%n", subtotal);
        if (cart.getAppliedDiscount() != null) {
            System.out.printf("Discount (%s): -Rs.%.2f%n", cart.getAppliedDiscount().getDescription(), discount);
        } else {
            System.out.println("Discount:        None applied");
        }
        System.out.printf("Final Amount:    Rs.%.2f%n", finalAmount);
    }

    private void handleClearCart() {
        cart.clear();
        System.out.println("Cart has been cleared.");
    }

    /**
     * Reads a double from input, repeatedly prompting until valid numeric input is given.
     */
    private double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Please enter a valid numeric value.");
            }
        }
    }

    /**
     * Reads an integer from input, repeatedly prompting until valid numeric input is given.
     */
    private int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Please enter a whole number.");
            }
        }
    }
}
