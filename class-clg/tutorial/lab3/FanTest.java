public class FanTest {
    public static void main(String[] args) {
        Fan fan1 = new Fan();
        fan1.setSpeed(Fan.FAST);
        fan1.setRadius(10);
        fan1.setColor("Yellow");
        fan1.setOn(true);

        Fan fan2 = new Fan();
        fan2.setSpeed(Fan.MEDIUM);
        fan2.setRadius(5);
        fan2.setColor("Blue");
        fan2.setOn(false);

        System.out.println("Fan 1:");
        System.out.println("Speed: " + fan1.getSpeed());
        System.out.println("Radius: " + fan1.getRadius());
        System.out.println("Color: " + fan1.getColor());
        System.out.println("Is On: " + fan1.getOn());

        System.out.println("\nFan 2:");
        System.out.println("Speed: " + fan2.getSpeed());
        System.out.println("Radius: " + fan2.getRadius());
        System.out.println("Color: " + fan2.getColor());
        System.out.println("Is On: " + fan2.getOn());
    }
}
