import org.example.CLI;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class ExampleTest {
    private final ByteArrayOutputStream Myoutput = new ByteArrayOutputStream();
    private final PrintStream myout = System.out;
    private Path testDir;

    @BeforeEach
    public void setUpStreams() throws IOException {
        testDir = Files.createTempDirectory("testDir");
      //  System.setOut(new PrintStream(Myoutput));

        // Samples File used in Testing LS
        Files.createFile(testDir.resolve("nonHiddenTestFile.txt"));
        Files.createFile(testDir.resolve(".hiddenTestFile.txt"));
        Files.createDirectory(testDir.resolve("testDir1"));
        CLI.currentDirectory = testDir;


    }
    @BeforeEach
    public void restMyoutput() {
        Myoutput.reset();
    }

    @BeforeAll
    public static void  TakeCareIamTesting(){
        CLI.setTestingMode(true);
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(myout);
    }

    @AfterEach
    public void tearDown() throws Exception {
        // Clean up the temporary directory
        for (Path path : Files.newDirectoryStream(testDir)) {
            Files.delete(path);
        }
        Files.delete(testDir); // Delete the test directory
    }


    @Test
    void terminateMyProgramme(){
        System.setOut(new PrintStream(Myoutput));
        CLI.ter();
        assertTrue(Myoutput.toString().contains("Sad to See you go,But See you later \uD83D\uDC4B ^_^."));

    }

    @Test
    void helpToknowCommands(){
        System.setOut(new PrintStream(Myoutput));
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

    @Test
    void testTouchCommand()throws Exception{
        System.setOut(new PrintStream(Myoutput));
        String [] args = {"touch","testFile"};
        Path testPath = testDir.resolve(args[1]);

//        Path testPath = Paths.get("").toAbsolutePath().resolve(args[1]);

        Files.deleteIfExists(testPath);
        CLI.createFile(args);

        // Verify the file was created successfully
        assertTrue(Files.exists(testPath));
       assertTrue(Myoutput.toString().contains("File Created Successfully \uD83C\uDF89: \n" + testPath));


        // Another Test  Verify it detects the file already exists
        Myoutput.reset();
        CLI.createFile(args);
        assertTrue(Myoutput.toString().contains("File Already Exists ✨" + testPath));
        Files.deleteIfExists(testPath);

        // Another Test
        Myoutput.reset();
        String[] args1 = {"touch"};

        // Call the method under test
        CLI.createFile(args1);

        // Assertions
        assertTrue(Myoutput.toString().contains("missing file operand"));

    }

    // Test lsCommand will add soon God willing
    @Test
    public void TestLsCommand() {
        System.setOut(new PrintStream(Myoutput));

        // tett for ls
        String[] args = {"ls"};
        CLI.listFiles(args);

        String expectedLsOutput = "Files and Directories in cli: "
                + "nonHiddenTestFile.txt\n"
                + "testDir1/\n";

        assertEquals(expectedLsOutput.trim(), Myoutput.toString().trim(), "Output did not match expected value.");

// test for ls -a
        Myoutput.reset();

        String[] args_a = {"ls", "-a"};
        CLI.listFiles(args_a);

        String expectedLs_a = "Files and Directories in cli: "
                + ".hiddenTestFile.txt\n"
                + "nonHiddenTestFile.txt\n"
                + "testDir1/\n";

        assertEquals(expectedLs_a.trim(), Myoutput.toString().trim(), "Output did not match expected value with -a.");

// Test for la -r
        Myoutput.reset();

        String[] args_r = {"ls", "-r"};
        CLI.listFiles(args_r);

        String expectedLs_r = "Files and Directories in cli: "
                + "testDir1/\n"
                + "nonHiddenTestFile.txt\n" ;

        assertEquals(expectedLs_r.trim(), Myoutput.toString().trim(), "Output did not match expected value with -r.");

    }




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


    @Test //this is testcase For MKdir()
    public void Test1(){
        CLI myClass = new CLI();
        myClass.MKDir("menna");
        File F = new File("menna");
        assertTrue(F.exists());
    }

    @Test //this is testcase For RMdir()
    public void Test2(){
        CLI myClass = new CLI();
        File F = new File("menna");
        assertTrue(F.exists());
        myClass.RMDir("menna");
        assertFalse(F.exists());

    }

    @Test //this is testcase For cd()
    // cd not added into commands yet
    public void Test3(){
        CLI myClass = new CLI();
        String DirName = "menna";
        File F = new File("menna");
        F.mkdir();
        CLI.cd(DirName);

        assertEquals(F.getAbsolutePath(),System.getProperty("user.dir"));
    }

    @Test
    void Test4(){ // this is testcase for ped()
        CLI myClass = new CLI();
        String[] li = {"pwd"};
        CLI.PWD(li);
        assertEquals("Current directory is:"+System.getProperty("user.dir")+"\n",Myoutput.toString());
    }

}