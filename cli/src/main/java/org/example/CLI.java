package org.example;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class CLI {
    private static boolean testingMode = false;
    private static Path currentDirectory = Paths.get("").toAbsolutePath();

    public static void setTestingMode(boolean mystate) {
        testingMode = mystate;
    }

    public static void ter() {
        System.out.print("Sad to See you go,But See you later \uD83D\uDC4B ^_^.");
        if (!testingMode) {
            System.exit(0);
        }
    }
    // Malak Hisham
    public static void redirect(Path p, String text) {
        try {
            Files.writeString(p, text);
            System.out.println("Output redirected to file Successfully :)");
            System.out.println("Great \uD83C\uDF89\uD83C\uDF89\uD83C\uDF89\uD83C\uDF89\uD83C\uDF89");
        } catch (IOException e) {
            System.out.println("Sorry,can't redirect to given file");
        }
    }

    public static void appendOutput(Path p,String text) {
        try {
            Files.writeString(p, text, StandardOpenOption.APPEND, StandardOpenOption.CREATE);
            System.out.println("Output appended to file Successfully :)");
            System.out.println("Great \uD83C\uDF89\uD83C\uDF89\uD83C\uDF89\uD83C\uDF89\uD83C\uDF89");
        } catch (IOException e) {
            System.out.println("Sorry,can't append to given file");
        }
    }

    public static String description() {
        return """
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

    }

    //    Shahd Elnassag ^_^
    // Function Implementation of touch command
    public static void createFile(String[] args) {
        try {
            if (args.length < 2) {
                System.out.println("touch: missing file operand, Enter file name After command touch");
            }
            Path fileDirectory = currentDirectory.resolve(args[1]);

            if (Files.exists(fileDirectory)) {
                System.out.println("File Already Exists ✨" + fileDirectory);
            }
            Files.createFile(fileDirectory);
            System.out.println("File Created Successfully \uD83C\uDF89: \n" + fileDirectory);

        } catch (Exception e) {
            // Catch all other exceptions to prevent program termination
            System.out.println("An unexpected error occurred \uD83D\uDE33: \n" + e.getMessage());
        }
    }

    // Function Implementation of ls , ls -a , la -r commands
    public static void listFiles(String[] args) {

        try {
            List<String> listOfFiles = new ArrayList<>();
            boolean listHidden = false;
            boolean reverse = false;

            for (String arg : args) {
                if (arg.equals("-a")) {
                    listHidden = true;
                }
                if (arg.equals("-r")) {
                    reverse = true;
                }
            }

            DirectoryStream<Path> directoryFiles = Files.newDirectoryStream(currentDirectory);

            for (Path file : directoryFiles) {
                listOfFiles.add(file.getFileName().toString());
            }
            // Handle ls -a
            if (listHidden == false) {
                listOfFiles.removeIf(fileName -> fileName.startsWith("."));
            }

            // Handel ls -r
            if (reverse == true) {
                Collections.reverse(listOfFiles);
            }
            String output="";

            output += "Files and Directories in " + Paths.get("").toAbsolutePath().getFileName() + ": ";
            for (String file : listOfFiles) {
                Path path = currentDirectory.resolve(file);
                if (Files.isDirectory(path)) {
                    output += file + "/";
                } else {
                    output += file;
                }
                output += '\n';
            }
            boolean rdirect = false;
            boolean appen = false;
            for(int i =0;i<args.length;i++){
                if(args[i].equals(">"))
                {
                    rdirect =true;
                    if(i!= args.length-1)
                        redirect(Paths.get(args[i+1]),output);
                    else
                        throw new RuntimeException("Please enter the file to redirect in");

                }if(args[i].equals(">>"))
                {
                    appen= true;
                    if(i!= args.length-1)
                        appendOutput(Paths.get(args[i+1]),output);
                    else
                        throw new RuntimeException("Please enter the file to redirect in");

                }
            }
            if(!appen&&!rdirect) {
                System.out.print(output);
            }


        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }


    //============================================================
    public static void MKDir(String DirName) {
        String currentDir = System.getProperty("user.dir");
        String directoryPath = currentDir + "\\"+ DirName;
        File Dir = new File(directoryPath);
        Dir.mkdir() ;
    }
    //============================================================
    public static void RMDir(String DirName)  {
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
    public  static void PWD(String []args)  {
        String currentDir = "Current directory is:"+ System.getProperty("user.dir") +"\n";
        try{
            boolean rd = false,ap= false;
            for(int i =0;i<args.length;i++){
                if(args[i].equals(">")){
                    if(i==args.length-1) throw new RuntimeException("You don't mentioned the file to redirect in");
                    else {
                        rd = true;
                        redirect(Paths.get(args[i + 1]), currentDir);
                    }
                }
                if(args[i].equals(">>")){
                    if(i==args.length-1) throw new RuntimeException("You don't mentioned the file to append to");
                    else {
                        ap = true;
                        appendOutput(Paths.get(args[i + 1]), currentDir);
                    }
                }
            }
            // System.out.println(rd);
            if(!ap&&!rd){

                System.out.println(currentDir);
            }
        }
        catch(Exception e){
            System.out.println( e.getMessage());
        }
    }


    public static void runMyCli() {
        Scanner input = new Scanner(System.in);
        while (true) {
            System.out.print("commandLineIsBest> ");
            String command = input.nextLine().trim();
            String[] commandArgs = command.split(" ");
            if (commandArgs[0].equals("exit")) {
                ter();
            } else if (commandArgs[0].equals("help")) {

                hel(commandArgs);

                // Test touch command
            } else if (commandArgs[0].equals("touch")) {
                createFile(commandArgs);
                // Test ls, ls -a, ls -r commands
            } else if (commandArgs[0].equals("ls")) {
                listFiles(commandArgs);
            } else if (commandArgs[0].equals("pwd")) {
                PWD(commandArgs);

            } else if (commandArgs[0].equals("mkdir")) {
                MKDir(commandArgs[1]);

            }
            else if(commandArgs[0].equals("rmdir")){
                RMDir(commandArgs[1]);
            }


            // Final Case be careful Do not Delete Me
            else {
                System.out.println("Command not found: " + commandArgs[0]);

            }
        }

    }

    public static void hel (String[]commandArgs){
        if (1 < commandArgs.length) {
            if (commandArgs[1].equals(">"))
                redirect(Paths.get(commandArgs[2]), description());
            else {
                appendOutput(Paths.get(commandArgs[2]), description());
            }
        } else {
            System.out.print(description());

        }
    }





    public static void main(String []args) {
        runMyCli();
    }
}