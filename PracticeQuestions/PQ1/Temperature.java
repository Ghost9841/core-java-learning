import java.util.Scanner;
public class Temperature {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("What is the current temperature");
        double temp = scan.nextDouble();
        if (temp > 30) {
            System.out.println("It is hot");
        } else if (temp <= 30 && temp >= 20 ) {
            System.out.println("It is warm");
        } else if (temp <= 19 && temp >= 10 ) {
            System.out.println("It is cool");
        } else {
            System.out.println("It is cold");

        }
    }
}
