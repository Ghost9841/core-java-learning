public class Rectangle {
   private double width;
   private double height;
   private static String color;

   Rectangle(){
    width = 0;
    height = 0;
   }

   Rectangle(double width, double height, String color){
    this.width = width;
    this.height = height;
    Rectangle.color = color; 
   }
    double getWidth() {
       return width;
   }
    void setWidth(double width) {
       this.width = width;
   }
    double getHeight() {
       return height;
   }
    void setHeight(double height) {
       this.height = height;
   }
    String getColor() {
       return color;
   }
    void setColor(String color) {
       Rectangle.color = color;
   }

   public double findArea(){
    return width * height;
   }
 public double findPerimeter(){
    return 2* (width + height);
   }



}