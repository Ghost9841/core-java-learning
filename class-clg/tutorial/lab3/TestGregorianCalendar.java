import java.util.GregorianCalendar;
public class TestGregorianCalendar {
  public static void main(String[] args) {
    
    GregorianCalendar calendar = new GregorianCalendar();
  
      int year = calendar.get(GregorianCalendar.YEAR);
      int month = calendar.get(GregorianCalendar.MONTH);
      int date = calendar.get(GregorianCalendar.DATE);


      System.out.println("Current year: " + year);
      System.out.println("Current month: " + month);
      System.out.println("Current date: " + date);
  }

}