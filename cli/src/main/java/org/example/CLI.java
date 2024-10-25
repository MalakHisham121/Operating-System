package org.example;

import java.util.Scanner;

public class CLI {
  private static boolean testingMode = false;

  public static void setTestingMode(boolean mystate){
      testingMode = mystate;
  }
    public static void ter() {
        System.out.print("Sad to see you go.");
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


    public static void runMyCli() {
        Scanner command = new Scanner(System.in);
        while (true) {
            System.out.print("commandLineIsBest> ");
            String com = command.nextLine().trim();
            if (com.equals("exit")) {
                ter();
            }
            else if(com.equals("help")){
              hel();
            }
        }

    }

    public static void main(String []args) {
runMyCli();
    }
}

