
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
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
//    public static void grep(Path currentDirectory, String pattern, String fileName) {
//        Path filePath = currentDirectory.resolve(fileName);
//        try {
//            Files.lines(filePath)
//                    .filter(line -> line.contains(pattern))
//                    .forEach(System.out::println);
//        } catch (IOException e) {
//            System.out.println("grep: cannot read file '" + fileName + "': " + e.getMessage());
//        }
//    }
    public static void grep(String pattern, String text) {
        // Compile the regex pattern
        Pattern compiledPattern = Pattern.compile(pattern);

        // Split the input text into lines
        String[] lines = text.split("\n");

        // Loop through each line and search for the pattern
        for (String line : lines) {
            Matcher matcher = compiledPattern.matcher(line);
            if (matcher.find()) {
                System.out.println(line);
            }
        }
    }
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
            boolean pip = false;
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
                if (args[i].equals("|")) {
                    pip = true;
                    pipe(args,i+1,output);
                }
            }


            if(!pip&& !appen&&!rdirect) {
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

    public static void pipe(String[] args,int pointer,String input){
        for(int i = pointer;i<args.length;i++){

            if(args[i].equals("grep")){
                grep(args[i+1],input);
            }
            else if (args[i].equals("head")) {
                // Call the head method with the input from the previous command
                head(input,Integer.parseInt(args[i + 1]));
                return; // Exit after executing the command
            }
        }
    }

    //============================================================
    public static void cd(String goingdirectory){
        String currentDirectory2 = System.getProperty("user.dir");
        File Go ;
        switch (goingdirectory) {
            case ".." -> Go = new File(currentDirectory2).getParentFile();
            case "~" -> Go= new File(System.getProperty("user.home"));// Change to home directory
            case "\\" ->   Go = new File("\\");// Change to root directory
            default -> Go = new File(goingdirectory); //Change to required directory


        }
        if (Go.exists()&& Go.isDirectory()){
            currentDirectory = Path.of(Go.getAbsolutePath());
            System.setProperty("user.dir",Go.getAbsolutePath());
        }
        else{
            System.out.println("there is no such directory");
        }
    }
    //============================================================
    //=======================================================================================
//    public static void sort(Path currentDirectory, String fileName) {
//        Path filePath = currentDirectory.resolve(fileName);
//        try {
//            Files.lines(filePath)
//                    .sorted()
//                    .forEach(System.out::println);
//        } catch (IOException e) {
//            System.out.println("sort: cannot read file '" + fileName + "': " + e.getMessage());
//        }
//    }
//===========================================================================================
//    public static void grep(Path currentDirectory, String pattern, String fileName) {
//        Path filePath = currentDirectory.resolve(fileName);
//        try {
//            Files.lines(filePath)
//                    .filter(line -> line.contains(pattern))
//                    .forEach(System.out::println);
//        } catch (IOException e) {
//            System.out.println("grep: cannot read file '" + fileName + "': " + e.getMessage());
//        }
//    }
//==============================================================================================
//    public static void unique(Path currentDirectory, String fileName) {
//        Path filePath = currentDirectory.resolve(fileName);
//        try {
//            Set<String> uniqueLines = new LinkedHashSet<>(Files.readAllLines(filePath));
//            uniqueLines.forEach(System.out::println);
//        } catch (IOException e) {
//            System.out.println("unique: cannot read file '" + fileName + "': " + e.getMessage());
//        }
//    }
//==============================================================================================
    public static void rm(Path currentDirectory, String[] files, boolean recursive) {
        for (String fileName : files) {
            Path filePath = Paths.get(fileName.trim());
            if (!filePath.isAbsolute()) {
                filePath = currentDirectory.resolve(filePath);
            }

            System.out.println("Trying to remove: " + filePath);  // Debugging line

            try {
                if (Files.exists(filePath)) {
                    if (Files.isDirectory(filePath)) {
                        if (recursive) {
                            RMDir(filePath.toString());
                            System.out.println("Removed directory: " + filePath);
                        } else {
                            System.out.println("rm: cannot remove '" + fileName + "': Is a directory (use -r to remove recursively)");
                        }
                    } else {
                        Files.deleteIfExists(filePath);
                        System.out.println("Removed: " + filePath);
                    }
                } else {
                    System.out.println("rm: cannot remove '" + fileName + "': No such file or directory");
                }
            } catch (IOException e) {
                System.out.println("rm: cannot remove '" + fileName + "': " + e.getMessage());
            }
        }
    }

    //=================================================================================
// Add this method to implement the 'mv' command functionality
    public static void mv(Path currentDirectory, String source, String destination) {
        Path sourcePath = currentDirectory.resolve(source);
        Path destinationPath = currentDirectory.resolve(destination);

        try {
            if (!Files.exists(sourcePath)) {
                System.out.println("mv: cannot move '" + source + "': No such file or directory");
                return;
            }

            if (Files.isDirectory(destinationPath)) {
                Path newPath = destinationPath.resolve(sourcePath.getFileName());
                Files.move(sourcePath, newPath);
                System.out.println("Moved: " + source + " to " + newPath);
            } else {
                Files.move(sourcePath, destinationPath);
                System.out.println("Moved: " + source + " to " + destination);
            }
        } catch (IOException e) {
            System.out.println("mv: cannot move '" + source + "': " + e.getMessage());
        }
    }

    //=================================================================================================

    public static void head(String input, int numberOfLinesToShow) {
        String[] lines = input.split("\n");

        for (int i = 0; i < Math.min(numberOfLinesToShow, lines.length); i++) {
            System.out.println(lines[i]);
        }
    }//===============================================================================
    public static void cat(String[] args) {
        if (args.length < 2) {
            System.out.println("cat: missing file operand, Enter file names after command cat");
            return;
        }
        String output="";
        boolean red = false;
        boolean pip = false;
        int ind_p =args.length;
        int ind_red =args.length;
        int ind_app =args.length;
        boolean app = false;
        for (int i = 1; i < args.length; i++) {
            if(!pip&&args[i].equals("|")){
                pip =true;
                ind_p = i+1;
                break;
            }
            if(args[i].equals(">")){
                red = true;
                ind_red = i+1;
                break;
            }
            if(args[i].equals(">>")) {
                app = true;
                ind_app = i+1;
                break;
            }
            try {
                Path filePath = currentDirectory.resolve(args[i]);

                if (Files.exists(filePath) && Files.isRegularFile(filePath)) {
                    List<String> lines = Files.readAllLines(filePath);
                    for (String line : lines) {
                        output+=line +"\n";
                    }
                } else {
                    System.out.println("cat: cannot open '" + args[i] + "': No such file or directory");
                }

            } catch (IOException e) {
                System.out.println("cat: cannot read file '" + args[i] + "': " + e.getMessage());
            }
        }
        if(pip){
            pipe(args,ind_p,output);
        } else if (red) {
            redirect(Paths.get(args[ind_red]),output);
        }
        else if(app){
            appendOutput(Paths.get(args[ind_app]),output);
        }
        else System.out.println(output);

    }

    //===============================================================================================
    public static void PWD(String []args)  {
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

                System.out.print(currentDir);
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
            else if (commandArgs[0].equals("rm")) {
                boolean recursive = false;
                String[] parts; // Declare the parts variable


                // Check if the second argument is "-r" for recursive deletion
                if (commandArgs.length > 1 && commandArgs[1].equals("-r")) {
                    recursive = true;
                    // Remove the "-r" option from the parts
                    parts = Arrays.copyOfRange(commandArgs, 2, commandArgs.length); // Get everything after "-r"
                } else {
                    parts = Arrays.copyOfRange(commandArgs, 1, commandArgs.length); // Get everything after "rm"
                }

                if (parts.length > 0) {
                    rm(currentDirectory, parts, recursive);
                } else {
                    System.out.println("rm: missing file operand");
                }
            }
            else if (commandArgs[0].equals("mv")) {
                if (commandArgs.length < 3) {
                    System.out.println("Usage: mv <source> <destination>");
                    continue; // Skip to the next command prompt
                }
                String source = commandArgs[1];
                String destination = commandArgs[2];
                mv(currentDirectory, source, destination);
            }
            else if (commandArgs[0].equals("cat")) {
                cat(commandArgs); // Call the cat method here

            }
            else if(commandArgs[0].equals("cd")){
                cd(commandArgs[1]);
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
