import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.*;
public class CLI {

    //============================================================
    public void MKDir(String DirName) {
        String currentDir = System.getProperty("user.dir");
        String directoryPath = currentDir + "\\"+ DirName;
        File Dir = new File(directoryPath);
    }
    //============================================================
    public void RMDir(String DirName)  {
        String currentDir = System.getProperty("user.dir");
        String directoryPath = currentDir + "\\"+ DirName;
        try{
            Files.delete(Paths.get(directoryPath));
            System.out.println("Directory deleted successfully");
        }
        catch (Exception e){
            System.out.println("Error"+e.getMessage());
        }
    }
    //============================================================
    public void PWD()  {
        String currentDir = System.getProperty("user.dir");
        System.out.println("current directory is :"+currentDir);
    }
    //============================================================

    //============================================================

}
