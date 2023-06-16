package org.example;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class FoodItem {
    private String name;
    private double price;
    private String restaurant;

    public FoodItem(String name, double price, String restaurant) {
        this.name = name;
        this.price = price;
        this.restaurant = restaurant;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getRestaurant() {
        return restaurant;
    }
}

public class FoodOrderingApp {
    private static final String RESTAURANTS_FILE_PATH = "/Users/macbook/Documents/IJ Projects/shardaswiggycliApp/src/main/java/org/example/restaurants.csv";
    private static final String MENU_FILE_PATH = "/Users/macbook/Documents/IJ Projects/shardaswiggycliApp/src/main/java/org/example/menu.csv";
    private static final String ORDERS_FILE_PATH = "/Users/macbook/Documents/IJ Projects/shardaswiggycliApp/src/main/java/org/example/orders.csv";
    private List<FoodItem> menu;
    private List<String> restaurants;

    public FoodOrderingApp() {
        menu = new ArrayList<>();
        restaurants = new ArrayList<>();
    }

    public void loadRestaurants() {
        try (BufferedReader reader = new BufferedReader(new FileReader(RESTAURANTS_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String restaurant = line.trim();
                restaurants.add(restaurant);
            }
        } catch (IOException e) {
            System.out.println("Error loading restaurants: " + e.getMessage());
        }
    }

    public void loadMenu() {
        try (BufferedReader reader = new BufferedReader(new FileReader(MENU_FILE_PATH))) {
            String line;
            boolean skipHeader = true;
            while ((line = reader.readLine()) != null) {
                if (skipHeader) {
                    skipHeader = false;
                    continue;
                }
                String[] data = line.split(",");
                if (data.length < 3) {
                    System.out.println("Invalid data format for menu item: " + line);
                    continue;
                }
                String name = data[0];
                double price;
                try {
                    price = Double.parseDouble(data[1]);
                } catch (NumberFormatException e) {
                    System.out.println("Error parsing price for item: " + name);
                    continue;
                }
                String restaurant = data[2];
                FoodItem item = new FoodItem(name, price, restaurant);
                menu.add(item);
            }
        } catch (IOException e) {
            System.out.println("Error loading menu: " + e.getMessage());
        }
    }

    public void displayRestaurants() {
        System.out.println("Restaurants:");
        for (int i = 0; i < restaurants.size(); i++) {
            System.out.println((i + 1) + ". " + restaurants.get(i));
        }
        System.out.println();
    }

    public void displayMenu() {
        System.out.println("Menu:");
        for (int i = 0; i < menu.size(); i++) {
            FoodItem item = menu.get(i);
            System.out.println((i + 1) + ". " + item.getName() + " - $" + item.getPrice() + " - " + item.getRestaurant());
        }
        System.out.println();
    }

    public void selectRestaurant() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the restaurant number: ");
        int restaurantNumber = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        if (restaurantNumber >= 1 && restaurantNumber <= restaurants.size()) {
            String restaurant = restaurants.get(restaurantNumber - 1);
            List<FoodItem> filteredMenu = new ArrayList<>();
            for (FoodItem item : menu) {
                if (item.getRestaurant().equalsIgnoreCase(restaurant)) {
                    filteredMenu.add(item);
                }
            }

            if (filteredMenu.isEmpty()) {
                System.out.println("No items found for the selected restaurant.");
            } else {
                menu = filteredMenu;
                System.out.println("Selected restaurant: " + restaurant);
            }
        } else {
            System.out.println("Invalid restaurant number!");
        }
    }

    public void placeOrder() {
        // Implement the logic for placing an order
    }

    public static void main(String[] args) {
        FoodOrderingApp app = new FoodOrderingApp();
        app.loadRestaurants();
        app.loadMenu();
        app.displayRestaurants();
        app.selectRestaurant();
        app.displayMenu();
        app.placeOrder();
    }
}
