import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Info {
    public static void main(String[] args) {
        Path path = Paths.get("info.txt");
        try {
            
        } catch (Exception e) {
            // TODO: handle exception
        System.out.println("Sth mistake here" + e.getMessage());
        }
        try { 
            System.out.println(Files.readString(path));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("Sth mistake here" + e.getMessage());   
        }
    }
}
