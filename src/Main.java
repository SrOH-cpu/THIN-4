import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class Main{


    public static void main(String[] args)
    {
        Charset CHARSET = StandardCharsets.UTF_8;
        String programing = null;
        boolean manualInput = false;
        boolean step = false;
        boolean fileRead = false;
        Path path = null;

        for(int i = 0; i < args.length; i++){
            if(args[i].equals("--help") || args[i].equals("-h")){
                System.out.println("nothing");
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
                manualInput = true;
            }
        }

        if(programing == null && !fileRead){
            throw new IllegalArgumentException("No program specified");
        }
        if(fileRead){
            try(BufferedReader bufferedReader = Files.newBufferedReader(path, CHARSET);){
                String line;
                line = bufferedReader.readLine();
                run(step,line);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

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
            if(input.matches("[0-1]+")){
                emulator.run(step, input);
            }else if("step true".equals(input)){
                step = true;
            }else if("step false".equals(input)){
                step = false;
            }
        } while(!"q".equals(input));
    }
}
