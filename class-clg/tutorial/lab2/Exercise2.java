import java.util.Scanner;

public class Exercise2 {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter Radius ");
        
        double radius = scan.nextDouble();
        double area = radius* radius * Math.PI;
        System.out.println("Area = " + area);
        
        System.out.println("Enter Length ");
        double length = scan.nextDouble();
        double volume = area * length;
        System.out.println("Volume = " + volume);


    }
}
