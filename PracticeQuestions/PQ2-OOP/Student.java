public class Student{
    private String name;
    private int rollNumber;
    private int grade;
    Student(String name, int rollNumber){
        this.name = name;
        if (rollNumber > 0) {
            
            this.rollNumber = rollNumber;
        } else {
            System.out.println("Invalid roll number");
        }
    }
    Student(String name, int rollNumber, int grade){
        this.name = name;
        if(rollNumber > 0){

            this.rollNumber = rollNumber;
        } else {
            System.out.println("Invalid roll number");
        }
        if (grade > 0) {
            
            this.grade = grade;
        } else {
            System.out.println("Invalid grade");
        }
    }
    void displayDetails(){
        System.out.println(name + rollNumber + grade);
    }

}