import java.util.Scanner;

public class Exercise7 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter marks (0 to 100): ");
        int marks = sc.nextInt();

        String grade;
        String description;

        if (marks < 0 || marks > 100) {
            grade = "Invalid";
            description = "Marks out of range";
        } 
        else if (marks <= 40) {
            grade = "F";
            description = "Fail";
        } 
        else if (marks <= 49) {
            grade = "F+";
            description = "Marginal Fail";
        } 
        else if (marks <= 54) {
            grade = "D";
            description = "Pass";
        } 
        else if (marks <= 64) {
            grade = "C";
            description = "";
        } 
        else if (marks <= 69) {
            grade = "B";
            description = "Credit";
        } 
        else if (marks <= 74) {
            grade = "B+";
            description = "";
        } 
        else if (marks <= 79) {
            grade = "A";
            description = "Distinction";
        } 
        else {  // 80–100
            grade = "A+";
            description = "Distinction";
        }

        System.out.println("Grade: " + grade);
        System.out.println("Description: " + description);
    }
}
