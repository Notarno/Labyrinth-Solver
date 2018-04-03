/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Martin Lang
 */
public class Edge {
    private Node x;
    private Node y;
    private String t;
    
    //Constructor for the Edge Class
    public Edge(Node u, Node v, String type){
        x = u;
        y = v;
        t = type;
    }
    //Returns the first end point of the Edge object
    public Node firstEndpoint(){
        return x;
    }
    //Returns the second end point of the Edge object
    public Node secondEndpoint(){
        return y;
    }
    //Returns the type of the Edge object
    public String getType(){
        return t;
    }
    //Sets the type of the Edge Object
    public void setType(String type){
        t = type;
    }
}
