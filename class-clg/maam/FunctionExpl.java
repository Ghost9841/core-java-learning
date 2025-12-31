public class FunctionExpl{
    public static void main(String[] args) {
        greet();
        sum();
}

    public static void greet() {
        System.out.println("Hello World");
    }
    public static void sum(){
        int a =10;
        int b=20;
        int sum = a + b;
        System.out.println("Sum" + sum);
    }
}