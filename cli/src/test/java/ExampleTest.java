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
    private Path sourceFile , existFile , MVDir;

    @BeforeEach
    public void setUpStreams() throws IOException {
        testDir = Files.createTempDirectory("testDir");
        System.setOut(new PrintStream(Myoutput));

        // Samples File used in Testing LS
        Files.createFile(testDir.resolve("nonHiddenTestFile.txt"));
        Files.createFile(testDir.resolve(".hiddenTestFile.txt"));
        Files.createDirectory(testDir.resolve("testDir1"));
        CLI.currentDirectory = testDir;
        // for rm
        sourceFile = testDir.resolve("sourceFile");
        Files.writeString(sourceFile, "Shahd Elnassag this is Source File");

        existFile = testDir.resolve("existFile");
        Files.writeString(existFile, "Shahd Elnassag this existing File");
        MVDir = testDir.resolve("MVDirTest");
        Files.createDirectory(MVDir);


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

// used if directory not empty
    private void deleteDirectory(Path dir) throws IOException {
        try (var paths = Files.newDirectoryStream(dir)) {
            for (Path path : paths) {
                if (Files.isDirectory(path)) {
                    deleteDirectory(path);
                }
                Files.deleteIfExists(path);
            }
        }
        Files.deleteIfExists(dir);
    }
@AfterEach
    public void tearDown() throws Exception {
        try (var paths = Files.newDirectoryStream(testDir)) {
            for (Path path : paths) {
                if (Files.isDirectory(path)) {
                    deleteDirectory(path);
                } else {
                    Files.deleteIfExists(path);
                }
            }
        }
        Files.deleteIfExists(testDir);
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
CLI.MKDir("cli");
CLI.cd("cli");
String[] gg ={"touch","nonHiddenTestFile.txt"};
        CLI.createFile(gg);
        CLI.MKDir("testDir1");
        gg = new String[]{"touch",".hiddenTestFile.txt"};
        CLI.createFile(gg);
        // tett for ls
        String[] args = {"ls"};
        Myoutput.reset();
        CLI.listFiles(args);

        String expectedLsOutput = "nonHiddenTestFile.txt\n"
                + "testDir1/\n";

        assertEquals(expectedLsOutput.trim(), Myoutput.toString().trim(), "Output did not match expected value.");

// test for ls -a
        Myoutput.reset();

        String[] args_a = {"ls", "-a"};
        Myoutput.reset();
        CLI.listFiles(args_a);

        String expectedLs_a = ".hiddenTestFile.txt\n"
                + "nonHiddenTestFile.txt\n"
                + "testDir1/\n";

        assertEquals(expectedLs_a.trim(), Myoutput.toString().trim(), "Output did not match expected value with -a.");

// Test for la -r
        Myoutput.reset();

        String[] args_r = {"ls", "-r"};
        Myoutput.reset();
        CLI.listFiles(args_r);

        String expectedLs_r = "testDir1/\n"
                + "nonHiddenTestFile.txt\n" ;

        assertEquals(expectedLs_r.trim(), Myoutput.toString().trim(), "Output did not match expected value with -r.");

    }

    // Test mv Command
    @Test
    public void testMVCommand() throws IOException{

        // test Rename Case1
        CLI.mv(testDir,"sourceFile","testRenameFile");
        Path renameFile = testDir.resolve("testRenameFile");
        assertTrue(Files.exists(renameFile), "File Renamed");
        assertFalse(Files.exists(sourceFile),"old name File doesn't exist");
        assertEquals("Renamed: sourceFile to testRenameFile",Myoutput.toString().trim());

        // Move renamed File case2
        Myoutput.reset();
        CLI.mv(testDir,"testRenameFile","MVDirTest");
        assertTrue(Files.exists(MVDir.resolve("testRenameFile")), " moved Renamed File");
        assertFalse(Files.exists(renameFile),"Directory Change of this file");
        assertEquals("Moved: testRenameFile to MVDirTest/",Myoutput.toString().trim());


        // Test Move Case3
        Myoutput.reset();
        CLI.mv(testDir,"existFile","MVDirTest");
        assertTrue(Files.exists(MVDir.resolve("existFile")), "File moved");
        assertFalse(Files.exists(existFile),"Directory Change of this file");
        assertEquals("Moved: existFile to MVDirTest/",Myoutput.toString().trim());

        // Renamed Moved File  Case4
        Myoutput.reset();
        Myoutput.reset();
        CLI.mv(MVDir, "existFile", "renameMovedFile");
        assertTrue(Files.exists(MVDir.resolve("renameMovedFile")), "Moved renamed File");
        assertFalse(Files.exists(MVDir.resolve("existFile")), "Old name File doesn't exist after renaming");
        assertEquals("Renamed: existFile to renameMovedFile", Myoutput.toString().trim());

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
    public void testMKdir(){
        CLI myClass = new CLI();
        myClass.MKDir("menna");
        File F = new File("menna");
        assertTrue(F.exists());
    }

    @Test //this is testcase For RMdir()
    public void testRMdir(){
        CLI myClass = new CLI();
        File F = new File("menna");
        assertTrue(F.exists());
        myClass.RMDir("menna");
        assertFalse(F.exists());

    }

    @Test //this is testcase For cd()
    // cd not added into commands yet
    void testCD(){
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

    @Test
    void testpipeStream() throws IOException {
        System.setOut(new PrintStream(Myoutput));
        String[] li ={"ls","|","grep","p"};
        CLI.cd("..");

         CLI.listFiles(li);// call the first function to get input of the pipe
        String full_command = Myoutput.toString();// output of the full command
        Myoutput.reset();
        String [] li2 ={"ls"};
        CLI.listFiles(li2);
        String input =  Myoutput.toString(); // save the output of first command to pass it as input to the second command
        Myoutput.reset();

        CLI.grep(li[3],input,li,3);//  pass the output of first command to second command
        String output1 = Myoutput.toString();   // save the output the got by the pipe

        assertEquals(output1,full_command);


    }

    @Test
    void testpipeRedirectio() throws IOException {
        System.setOut(new PrintStream(Myoutput));
        String[]li = new String[]{"ls","|","grep","fi",">","pipeAndRedirection"};
        CLI.cd("..");
        // call the first function to get input of the pipe
        CLI.listFiles(li);
        Myoutput.reset();
        String full_command = Files.readString(new File(li[5]).toPath());// output of the full command
        String[] li2 =new String[]{"ls"};
        CLI.listFiles(li2);
        String input =  Myoutput.toString(); // save the output of first command to pass it as input to the second command
        Myoutput.reset();

        CLI.grep(li[3],input,li,3);//  pass the output of first command to second command
        String output1 = Files.readString(Paths.get(li[5]));   // save the output the got by the pipe
        Files.delete((Paths.get(li[5])));
        assertEquals(output1,full_command);

    }


    // cat
    @Test
    void Test5() {
        CLI myCLI = new CLI();
        ByteArrayOutputStream myOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(myOutput));

        String[] args = {"cat", "habiba.txt"};

        CLI.cat(args);

        System.out.println("Actual Output: " + myOutput.toString().trim());

        String expectedOutput = "habiba mohamed ahmed";

        System.out.println("Actual Output: " + myOutput.toString().trim());

        System.setOut(System.out);
    }
    @Test
    void Test6() {
        CLI myCLI = new CLI();
        ByteArrayOutputStream myOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(myOutput));

        String[] args = {"cat", "C:\\Users\\Dell\\Downloads\\Operating-System-main (3)\\Operating-System-main\\cli\\src\\test\\java\\habiba.txt"};

        CLI.cat(args);

        String actualOutput = myOutput.toString().trim();
        System.out.println("Actual Output: " + actualOutput);

        String expectedOutput = "gggggggggggggggg'\n'hhhhhhhhhhhhhhhh'\n'zzzzzzzzzzzzzzzz";

        System.out.println("Actual Output: " + myOutput.toString().trim());

        System.setOut(System.out);
    }

    @Test // rm
    void testRemoveSingleFile() throws IOException {
        Path test = Paths.get(System.getProperty("user.dir"));
        Path testFile = test.resolve("habiba.txt");
        Files.createFile(testFile);

        String[] filesToRemove = {"habiba.txt"};
        CLI.rm(test, filesToRemove, false);

        assertFalse(Files.exists(testFile));
        assertTrue(Myoutput.toString().contains("Removed: " + testFile));
    }

    @Test
    void testRemoveDirectoryRecursively() throws IOException {
        Path testrmDir = Paths.get(System.getProperty("user.dir")).resolve("habiba");
        Files.createDirectory(testrmDir);

        Path subDir1 = testrmDir.resolve("subDir1");
        Path subDir2 = testrmDir.resolve("subDir2");
        Files.createDirectory(subDir1);
        Files.createDirectory(subDir2);

        Path file1 = testrmDir.resolve("file1.txt");
        Path file2 = subDir1.resolve("file2.txt");
        Path file3 = subDir2.resolve("file3.txt");
        Files.createFile(file1);
        Files.createFile(file2);
        Files.createFile(file3);

        String[] dirsToRemove = {"habiba"};
        CLI.rm(Paths.get(System.getProperty("user.dir")), dirsToRemove, true);

        assertFalse(Files.exists(testrmDir),"Remove testrmDir");
        assertFalse(Files.exists(subDir1),"Remove subDir1");
        assertFalse(Files.exists(subDir2),"Remove subDir2");
        assertFalse(Files.exists(file1),"Remove file1");
        assertFalse(Files.exists(file2),"Remove file2");
        assertFalse(Files.exists(file3),"Remove file3");

        assertTrue(Myoutput.toString().contains("Removed directory: " + testrmDir));
    }


}