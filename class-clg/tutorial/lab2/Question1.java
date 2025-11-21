import java.util.Scanner;
public class Question1 {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter number in foot: ");
        double inFeet = scan.nextDouble();
        double inMeter = inFeet * 0.305;
        
        
        System.out.println(inFeet + " Foot is equal to " + inMeter + " meters");

    }
}
