import java.util.Scanner;

public class Exercise4 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter a uppercase letter;");
        String letter1 = sc.nextLine();
        String letter2 = letter1.toLowerCase();
        System.out.println("Lowecase:" + letter2);
    }
}
