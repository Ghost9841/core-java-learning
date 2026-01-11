public class Company {
    private static String companyName;
    private String employeeName;
    static void setCompanyName(String companyName){
        Company.companyName = companyName;
    }
    static String getCompanyName(){
        return companyName;
    }
    String getEmployeename(){
        return employeeName;
    }
    void setEmployeeName(String employeeName){
        this.employeeName = employeeName;
    }
}
