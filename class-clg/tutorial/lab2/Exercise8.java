public class Exercise8 {
    public static void main(String[] args) {
        double[] salaries = new double[100];
        for (int i = 0; i < salaries.length; i++) {
            salaries[i] = Math.random();
        }
        double sum = 0.0;
        for (double d : salaries) {
            sum += d;
        }
        System.out.println("Sum is"+ sum);
    }
}
