public class Fan {
    private int speed;
    private boolean on;
    private double radius;
    private String color;
    
    public static final int SLOW = 1;
    public static final int MEDIUM = 2;
    public static final int FAST = 3;

    Fan() {
        speed = SLOW;
        on = false;
        radius = 5;
        color = "blue";
    }
    Fan(int speed, boolean on, double radius, String color) {
        this.speed = speed;
        this.on = on;
        this.radius = radius;
        this.color = color;
    }
    int getSpeed() {
        return speed;
    }
    void setSpeed(int speed) {
        this.speed = speed;
    }
    String getColor() {
        return color;
    }
    void setColor(String color) {
        this.color = color;
    }
    double getRadius() {
        return radius;
    }
    void setRadius(double radius) {
        this.radius = radius;
    }
    boolean getOn() {
        return on;
    }
    void setOn(boolean on) {
        this.on = on;
    }
    public String toString() {
        if (on) {
            return "Fan is on: Speed = " + speed + ", Color = " + color + ", Radius = " + radius;
        } else {
            return "Fan is off: Color = " + color + ", Radius = " + radius;
        }
    }   
}
