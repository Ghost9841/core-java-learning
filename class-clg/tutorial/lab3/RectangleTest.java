public class RectangleTest {
    public static void main(String[] args) {
        Rectangle rectangle = new Rectangle();
        rectangle.setWidth(5);
        rectangle.setHeight(50);
        rectangle.setColor("Red");

        System.out.println("Width: " + rectangle.getWidth());
        System.out.println("Height: " + rectangle.getHeight());
        System.out.println("Color: " + rectangle.getColor());
        System.out.println("Area: " + rectangle.findArea());
        System.out.println("Perimeter: " + rectangle.findPerimeter());
    }
}
