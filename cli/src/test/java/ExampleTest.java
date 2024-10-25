import org.example.CLI;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ExampleTest {
private final ByteArrayOutputStream Myoutput = new ByteArrayOutputStream();
    private final PrintStream myout = System.out;
    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(Myoutput));
    }

    @BeforeAll
    public static void  TakeCareIamTesting(){
        CLI.setTestingMode(true);
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(myout);
    }


    @Test
    void terminateMyProgramme(){
        CLI.ter();
        assertTrue(Myoutput.toString().contains("Sad to see you go."));

    }

    @Test
    void helpToknowCommands(){
        CLI.hel();
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

}
