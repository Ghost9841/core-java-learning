public class Student {
    String name;
    int rollNumber;
    int grade;
    Student(String name, int rollNumber){
        this.name = name;
        this.rollNumber = rollNumber;
    }
    Student(int grade,String name, int rollNumber ){
        this.grade = grade;
        this.name = name;
        this.rollNumber = rollNumber;
    }

}
