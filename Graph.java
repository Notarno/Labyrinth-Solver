
/**
 *
 * @author Martin Lang
 */
import java.util.Iterator;
import java.util.ArrayList;

public class Graph implements GraphADT{
    private Node[] vertices;
    private ArrayList<Edge>[] edges;
    private int numOfVerts;
    
    //Constructor for the Graph class
    public Graph(int n){
        //Initializing all vertices to values of 0,1,2....,n-1
        //Initializing empty ArrayList for edges
        numOfVerts = n;
        vertices = new Node[numOfVerts];
        edges = new ArrayList[numOfVerts];
        
        for(int i = 0; i < n; i++){
            vertices[i] = new Node(i);
        }
        
        for(int i = 0; i < n; i++){
            edges[i] = new ArrayList<Edge>();
        }
    }
    //Inserts an edge into the graph for the following node(s)
    @Override
    public void insertEdge(Node nodeu, Node nodev, String edgeType) throws GraphException {
        //Check to see if node(s) are in the graph
        //If they aren't throw an exception
        if(nodeu.getName() > numOfVerts - 1 || nodev.getName() > numOfVerts - 1){
            throw new GraphException("The following Node(s) can not be found");
        }
        //If nodes are in the graph, then add an edge accordingly
        if(nodeu.getName() < numOfVerts){
            for(int i = 0; i < edges[nodeu.getName()].size(); i++){
                if(edges[nodeu.getName()].isEmpty()){
                    break;
                }
                //Throws an exception if an edge already exists between the nodes
                if(edges[nodeu.getName()].get(i).firstEndpoint() == nodeu && edges[nodeu.getName()].get(i).secondEndpoint() == nodev){
                    throw new GraphException("Edge already exists");
                }
            }
        }
       //Add an edge between the nodes
        Edge temp1 = new Edge(nodeu, nodev, edgeType);
        Edge temp2 = new Edge(nodev, nodeu, edgeType);
        edges[nodeu.getName()].add(temp1);
        edges[nodev.getName()].add(temp2);
    }
    //Returns the node with the indicated name
    @Override
    public Node getNode(int name) throws GraphException {
        //Checks to see if any node has the following name
        if(name >= numOfVerts){
            throw new GraphException("No node with this name exists");
        }
        else{
            return this.vertices[name];
        }
    }
    //Iterator method to find incident edges
    @Override
    public Iterator incidentEdges(Node u) throws GraphException {
        //Checks to see if Node is in the graph
        if(u.getName() > numOfVerts - 1 || u.getName() < 0){
            throw new GraphException("The following Node does not exist");
        }
        
        return edges[u.getName()].iterator();
    }
    //Returns the edge between the indicated nodes
    @Override
    public Edge getEdge(Node u, Node v) throws GraphException {
        //Checks to see if Node(s) are in the graph
        if(u.getName() > numOfVerts - 1 || v.getName() > numOfVerts - 1 || u.getName() < 0 || v.getName() < 0){
            throw new GraphException("The following Node(s) can not be found");
        }
        //If Node(s) are in the in the graph, check to see if an edge exists between them
        if(!(edges[u.getName()].isEmpty())){
            for(int i = 0; i < edges[u.getName()].size(); i++){
                //If edge doesn't exist throw exception
                //Otherwise return that found edge
                if(edges[u.getName()].get(0).firstEndpoint() == null){
                    throw new GraphException("There is no edge between these nodes");
                }
                if(edges[u.getName()].get(i).firstEndpoint() == u && edges[u.getName()].get(i).secondEndpoint() == v){
                    return edges[u.getName()].get(i);
                }
            }
        }
        
        throw new GraphException("There is no edge between these nodes");
    }
    //Checks to see if indicated nodes are adjacent
    @Override
    public boolean areAdjacent(Node u, Node v) throws GraphException {
        //Checks to see if Node(s) are in the graph
        if(u.getName() > numOfVerts - 1 || v.getName() > numOfVerts - 1 || u.getName() < 0 || v.getName() < 0){
            throw new GraphException("The following Node(s) can not be found");
        }
        
        try{
            if(this.getEdge(u, v) != null){
                return true;
            }
        }catch(GraphException e){
            System.out.println(e);
        }
        
        return false;
    }
}
