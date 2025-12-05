public class Exercise9 {
    public static void main(String[] args) {
        double tuition = 10000;
        double rate = 0.05;

        for (int year = 1; year <= 10; year++) {
            tuition = tuition * (1 + rate);
            System.out.printf("Year %d: Tuition = %.2f%n", year, tuition);
        }
    }
}
