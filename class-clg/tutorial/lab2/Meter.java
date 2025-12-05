import java.util.Scanner;
public class Meter {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter length in feet: ");
        double feet = scanner.nextDouble();
        double meters = feet * 0.305;
        System.out.printf(feet + " feet is " + meters + " meters%n");
        scanner.close();
    }
}
