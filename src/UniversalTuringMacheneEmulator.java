import java.util.*;

public class UniversalTuringMacheneEmulator {

    private String[] inputBand = new String[]{" "};
    private int currentState;
    private final List<StateChanges> stateChangesList = new ArrayList<StateChanges>();
    private int headPosition;

    public UniversalTuringMacheneEmulator(String input) {
        String programming = input.split("111")[0];
        if(input.split("111").length>1){
            this.inputBand = input.split("111")[1].split("");
        }

        String[] stateChanges = programming.split("11");
        currentState = stateChanges[0].split("1")[0].length();
        for(String stateChange : stateChanges){
            stateChangesList.add(new StateChanges(stateChange.split("1")[0].length(),Integer.toString(stateChange.split("1")[1].length()-1),stateChange.split("1")[2].length(),Integer.toString(stateChange.split("1")[3].length()-1), Direction.getDirection(stateChange.split("1")[4])));
        }
        System.out.println("Currently loaded Turing machine");
        for(StateChanges stateChange : stateChangesList){
            System.out.println(stateChange.toString());
        }
    }

    public void run(boolean steps, String input) {
        inputBand = input.split("");
        run(steps);
    }

    public void run(boolean steps){
        int stepCounter = 0;
        try{
            if(steps){
                System.out.println(stepCounter + ": " + printCalculation());
            }
            while (step()){
                stepCounter++;
                if(steps){
                    System.out.println(stepCounter + ": " + printCalculation());
                }

            } ;
        }catch(Exception e){
            e.printStackTrace();
        }

        String result = "declined";
        if(currentState==2) {
            result = "accepted";
        }

        System.out.println("Steps: "+ stepCounter +" Result:" + result + " Band: " + printCalculation());

    }

    public boolean step(){

        for(StateChanges stateChange : stateChangesList){
            if(stateChange.stateOne() == currentState && stateChange.read().equals(inputBand[headPosition])){
                inputBand[headPosition] =  stateChange.write();
                currentState = stateChange.stateTwo();
                if(stateChange.direction() == Direction.RIGHT){
                    moveRight();
                }if(stateChange.direction() == Direction.LEFT){
                    moveLeft();
                }
                return true;
            }
        }
        return false;
    }

    public void moveRight(){
        if(inputBand.length<headPosition){
            String temp = Arrays.toString(inputBand);
            temp = temp + " ";
            inputBand = temp.split("");
        }
        headPosition++;
    }
    public void moveLeft(){
        headPosition--;
        if(headPosition<0){
            headPosition = 0;
            String temp = Arrays.toString(inputBand);
            temp = " " + temp;
            inputBand = temp.split("");
        }
    }


    private String printCalculation(){
        List<String> outputList = new ArrayList<>(Arrays.asList(inputBand));
        outputList.add(headPosition,"(q"+currentState+")");
        for(int i = outputList.size(); i<headPosition+15 ;i++){
            outputList.addLast(" ");
        }
        for(int i = headPosition-15; i<0 ;i++){
            outputList.addFirst(" ");
        }


        return "["+String.join("", outputList) + "]";
    }
}
