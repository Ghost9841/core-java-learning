import java.util.Scanner;

public class ArrayEvenInput {
    public static void main(String[] args) {
        int[] numbers = new int[5];
        int count = 0;
        Scanner scan = new Scanner(System.in);
        for (int i = 0; i < 5; i++) {
            System.out.println("Please enter your number of index " + i + " :");
            int temp = scan.nextInt();
            numbers[i] = temp;
            if (numbers[i]%2 == 0) {
                count++;
            }
        }
        System.out.println("There are " + count + " evens in the array u gave");
    }
}
