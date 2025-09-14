// Product Management System
import java.io.*;
import java.util.*;

// Product Class
class Product {

    // Attributes of product class
    public String name;
    public double price;
    public int quantity;

    // Constructor to initialize the attributes
    public Product(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    // Getter methods to access the attributes
    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    // Method to return a string representation
    @Override
    public String toString() {
        return "Name: " + name + ", Price: ₱" + price + ", Quantity: " + quantity;
    }
}

// Inventory Class
class Inventory {
    private static final String inventoryFile = "inventory.txt";

    // Method for writing in the inventory file
    public void updateFile (List<String> lines) {
        try (FileWriter fileWriter = new FileWriter(inventoryFile)) {
            for (String line : lines) {
                fileWriter.write(line + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error updating file: " + e);
        }
    }

    // Method to add a product to the inventory
    public void addProduct(Product products) {
        // true; so its data will be appended to the end of the inventory without removing existing data
        try (FileWriter writer = new FileWriter(inventoryFile, true)) {
            writer.write(products.getName() + "," + products.getPrice() + "," + products.getQuantity() + "\n");
            System.out.println("----------------------------------------");
            System.out.println("The product has been successfully added!");
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }

    // Method to update a product in the inventory
    public void updateProduct(int item, Product updatedProduct) {
        ArrayList<String> lines = new ArrayList<>();
        try (Scanner input = new Scanner(new File(inventoryFile))) {
            while (input.hasNextLine()) {
                lines.add(input.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error updating product: " + e);
            return;
        }

        if (item >= 0 && item < lines.size()) {
            lines.set(item, updatedProduct.getName() + "," + updatedProduct.getPrice() + "," + updatedProduct.getQuantity());
            updateFile(lines);
            System.out.println("The product has been successfully updated!");
        } else {
            System.out.println("Invalid product index.");
        }
    }

    // Method to delete a product in the inventory
    public void deleteProduct(int index) {
        ArrayList<String> lines = new ArrayList<>();
        try (Scanner input = new Scanner(new File(inventoryFile))) {
            while (input.hasNextLine()) {
                lines.add(input.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error deleting product: " + e.getMessage());
            return;
        }

        if (index >= 0 && index < lines.size()) {
            lines.remove(index);
            updateFile(lines);
            System.out.println("The product has been successfully deleted!");
        } else {
            System.out.println("Invalid product index.");
        }
    }

    // Method to display the products in the inventory
    public void displayProducts() {
        try (Scanner input = new Scanner(new File(inventoryFile))) {
            if (!input.hasNextLine()) {
                System.out.println("-------------------");
                System.out.println("Inventory is empty.");
            } else {
                System.out.println("=======================================================");
                System.out.println("Inventory:");
                int i = 1;
                while (input.hasNextLine()) {
                    String line = input.nextLine();
                    String[] parts = line.split(",");
                    System.out.println(i++ + ". Name: " + parts[0] + ", Price: " + parts[1] + " pesos" + ", Quantity: " + parts[2]);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: " + e);
        }
    }
}

// Main Class
public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Inventory inventory = new Inventory();

        // Loop for Menu
        while (true) {
            // Menu choices
            System.out.println("=============================================================================================");
            System.out.println("Welcome to the Product Management System! Please select the action you would like to perform!");
            System.out.println("=============================================================================================");
            System.out.println("A. Add a Product");
            System.out.println("B. update a Product");
            System.out.println("C. Delete a Product");
            System.out.println("D. View Products");
            System.out.println("E. Exit");
            System.out.println("---------------------------");
            System.out.print("Please input your selection: ");
            char selection = Character.toUpperCase(input.next().charAt(0));

            // Menu execution using switch statement
            switch (selection) {
                // Case A for adding products to the inventory
                case 'A':
                    System.out.println("=======================================================");
                    System.out.println("Adding a product. Please provide the following details.");
                    System.out.println("=======================================================");
                    System.out.print("Enter product name: ");
                    String name = input.next();
                    System.out.print("Enter product price: ");
                    double price = input.nextDouble();
                    System.out.print("Enter product quantity: ");
                    int quantity = input.nextInt();
                    inventory.addProduct(new Product(name, price, quantity));
                    break;

                // Case B for updating products in the inventory
                case 'B':
                    System.out.println("=========================================================");
                    System.out.println("Updating a product. Please provide the following details.");
                    inventory.displayProducts();
                    System.out.println("--------------------------------------------------------");
                    System.out.print("Please enter the index of the product you wish to update: ");
                    int indexUpdate = input.nextInt() - 1;
                    if (indexUpdate >= 0) {
                        System.out.print("Enter updated name: ");
                        String updatedName = input.next();
                        System.out.print("Enter updated price: ");
                        double updatedPrice = input.nextDouble();
                        System.out.print("Enter updated quantity: ");
                        int updatedQuantity = input.nextInt();
                        inventory.updateProduct(indexUpdate, new Product(updatedName, updatedPrice, updatedQuantity));
                    } else {
                        System.out.println("Invalid product index.");
                    }
                    break;

                // Case C for deleting products in the inventory
                case 'C':
                    System.out.println("=========================================================");
                    System.out.println("Deleting a product. Please provide the following details.");
                    inventory.displayProducts();
                    System.out.println("--------------------------------------------");
                    System.out.println("Please specify the item you want to delete: ");
                    int indexDelete = input.nextInt() - 1;
                    inventory.deleteProduct(indexDelete);
                    break;

                // Case D for viewing products in the inventory
                case 'D':
                    inventory.displayProducts();
                    break;

                // Case E for exiting the program
                case 'E':
                    System.out.println("Exiting program.");
                    System.exit(0);

                default:
                    System.out.println("Invalid option. Please enter letters A - E only.");
            }
        }
    }
}
