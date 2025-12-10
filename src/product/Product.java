package product;

import category.Category;

public class Product {
    private String id;
    private String name;
    private String description;
    private double price;
    private int quantity;
    private Category category;
    // stock status is derived from quantity, not a field

    public Product(String id, String name, String description, double price, int quantity, Category category) {
        this.id = id;
        this.name = name;
        this.description = description;
        if (price >= 0) {
            this.price = price;
        } else {
            this.price = 0;
            System.out.println("Price cannot be negative. Set to 0.");
        }
        if (quantity >= 0) {
            this.quantity = quantity;
        } else {
            this.quantity = 0;
            System.out.println("Quantity cannot be negative. Set to 0.");
        }
        this.category = category;
    }

    public void addStock(int amount) {
        if (amount > 0) {
            this.quantity += amount;
            System.out.println(amount + " items added to stock. New quantity: " + this.quantity);
        } else {
            System.out.println("Cannot add a non-positive amount to stock.");
        }
    }

    public void sellProduct(int amount) {
        if (amount > 0 && amount <= this.quantity) {
            this.quantity -= amount;
            System.out.println(amount + " items sold. New quantity: " + this.quantity);
        } else {
            System.out.println("Sale could not be completed. Check amount or stock level.");
        }
    }

    public void applyDiscount(double percent) {
        if (percent > 0 && percent <= 100) {
            this.price -= this.price * (percent / 100.0);
            System.out.println("Applied " + percent + "% discount. New price: " + this.price);
        } else {
            System.out.println("Invalid discount percentage.");
        }
    }

    public double calculateTotalValue() {
        return this.price * this.quantity;
    }

    public String getStockStatus() {
        if (quantity == 0) {
            return "OUT_OF_STOCK";
        } else if (quantity > 0 && quantity <= 10) {
            return "LOW";
        } else {
            return "IN_STOCK";
        }
    }

    public void displayProductInfo() {
        System.out.println("Product ID: " + id);
        System.out.println("Name: " + name);
        System.out.println("Description: " + description);
        System.out.println("Price: " + String.format("%.2f", price));
        System.out.println("Quantity: " + quantity);
        if (category != null) {
            System.out.println("Category: " + category.getName());
        } else {
            System.out.println("Category: Not assigned");
        }
        System.out.println("Stock Status: " + getStockStatus());
        System.out.println("Total Value: " + String.format("%.2f", calculateTotalValue()));
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public Category getCategory() {
        return category;
    }
}
