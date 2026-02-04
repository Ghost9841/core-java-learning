class Person {
    String name;
    String address;
    String phoneNumber;
    String email;

    public Person(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + ": " + name;
    }
}
class Student extends Person {

    public enum Status { FRESHMAN, SOPHOMORE, JUNIOR, SENIOR }

    private Status status;

    public Student(String name, Status status) {
        super(name);
        this.status = status;
    }

    @Override
    public String toString() {
        return super.toString() + ", Status: " + status;
    }
}


class MyDate {
    private int year;
    private int month;
    private int day;

    public MyDate(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    @Override
    public String toString() {
        return year + "-" + month + "-" + day;
    }
}


class Employee extends Person {
    protected String office;
    protected double salary;
    protected MyDate dateHired;

    public Employee(String name, String office, double salary, MyDate dateHired) {
        super(name);
        this.office = office;
        this.salary = salary;
        this.dateHired = dateHired;
    }

    @Override
    public String toString() {
        return super.toString() +
               ", Office: " + office +
               ", Salary: " + salary +
               ", Date Hired: " + dateHired;
    }
}

class Faculty extends Employee {
    private String officeHours;
    private String rank;

    public Faculty(String name, String office, double salary, MyDate dateHired,
                   String officeHours, String rank) {
        super(name, office, salary, dateHired);
        this.officeHours = officeHours;
        this.rank = rank;
    }

    @Override
    public String toString() {
        return super.toString() +
               ", Office Hours: " + officeHours +
               ", Rank: " + rank;
    }
}

class Staff extends Employee {
    protected String title;

    public Staff(String name, String office, double salary, MyDate dateHired, String title) {
        super(name, office, salary, dateHired);
        this.title = title;
    }

    @Override
    public String toString() {
        return super.toString() + ", Title: " + title;
    }
}

class FullTime extends Staff {
    private double fixedSalary;

    public FullTime(String name, String office, MyDate dateHired,
                    String title, double fixedSalary) {
        super(name, office, fixedSalary, dateHired, title);
        this.fixedSalary = fixedSalary;
    }

    public double getEarnings() {
        return fixedSalary;
    }

    @Override
    public String toString() {
        return super.toString() + ", Fixed Salary: " + fixedSalary;
    }
}

class PartTime extends Staff {
    private double hourlyRate;
    private int hoursWorked;

    public PartTime(String name, String office, MyDate dateHired,
                    String title, double hourlyRate, int hoursWorked) {
        super(name, office, 0, dateHired, title);
        this.hourlyRate = hourlyRate;
        this.hoursWorked = hoursWorked;
    }

    public double getEarnings() {
        return hourlyRate * hoursWorked;
    }

    @Override
    public String toString() {
        return super.toString() +
               ", Hourly Rate: " + hourlyRate +
               ", Hours Worked: " + hoursWorked +
               ", Earnings: " + getEarnings();
    }
}

