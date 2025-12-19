package maam;
import java.util.Scanner;
public class MenuPrice {
    public static void main(String[] args) {
        String[] item = {"Burger", "Pizza", "Pasta", "Salad"};
        double price;
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the item name:");
        String choice = sc.nextLine();
        switch (choice) {
            case "Burger":
                price = 5.99;
                System.out.println("The price of Burger is $" + price);
                break;
            case "Pizza":
                price = 8.99;
                System.out.println("The price of Pizza is $" + price);
                break;
            case "Pasta":
                price = 7.49;
                System.out.println("The price of Pasta is $" + price);
                break;
            case "Salad":
                price = 4.99;
                System.out.println("The price of Salad is $" + price);
                break;
            default:
                System.out.println("Item not found in the menu.");
        }
    }
}
