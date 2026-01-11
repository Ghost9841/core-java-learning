public class CompanyTest {
    public static void main(String[] args) {
        Company c1 = new Company();
        Company.setCompanyName("Aakash");
        c1.setEmployeeName("Aakash");
        System.out.println(c1.getCompanyName());
        System.out.println(c1.getEmployeename());
        Company.setCompanyName("Tech Nest");
        System.out.println(Company.getCompanyName());
    }
}
