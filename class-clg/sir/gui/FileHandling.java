import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class FileHandling {
    public static void main(String[] args) {
        Path path = Paths.get("student.txt");
        try {
            Files.writeString(
                path,
                "Ghost\n",
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND
        );
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