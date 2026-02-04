import java.util.Scanner;
public class LargestNum {
    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        System.out.println("A =");
        int a = scan.nextInt();
        System.out.println("B =");
        int b = scan.nextInt();
        System.out.println("C =");
        int c = scan.nextInt();
        if (a > b && a > c) {
            System.out.println("A is largest");
        } else if (b > c) {
            System.out.println("B is largest");
        } else {
            System.out.println("C is largest");
        }
    }
}
