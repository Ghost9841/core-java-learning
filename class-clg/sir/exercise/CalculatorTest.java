public class CalculatorTest {
    public static void main(String[] args) {
        Calculator cal = new Calculator();
        int a=cal.add(1, 22);
        int b=cal.add(1, 2,3);
        double c=cal.add(1.0, 2);
        System.out.println("LOL " + a + "Lol2 " +  b + "Lol3 " + c );
    }
}
