import java.util.Scanner;

public class InputInteger {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int num1 = scan.nextInt();
        int num2 = scan.nextInt();
        boolean result = false;
        
        if (num1 == 6 || num2 == 6) {
            result = true;
        } else if (num1 + num2 == 6){
            result = true;
        } else if (Math.abs(num1 - num2) == 6){
            result = true;
        }
        
        System.out.println(result);
    }
}