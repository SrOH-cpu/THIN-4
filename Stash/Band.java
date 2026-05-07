import java.util.Optional;

public class Band {

    private Node head;


    public Band(String input){
        for(String c : input.split("")){
            Node temp = new Node(Integer.parseInt(c));
            temp.setPrev(head);
            if(head != null){
                head.setNext(temp);
            }
            head = temp;
        }
    }

    public void moveForward(){
        if(head.getNext() == null){
            head.setNext(new Node(0));
        }
        head = head.getNext();
    }

    public void moveBackward(){
        if(head.getPrev() == null){
            head.setPrev(new Node(0));
        }
        head = head.getPrev();
    }

    public int read(){
        return head.getContent();
    }

    public void write(int input){
        head.setContent(input);
    }



    private class Node{
        private Node next;
        private Node prev;
        private int content;

        private Node(int content){
            this.content = content;
        }

        private int getContent(){
            return content;
        }

        private void setContent(int content){
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
    }
}
