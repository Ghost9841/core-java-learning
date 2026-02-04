import java.util.Scanner;
public class Scores {
    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter your name");
        String name = scan.nextLine();
        System.out.println("Enter your score between 0 & 100");
        double score = scan.nextDouble();
        if (score >= 90 && score <= 100) {
            System.out.println("A");
        } else if(score >= 80 && score <= 89) {
            System.out.println("B");  
        } else if(score >= 70 && score <= 79) {
            System.out.println("C");  
        } else if(score >= 60 && score <= 69) {
            System.out.println("D");  
        } else {
            System.out.println("F");
        }

    }
}