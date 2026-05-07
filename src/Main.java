import java.util.Scanner;

public class Main{
    public static void main(String[] args)
    {
        String programing = null;
        UniversalTuringMacheneEmulator emulator;
        boolean manualInput = false;
        boolean step = false;

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
        }

        if(programing == null){
            throw new IllegalArgumentException("No program specified");
        }
        if(manualInput){
            emulator = new UniversalTuringMacheneEmulator(programing);
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
        } else {
            new  UniversalTuringMacheneEmulator(programing).run(step);
        }
    }
}
