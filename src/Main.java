import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class Main{

    private static void printHelpMessage(){
        System.out.println("Universal Turing Machine Emulator");
        System.out.println("==================================");
        System.out.println();
        System.out.println("Usage: java Main [options]");
        System.out.println();
        System.out.println("Options:");
        System.out.println("  -p, --programming <program>  The Turing machine program to execute");
        System.out.println("  -i, --input                  Enable manual input mode");
        System.out.println("  -f, --file <path>            Read program from a file");
        System.out.println("  -s, --step                   Enable step-by-step execution mode");
        System.out.println("  -h, --help                   Display this help message");
        System.out.println();
        System.out.println("Examples:");
        System.out.println("  java Main -p \"<program>\"");
        System.out.println("  java Main -p \"<program>\" -i");
        System.out.println("  java Main -f program.txt -s");
        System.out.println();
        System.out.println("Note: Either -p or -f must be specified.");
    }

    public static void main(String[] args)
    {
        Charset CHARSET = StandardCharsets.UTF_8;
        String programing = null;
        boolean manualInput = false;
        boolean step = false;
        boolean fileRead = false;
        Path path = null;
        boolean dez = false;

        for(int i = 0; i < args.length; i++){
            if(args[i].equals("--help") || args[i].equals("-h")){
                printHelpMessage();
                return;
            }
            if(args[i].equals("-programming") || args[i].equals("-p")){
                programing = args[++i];
            }
            if(args[i].equals("--input") || args[i].equals("-i")){
                manualInput = true;
            }
            if(args[i].equals("--step") || args[i].equals("-s")){
                step = true;
            }
            if(args[i].equals("--file") || args[i].equals("-f")){
                path= Path.of(args[++i]);
                fileRead = true;
            }
            if(args[i].equals("--dezimal-number") || args[i].equals("-d")){
                dez = true;
            }
        }


        if(programing == null && !fileRead){
            throw new IllegalArgumentException("No program specified");
        }
        if(dez && !fileRead){
            programing = new BigInteger(programing).toString(2);
            System.out.println(programing);
        }
        if(fileRead){
            String line;
            String fileInput = "";
            try(BufferedReader bufferedReader = Files.newBufferedReader(path, CHARSET);){
                System.out.println("here");
                while((line = bufferedReader.readLine()) != null){
                    fileInput += line;
                    System.out.println(line);
                    if(line == null){
                        throw new IllegalArgumentException("input not found");
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if(dez){
                fileInput = new BigInteger(fileInput).toString(2);
            }

            run(step,fileInput);

        }else if(manualInput){
            run(step,programing);
        } else {
            new  UniversalTuringMacheneEmulator(programing).run(step);
        }
    }

    private static void run(Boolean step, String programing){
        UniversalTuringMacheneEmulator emulator = new UniversalTuringMacheneEmulator(programing);
        String input;
        Scanner sc = new Scanner(System.in);
        do{
            System.out.print("Enter manualInput: ");
            input = sc.nextLine();
            if(input.matches("(-|[0-1])+")){
                emulator.run(step, input);
            }else if("step true".equals(input)){
                step = true;
            }else if("step false".equals(input)){
                step = false;
            }else if("help".equals(input)){
                printHelpMessage();
            }
        } while(!"q".equals(input));
    }
}
