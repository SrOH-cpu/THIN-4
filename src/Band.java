public class Band {

    private Node head;
    private Node start;
    private Node end;
    private int headpos = 0;

    public Band(String input){
        String[] inputArray = input.split("");
        if(inputArray.length ==0){
            start = new Node(" ");
        } else{
            start = new Node(inputArray[0]);
        }
        head = start;
        for(int i = 1; i < inputArray.length; i++){
            Node temp = new Node(inputArray[i]);
            Node tempPrev = head;
            head.setNext(temp);
            head = temp;
            head.setPrev(tempPrev);
        }
        end = head;
        head = start;
    }

    public void moveRight(){
        if(head.getNext() == null){
            head.setNext(new Node("-"));
            end = head.getNext();
            end.setPrev(head);
        }
        headpos++;
        head = head.getNext();

    }

    public void moveLeft(){
        if(head.getPrev() == null){
            head.setPrev(new Node("-"));
            start = head.getPrev();
            start.setNext(head);
            headpos = 0;
        } else{
            headpos--;
        }
        head = head.getPrev();
    }

    public String read(){
        return head.getContent();
    }

    public void write(String input){
        head.setContent(input);
    }

    public int getHeadpos() {
        return headpos;
    }

    @Override
    public String toString(){
        String output = start.getContent();
        Node temp = start;
        while(temp.hasNext()){
            temp = temp.getNext();
            output += temp.getContent();
        }
        return output;
    }



    private class Node{
        private Node next = null;
        private Node prev = null;
        private String content;

        private Node(String content){
            this.content = content;
        }

        private String getContent(){
            return content;
        }

        private void setContent(String  content){
            this.content = content;
        }

        private Node getNext() {
            return next;
        }
        private void setNext(Node next) {
            this.next = next;
        }
        private Node getPrev() {
            return prev;
        }
        private void setPrev(Node prev) {
            this.prev = prev;
        }
        private boolean hasNext(){
            return next != null;
        }
        private boolean hasPrev(){
            return prev != null;
        }
    }
}
