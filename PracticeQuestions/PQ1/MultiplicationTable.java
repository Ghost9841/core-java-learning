import java.util.Scanner;

public class MultiplicationTable {
    public static void main(String[] args){
        Scanner scan  = new Scanner(System.in);
        System.out.println("Please give a number to get multiplication table");
        int number = scan.nextInt();
        for(int i = 1; i <= 10; i++){
            int fact = 1;
            fact = number * i;
            System.out.println(number +"*"+ i + " = " + fact);
        }
    }
}
