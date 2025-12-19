package maam;
import java.util.Scanner;


public class NumberGuessing {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int numberToGuess = (int)(Math.random() * 100) + 1; 
        int numberOfTries = 0;
        int guess = 0;
        System.out.println("Welcome to the Number Guessing Game!");
        System.out.println("I have selected a number between 1 and 100. Can you guess it?");
        while (guess != numberToGuess) {
            System.out.print("Enter your guess: ");
            guess = sc.nextInt();   
        }    
    }
}
