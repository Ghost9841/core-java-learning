public class Car{
    static int totalCars = 0;
    Car(String name){
        System.out.println(name);
        totalCars++;
    }
    static void main(String[] args){
        Car car1 = new Car("Ferari");
        System.out.println("Total Cars" + totalCars);
    }
}