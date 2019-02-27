

/**
 *
 * @author Martin Lang
 */
import java.util.*;
import java.io.*;

public class Labyrinth {
    private int Bbomb;
    private int Abomb;
    private int entrance;
    private int exit; 
    private int width;
    private int length;
    private Graph lab;
    private Stack<Node> path;
   
    
    //Constructor for Labyrinth Class
    public Labyrinth(String inputFile) throws LabyrinthException{
        path = new Stack<Node>();
        
        BufferedReader input;
        String line;
        Iterator<String> it;
        Vector<String> vec = new Vector<String>();
        //Reads the input file and stores the String values
        //in a vector variable
        try{
            input = new BufferedReader(new FileReader(inputFile));
            line = input.readLine();
            
            while(line != null){
                vec.add(line);
                line = input.readLine();
            }
            
            input.close();
            it = vec.iterator();
        }catch(Exception e){
            throw new LabyrinthException(e.getMessage());
        }
        //Checks each character in the input file
        //and inserts the necessary edges to the labyrinth graph
        try{
            //scale variable is declared but not used
            int scale = Integer.parseInt(it.next());
            width = Integer.parseInt(it.next());
            length = Integer.parseInt(it.next());
            Bbomb = Integer.parseInt(it.next());
            Abomb = Integer.parseInt(it.next());
            lab = new Graph(length*width);
            
            char[] charArray;
            char charCheck;
            
            int temp0;
            int temp1;
            
            for(int i = 0; i <= ((2*length) - 2); i++){
                charArray = it.next().toCharArray();
                
                for(int j = 0; j <= ((2*width) - 2); j++){
                    charCheck = charArray[j];
                    //checks for exit node
                    if(charCheck == 'x'){
                        exit = (((width *i) / 2) + (j / 2));
		    }
                    //checks for entrance node
                    else if(charCheck == 'b'){
                        entrance = (((width * i) / 2) + (j / 2));
                    }
                    //checks for horizontal walls/corridors
                    else if(charCheck == 'h' || charCheck == 'H' || charCheck == 'm' || charCheck == '-'){
                        temp0 = (((width * i) / 2) + ((j - 1) / 2));
			temp1 = (((width * i) / 2) + ((j + 1) / 2));
							
                        if(charCheck == '-'){
                            lab.insertEdge(lab.getNode(temp0), lab.getNode(temp1), "corridor");
                        }
			else if(charCheck == 'm'){
                            lab.insertEdge(lab.getNode(temp0), lab.getNode(temp1), "metalWall");
			}				
			else if(charCheck == 'H'){
                            lab.insertEdge(lab.getNode(temp0), lab.getNode(temp1), "thickWall");
			}				
                        else{
                            lab.insertEdge(lab.getNode(temp0), lab.getNode(temp1), "wall");					
			}
                    }
                    //checks for vertical walls/corridors
                    else if(charCheck == 'v' || charCheck == 'V' || charCheck == 'M' || charCheck == '|'){
                        temp0 = (((width * (i - 1)) / 2) + (j / 2));
			temp1 = (((width * (i + 1)) / 2) + (j / 2));
							
                        if(charCheck == '|'){
                            lab.insertEdge(lab.getNode(temp0), lab.getNode(temp1), "corridor");
			}				
			else if(charCheck == 'M'){
                            lab.insertEdge(lab.getNode(temp0), lab.getNode(temp1), "metalWall");
			}				
			else if(charCheck == 'V'){
                            lab.insertEdge(lab.getNode(temp0), lab.getNode(temp1), "thickWall");
                        }				
			else{
                            lab.insertEdge(lab.getNode(temp0), lab.getNode(temp1), "wall");
			}
                    }
                }
            }
        }catch(Exception e){
            throw new LabyrinthException(e.getMessage());
        }
    }
    //Returns the labyrinth graph
    public Graph getGraph() throws LabyrinthException{
        //Check to see if the graph is defined
        if(lab == null){
            throw new LabyrinthException("Graph isn't defined");
        }
        else{
            return lab;
        }
    }
    //Iterator method to solve the labyrinth
    public Iterator<Node> solve(){
        try{
            for(int i = 0; i < width*length; i++){
                    lab.getNode(i).setMark(false);
            }
                //If a pathway exists return the path,
                //Otherwise return null
                if(findPath(lab.getNode(entrance), lab.getNode(exit))){
                    System.out.println("There exists a pathway");
                    return path.iterator();
                }
                else{
                    return null;
                }
        }
        catch (Exception e) {
            System.out.println(e);
            return null;
        }        
    }
    //Private method used to find a path between the entrance
    //and exit of the labyrinth
    private boolean findPath(Node u, Node v){
        u.setMark(true);
        path.push(u);
        
        if(u.getName() == v.getName()){
            return true;
        }
        
        try{
            Edge tempEdge;
            Node tempNode = null;
            Iterator<Edge> edgeIt = lab.incidentEdges(u);
            //Loop path finding algorithm untit path is found or
            //when no more iterator variable edgeIt has no more edges
            //to be checked
            while(edgeIt.hasNext()){
                tempEdge = edgeIt.next();
                
                if(u.getName() == tempEdge.firstEndpoint().getName()){
                    tempNode = tempEdge.secondEndpoint();
                }
                else if(u.getName() == tempEdge.secondEndpoint().getName()){
                    tempNode = tempEdge.firstEndpoint();
                }
                //For each edge in the path, check the type
                //and perform actions based on the type of that edge
                if(!(tempNode.getMark())){
                    if("metalWall".equals(tempEdge.getType())){
                        if(Abomb > 0){
                            Abomb--;
                            if(findPath(tempNode, v)){
                                return true;
                            }
                            else{
                                Abomb++;
                            }
                        }
                    }
                    else if("thickWall".equals(tempEdge.getType())){
                        if(Bbomb > 1){
                            Bbomb = Bbomb - 2;
                            if(findPath(tempNode, v)){
                                return true;
                            }
                            else{
                                Bbomb = Bbomb + 2;
                            }
                        }
                    }
                    else if("corridor".equals(tempEdge.getType())){
                        if(findPath(tempNode, v)){
                            return true;
                        }
                    }
                    else if("wall".equals(tempEdge.getType())){
                        if(Bbomb > 0){
                            Bbomb--;
                            if(findPath(tempNode, v)){
                                return true;
                            }
                            else{
                                Bbomb++;
                            }
                        }
                    }
                }
            }
            
            path.pop();
            u.setMark(false);
            return false;
        }catch(GraphException e){
            System.out.println(e);
            return false;
        }
    }
}
