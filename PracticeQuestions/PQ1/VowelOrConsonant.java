import java.util.Scanner;
public class VowelOrConsonant {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter a character to check it is vowel or consonants");
        char c = scan.next().charAt(0);
        if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u') {
            System.out.println("It is vowel");
        } else {
            System.out.println("It is consonant");

        }
    }
}
