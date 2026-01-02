public class Company {
    static String companyName;
    String employeeName;
    
    public static void setCompanyName(String companyName) {
        Company.companyName = companyName;
    }
    void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }
    void displayDetails(){
        System.out.println(employeeName + "\n"+ companyName);
    }
}
