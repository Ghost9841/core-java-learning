import java.util.Scanner;

public class Exercise5 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter an ASCII code (0-128): ");
        int ascii = scanner.nextInt();

        if (ascii >= 0 && ascii <= 128) {
            char character = (char) ascii;
            System.out.println("ASCII code " + ascii + " corresponds to character: '" + character + "'");
        } else {
            System.out.println("Error: Please enter a number between 0 and 128.");
        }
        scanner.close();
    }
}