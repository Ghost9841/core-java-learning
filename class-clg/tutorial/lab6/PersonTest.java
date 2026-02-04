public class PersonTest {
    public static void main(String[] args) {

        Student student = new Student("Alice", Student.Status.SOPHOMORE);
        System.out.println(student);

        Faculty faculty = new Faculty(
                "Dr. Smith",
                "Room 101",
                100000,
                new MyDate(2020, 1, 1),
                "9AM - 12PM",
                "Professor"
        );
        System.out.println(faculty);

        FullTime fullTime = new FullTime(
                "John",
                "Admin Office",
                new MyDate(2018, 5, 10),
                "Manager",
                60000
        );
        System.out.println(fullTime);
        System.out.println("Full Time Earnings: " + fullTime.getEarnings());

        PartTime partTime = new PartTime(
                "Emma",
                "Library",
                new MyDate(2022, 3, 15),
                "Assistant",
                20,
                25
        );
        System.out.println(partTime);
    }
}
