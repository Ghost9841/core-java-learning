import java.util.Scanner;
public class DemoLogin {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String pre_username = "ghostaakash";
        String pre_password = "sad@123";

        System.out.println("Enter username: ");
        String current_username = scan.nextLine();
        System.out.println("Enter password: ");
        String current_password = scan.nextLine();
        if (current_username.equals(pre_username) && current_password.equals(pre_password)) {
            System.out.println("Login successful");
        } else {
            System.out.println("Login failed");
        }
    }
}
