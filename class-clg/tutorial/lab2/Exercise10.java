import java.util.Scanner;
public class Exercise10 {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String prompt;
        do {
            System.out.println("You are in loop");
            System.out.println("Do you want to keep looping? Say yes say yes else say no");
            prompt = scan.nextLine();
        } while (prompt.equals("yes"));
    }
}
