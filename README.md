# Simple Cart and Discount System

A command-line application built in Java that lets a user add items to a shopping cart, calculate the subtotal, apply a discount (percentage, flat amount, or coupon code), and view the final payable amount.

## Technology Stack

- **Language:** Java 21 (uses modern `switch` expressions)
- **Build/Run:** Plain `javac` / `java` — no external dependencies, no build tool (Maven/Gradle) required
- **Interface:** Interactive command-line menu (`java.util.Scanner` for input)

## Project Structure

```
cart-discount-system/
├── src/
│   ├── Item.java              # Represents a single cart line item (name, price, quantity)
│   ├── Cart.java               # Manages cart items, subtotal, and discount application
│   ├── Discount.java            # Strategy interface for discount types
│   ├── PercentageDiscount.java  # e.g. 10% off
│   ├── FlatDiscount.java        # e.g. flat Rs.100 off
│   ├── CouponDiscount.java      # Predefined coupon codes (SAVE10, SAVE20, WELCOME50)
│   └── CartApplication.java     # Main entry point / CLI menu loop
└── README.md
```

## Setup and Run Instructions

### Prerequisites
- Java JDK 17 or higher installed (`java -version` and `javac -version` to confirm)

### Steps

1. Clone the repository:
   ```
   git clone <repository-url>
   cd cart-discount-system
   ```

2. Compile the source files:
   ```
   cd src
   javac *.java
   ```

3. Run the application:
   ```
   java CartApplication
   ```

4. Follow the on-screen menu to add items, view the cart, apply a discount, and view the final amount.

### Example Session

```
1. Add item to cart
2. Remove item from cart
3. View cart
4. Apply discount
5. View final amount
6. Clear cart
0. Exit
Enter your choice: 1
Enter item name: Apple
Enter item price (Rs.): 50
Enter quantity: 3
Added: Apple x3 @ Rs.50.00
```

## Features

- **Add items** to the cart (adding an item with the same name again increases its quantity rather than duplicating the line)
- **Remove items** from the cart by name
- **View cart** with a line-by-line breakdown and subtotal
- **Apply a discount**, with three supported types:
  - Percentage discount (e.g. 10% off)
  - Flat amount discount (e.g. Rs.100 off, automatically capped so the total can't go negative)
  - Coupon code (`SAVE10`, `SAVE20`, `WELCOME50` — each with its own percentage and minimum cart value requirement)
- **View final amount**, showing subtotal, discount applied, and final payable total
- **Clear cart** to start over
- Input validation at every step (empty names, negative prices, zero/negative quantities, non-numeric input, invalid menu choices, invalid coupon codes, coupons used below their minimum spend)

## Assumptions Made

- Currency is displayed as Indian Rupees (Rs.) — this can be trivially changed in the format strings if a different currency is needed.
- Only one discount can be active on the cart at a time; applying a new discount replaces the previous one.
- A flat discount that's larger than the subtotal will simply cap the final amount at Rs.0 rather than going negative or throwing an error.
- Coupon codes are hardcoded (`SAVE10`, `SAVE20`, `WELCOME50`) for demonstration purposes rather than pulled from a database, since the assignment doesn't call for persistence.
- The cart is in-memory only and resets each time the program is run — no data persistence (file/database) was implemented, since the assignment didn't specify this requirement.
- Adding an item that already exists in the cart (matched case-insensitively by name) increases its quantity rather than creating a duplicate line, which matches typical real-world cart behavior.
- The application is single-user and single-session (no login, no concurrent users).

## AI Usage Note

See [AI_USAGE_NOTE.md](AI_USAGE_NOTE.md) for details on which AI tools were used during development and how.
