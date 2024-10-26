package org.example;

import java.nio.file.Path;
import java.nio.file.*;
//import java.nio.file.FileAlreadyExistsException;
import java.io.IOException;
import java.util.Scanner;

public class CLI {
  private static boolean testingMode = false;
  private static Path currentDirectory = Paths.get("").toAbsolutePath();

  public static void setTestingMode(boolean mystate){
      testingMode = mystate;
  }
    public static void ter() {
        System.out.print("See you later ^_^.");
        if(!testingMode) {
            System.exit(0);
        }
    }
    public static String description(){
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
    public static void hel(){
      System.out.print(description());

    }
//    Shahd Elnassag


    // Function Implementation of touch command
    public static void createFile(String[]args){
        try {
            if(args.length < 2){
              System.out.println("touch: missing file operand, Enter file name After command touch");
          }
            Path fileDirectory = currentDirectory.resolve(args[1]);

            if (Files.exists(fileDirectory)) {
              System.out.println("File Already Exists" + fileDirectory );
          }
          Files.createFile(fileDirectory);
          System.out.println("File Created Successfully: "+fileDirectory);

      } catch (Exception e) {
        // Catch all other exceptions to prevent program termination
        System.out.println("An unexpected error occurred: " + e.getMessage());
    }
    }
    // Function Implementation of ls command
    // Will added soon God Willing


    public static void runMyCli() {
        Scanner input = new Scanner(System.in);
        while (true) {
            System.out.print("commandLineIsBest> ");
            String command = input.nextLine().trim();
            String [] commandArgs = command.split(" ");
            if (commandArgs[0].equals("exit")) {
                ter();
            }
            else if(commandArgs[0].equals("help")){
              hel();
            }else if(commandArgs[0].equals("touch")){
               createFile(commandArgs);
            }
        }

    }

    public static void main(String []args) {
runMyCli();
    }
}

