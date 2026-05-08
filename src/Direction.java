public enum Direction{
    LEFT,
    RIGHT;

    public static Direction getDirection(String s){
        s = s.trim();
        if("00".equals(s)){
            return RIGHT;
        }else if("0".equals(s)){
            return LEFT;
        }else{
            throw new IllegalArgumentException("Invalid Direction: " + s);
        }
    }
}