package product;

import category.Category;

public class ShopDemo {
    public static void main(String[] args) {
        // Create a category
        Category electronics = new Category("ELEC-1", "Electronics", "Devices and gadgets");

        // Create a few products
        Product laptop = new Product("LP-001", "Laptop", "A powerful laptop", 1200.50, 15, electronics);
        Product phone = new Product("PH-001", "Smartphone", "A smart mobile phone", 800.00, 25, electronics);
        Product headphones = new Product("HP-001", "Headphones", "Noise-cancelling headphones", 150.75, 50, electronics);

        // Add products to the category
        electronics.addProduct(laptop);
        electronics.addProduct(phone);
        electronics.addProduct(headphones);

        System.out.println("--- Initial Shop State ---");
        electronics.displayCategoryInfo();
        System.out.println("========================================\n");

        // --- Demonstrate Product operations ---

        // 1. Sell some items
        System.out.println("--- Selling 5 laptops ---");
        laptop.sellProduct(5);
        laptop.displayProductInfo();
        System.out.println("----------------------------------------\n");

        // 2. Add stock
        System.out.println("--- Adding 10 phones to stock ---");
        phone.addStock(10);
        phone.displayProductInfo();
        System.out.println("----------------------------------------\n");

        // 3. Apply a discount
        System.out.println("--- Applying 10% discount to headphones ---");
        headphones.applyDiscount(10);
        headphones.displayProductInfo();
        System.out.println("----------------------------------------\n");


        // --- Display final category state ---
        System.out.println("--- Final Shop State ---");
        electronics.displayCategoryInfo();
        System.out.println("\nTotal Value of all products in " + electronics.getName() + " category: " + String.format("%.2f", electronics.getTotalValue()));
        System.out.println("=======================================");
    }
}
