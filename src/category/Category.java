package category;

import product.Product;
import java.util.ArrayList;
import java.util.List;

public class Category {
    private String id;
    private String name;
    private String description;
    private List<Product> products;

    public Category(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.products = new ArrayList<>();
    }

    public void addProduct(Product product) {
        if (product != null) {
            products.add(product);
        }
    }

    public void removeProduct(Product product) {
        products.remove(product);
    }

    public double getTotalValue() {
        double totalValue = 0;
        for (Product product : products) {
            totalValue += product.calculateTotalValue();
        }
        return totalValue;
    }

    public void displayCategoryInfo() {
        System.out.println("Category ID: " + id);
        System.out.println("Category Name: " + name);
        System.out.println("Description: " + description);
        System.out.println("--- Products in this Category ---");
        for (Product product : products) {
            product.displayProductInfo();
            System.out.println("--------------------");
        }
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<Product> getProducts() {
        return new ArrayList<>(products);
    }
}
