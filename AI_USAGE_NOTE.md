# AI Tool Usage Note

**Tool used:** Claude (Anthropic), used as a pair-programming assistant
throughout development.

**How it helped:** 
* I described the requirements (add items to cart,
calculate total, apply a discount, display final amount, built in Python) and
Claude generated an initial working version of the application, structured
around a `Cart` class, an `Item` model, and a discount-lookup function. From
there.
* I used Claude iteratively to harden the code: adding input validation
for edge cases (empty item names, negative prices, zero quantities, invalid
discount codes), preventing a fixed discount from pushing the total below
zero, and improving the CLI flow so invalid input prompts the user to retry
instead of crashing or silently failing. Claude also ran the code after each
change to verify behavior (e.g., testing that an invalid discount code is
rejected, that a $100 fixed discount on a $10 cart correctly caps at $0
instead of going negative) 
* At last,before the final version was finalized, which
speed up the verification cycle significantly compared to manually tracing
through test cases by hand.

**Challenges encountered:**
* Balancing strict input validation with a simple and user-friendly interface.
* Handling edge cases such as invalid discount codes and discounts exceeding the cart total (e.g., a $100 discount applied to a $10 cart), ensuring the final amount never becomes negative.
* Deciding whether invalid user inputs should be rejected immediately or allow users to retry, while maintaining a smooth CLI experience.
