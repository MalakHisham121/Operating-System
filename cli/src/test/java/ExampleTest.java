import org.example.CLI;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ExampleTest {
    private final ByteArrayOutputStream Myoutput = new ByteArrayOutputStream();
    private final PrintStream myout = System.out;



//    @BeforeAll
//    public static void  TakeCareIamTesting(){
//        CLI.setTestingMode(true);
//    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(myout);
    }


    @Test
    void terminateMyProgramme(){
        CLI.ter();
        assertTrue(Myoutput.toString().contains("Sad to See you go,But See you later \uD83D\uDC4B ^_^."));

    }

    @Test
    void helpToknowCommands(){
        String[] ar = {"help"};
        CLI.hel(ar);
        String expected = """
            System Commands:
            - pwd          : Displays the current directory path.
            - cd           : Changes the current directory to another directory.
            - ls           : Lists files and directories in the current directory.
              - ls -a      : Lists all files, including hidden files.
              - ls -r      : Lists files and directories in reverse order.
            - mkdir        : Creates a new directory.
            - rmdir        : Removes an empty directory.
            - touch        : Creates a new, empty file.
            - mv           : Moves or renames a file or directory.
            - rm           : Deletes a file or directory (with -r for recursive).
            - cat          : Displays the contents of a file.
            - >            : Redirects output to a file, overwriting its contents.
            - >>           : Redirects output to a file, appending to its contents.
            - |            : Pipes the output of one command as input to another.
            
            Internal Commands:
            - exit         : Exits the CLI program.
            - help         : Displays available commands and their descriptions.
            """;

        assertEquals(expected,Myoutput.toString());


    }
    // Shahd Elnassag Test Cases
//    @Test
//    void testTouchCommand()throws Exception{
//        System.out.println("Testing Directory: "+ testDirectory);
//        String [] args = {"touch","testFile"};
//        CLI.createFile(args);
//
//        Path testPath = testDirectory.resolve(args[1]);
//
//        CLI.createFile(args);
//        assertTrue(Files.exists(testPath) , "File Created");
//        assertTrue(Myoutput.toString().contains("File Created Successfully: " + testPath));
//
//        // Another Test
////        Myoutput.reset();
////        CLI.createFile(args);
////        assertTrue(Myoutput.toString().contains("File Already Exists" + testPath));
//
//
//
//
//    }
//
//}


    @Test
    public void testRedirect() throws IOException{
        String text = "I am just testing\nI am Sure you are doing great! ";
        String file = "malak.txt";
        CLI.redirect(Paths.get(file),text);
        String currentContent = Files.readString(Paths.get(file));
        assertEquals(text,currentContent);
        Files.delete((Paths.get(file)));

    }

    @Test
    public void testAppend() throws IOException{
        String file = "malak";
        String beforeTest;
        if(Files.exists(Paths.get(file))) {
            beforeTest = Files.readString(Paths.get(file));
        }
        else beforeTest = "";
        String text = "I am just testing\nI am Sure you are doing great! ";
        CLI.appendOutput(Paths.get(file),text);
        String currentContent = Files.readString(Paths.get(file));
        assertEquals(beforeTest+text,currentContent);
        Files.delete((Paths.get(file)));

    }

}