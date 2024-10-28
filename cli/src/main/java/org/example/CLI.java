package org.example;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class CLI {
  private static boolean testingMode = false;
  public static Path currentDirectory = Paths.get("").toAbsolutePath();

  public static void setTestingMode(boolean mystate){
      testingMode = mystate;
  }
    public static void ter() {
        System.out.print("Sad to See you go,But See you later \uD83D\uDC4B ^_^.");
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
//    Shahd Elnassag ^_^
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
    // Function Implementation of ls , ls -a , la -r commands
    public static void listFiles(String[]args){

      try {
          List<String> listOfFiles = new ArrayList<>();
          boolean listHidden = false;
          boolean reverse = false;

          for (String arg : args) {
              if (arg.equals("-a")){
                  listHidden = true;
              }if (arg.equals("-r")){
                  reverse = true;
              }
          }

          DirectoryStream<Path> directoryFiles = Files.newDirectoryStream(currentDirectory);

          for (Path file : directoryFiles) {
              listOfFiles.add(file.getFileName().toString());
          }
          // Handle ls -a
          if (listHidden == false) {
              listOfFiles.removeIf(fileName ->fileName.startsWith("."));
          }

          // Handel ls -r
          if (reverse == true) {
              Collections.reverse(listOfFiles);
          }
          System.out.println("Files and Directories in " + Paths.get("").toAbsolutePath().getFileName() + ": ");
          for (String file : listOfFiles) {
              Path path = currentDirectory.resolve(file);
              if (Files.isDirectory(path)) {
                  System.out.println(file + " /");
              }else {
                  System.out.println(file);
              }
          }

      }catch (Exception e){
          System.out.println("An unexpected error occurred: " + e.getMessage());
      }
    }


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
              // Test touch command
            }else if(commandArgs[0].equals("touch")){
               createFile(commandArgs);
               // Test ls, ls -a, ls -r commands
            }else if(commandArgs[0].equals("ls")){
                listFiles(commandArgs);
            }

            // Final Case be careful Do not Delete Me
            else {
                System.out.println("Command not found: " + commandArgs[0]);

            }
        }

    }

    public static void main(String []args) {
runMyCli();
    }
}

