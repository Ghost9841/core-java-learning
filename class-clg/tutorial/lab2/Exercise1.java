import java.util.Scanner;
public class Exercise1 {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter Fahrenheit ");
        double Fahrenheit = scan.nextDouble();
        double celsius = (Fahrenheit - 32)* 5/9;
        
        System.out.println("Celsius of " + Fahrenheit  +"fahrenheit" + " is " + celsius + " celsius");
    }    
}
