public class StateChanges {
    private int stateOne;
    private int stateTwo;
    private String read;
    private String write;
    private Direction direction;

    public StateChanges(int stateOne, String read, int stateTwo, String write, Direction direction){
        this.stateOne = stateOne;
        if("2".equals(read)){
            this.read = " ";
        } else if(Integer.parseInt(read)>2){
            this.read = Integer.toString(Integer.parseInt(read)-1);
        } else{
            this.read = read;
        }

        this.stateTwo = stateTwo;
        if("2".equals(write)){
            this.write = " ";
        } else if(Integer.parseInt(write)>2){
            this.write = Integer.toString(Integer.parseInt(write)-1);
        } else{
            this.write = write;
        }
        this.direction = direction;
    }

    public int stateOne(){
        return stateOne;
    }
    public int stateTwo(){
        return stateTwo;
    }
    public String read(){
        return read;
    }
    public String write(){
        return write;
    }
    public Direction direction(){
        return direction;
    }

    @Override
    public String toString() {
        return "(q"+stateOne + " " + read + ") --> (q" + stateTwo + " " + write + " " + direction+")";
    }
}
