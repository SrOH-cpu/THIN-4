import java.util.*;

public class UniversalTuringMacheneEmulator {

    private String[] inputBand = new String[]{" "};
    private int startState = 1;
    private int currentState;
    private final List<StateChanges> stateChangesList = new ArrayList<StateChanges>();
    private int headPosition;
    private Band band = new Band(" ");

    public UniversalTuringMacheneEmulator(String input) {
        String programming = input.split("111")[0];
        if(input.split("111").length>1){
            this.inputBand = input.split("111")[1].split("");
            band = new Band(input.split("111")[1]);
        }

        if(programming.startsWith("1")){
            programming = programming.substring(1);
        }
        if(programming.endsWith("1")){
            programming = programming.substring(0, programming.length()-1);
        }
        String[] stateChanges = programming.split("11");
        currentState = startState;
        for(String stateChange : stateChanges){
            stateChangesList.add(new StateChanges(stateChange.split("1")[0].length(),Integer.toString(stateChange.split("1")[1].length()-1),stateChange.split("1")[2].length(),Integer.toString(stateChange.split("1")[3].length()-1), Direction.getDirection(stateChange.split("1")[4])));
        }
        System.out.println("Currently loaded Turing machine");
        for(StateChanges stateChange : stateChangesList){
            System.out.println(stateChange.toString());
        }
    }

    public void run(boolean steps, String input){
        band = new Band(input);
        currentState = startState;
        run(steps);
    }

    public void run(boolean steps){
        int stepCounter = 0;
        if(steps){
            System.out.println(stepCounter + ": " + printCalculation());
        }
        while (step()){
            stepCounter++;
            if(steps){
                System.out.println(stepCounter + ": " + printCalculation());
            }

        }
        String result = "declined";
        if(currentState==2) {
            result = "accepted";
        }

        System.out.println("Steps: "+ stepCounter +" Result:" + result + " Band: " + printCalculation());

    }

    private String printCalculation() {
        List<String> outputList = new ArrayList<>(Arrays.asList(band.toString().split("")));
        outputList.add(band.getHeadpos(),"(q"+currentState+")");
        for(int i = outputList.size(); i< band.getHeadpos()+15 ;i++){
            outputList.addLast("-");
        }
        for(int i = band.getHeadpos()-15; i<0 ;i++){
            outputList.addFirst("-");
        }
        return "["+String.join("", outputList) + "]";
    }


    public boolean step(){
        for(StateChanges stateChange : stateChangesList){
            if(stateChange.stateOne() == currentState && stateChange.read().equals(band.read())){
                band.write(stateChange.write());
                currentState = stateChange.stateTwo();
                if(stateChange.direction() == Direction.RIGHT){
                    band.moveRight();
                }if(stateChange.direction() == Direction.LEFT){
                    band.moveLeft();
                }
                return true;
            }
        }
        return false;
    }

    public void runString(boolean steps, String input) {
        inputBand = input.split("");
        band = new Band(input);
        runString(steps);
    }

    public void runString(boolean steps){
        int stepCounter = 0;
        try{
            if(steps){
                System.out.println(stepCounter + ": " + printCalculationString());
            }
            while (stepString()){
                stepCounter++;
                if(steps){
                    System.out.println(stepCounter + ": " + printCalculationString());
                }

            } ;
        }catch(Exception e){
            e.printStackTrace();
        }

        String result = "declined";
        if(currentState==2) {
            result = "accepted";
        }

        System.out.println("Steps: "+ stepCounter +" Result:" + result + " Band: " + printCalculationString());

    }

    public boolean stepString(){
        System.out.println("Current State: " + Arrays.toString(inputBand));
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
        if(inputBand.length>headPosition){
            String temp = Arrays.toString(inputBand).replace("[","").replace("]","").replace(" ","");
            temp = temp + "-";
            inputBand = temp.split("");
        }
        headPosition++;
    }
    public void moveLeft(){
        headPosition--;
        if(headPosition<0){
            headPosition = 0;
            String temp = Arrays.toString(inputBand).replace("[","").replace("]","").replace(" ","");
            temp = "-" + temp;
            inputBand = temp.split("");
        }
    }


    private String printCalculationString(){
        List<String> outputList = new ArrayList<>(Arrays.asList(inputBand));
        outputList.add(headPosition,"(q"+currentState+")");
        for(int i = outputList.size(); i<headPosition+15 ;i++){
            outputList.addLast("-");
        }
        for(int i = headPosition-15; i<0 ;i++){
            outputList.addFirst("-");
        }


        return "["+String.join("", outputList) + "]";
    }
}
