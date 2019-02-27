

/**
 *
 * @author Martin Lang
 */
public class Node {
    private int n;
    private boolean m;
    
    //Constructor for Node class
    public Node(int name){
        n = name;
    }
    //Sets the mark of the node object
    public void setMark(boolean mark){
        m = mark;
    }
    //Returns the mark of the node object
    public boolean getMark(){
        return m;
    }
    //Returns the name of the node object
    public int getName(){
        return n;
    }
}
