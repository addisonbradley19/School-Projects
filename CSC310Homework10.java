/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//course: CSC310
//project name: Hw10
//date: 12/6/2016
//author: Addison Bradley
//purpose: This program will implement Kruskal's Algorithim and provide a minimum spanning tree from those efforts
import java.util.Scanner;
public class CSC310Homework10 {
    static class Edge{              //class to represent the edges conncting the graph
        int beg, end, weight;            //includes the beginning vertex(bg), the ending vertix(vx), and the weight of the edge
        
        Edge(int bg, int ed, int w){      //input portion
            beg = bg;
            end = ed;
            weight = w;
        }
    }
    
    static class Set{           //class to represent disjoint sets or Union Find sets do to their most popular functions. this includes the rank of each dijoint set(number of subsets within them, always starts out at one because they always have at least one subset which is itself)
        int root, rank;           //they also have a value that represents the subset that each disjoint set lives in. Starts off with itself because in the beginning you can't get higher than yourself. For this program we called this root because we wanted to find the number higher than it. It does not represent the absolute root in some cases but it fits in quite a few cases and it it was the first name I thought of.
        
        Set(int numIt){             //this style of set is called Tree Based Disjoint Sets and learned about it while watching guides on Youtube. I decided to use it because it seemed the most simple to me and is similar to other tree based systems from previous assignments this year
            root = numIt;
            rank = 1;
        }
    }
    
   static class setCollection{          //class set collection is pretty much the array of all disjoint sets and to have place where the program can use Find and Union with other disjoint sets 
        
        Set subset[];               //considering the user will enter the vertix names and then we convert these into numbers, we can just use placement in the array to also represent a set
        
        int S;                      //the number of sets in setCollection, mainly used when deciding an ending point for for loops
        
        setCollection(){
           subset = new Set[20];            //when intializing a setCollection you get an empty array and an S of 0 because we currently have no sets within setCollection
           S = 0;
        }
        
        int Find(int n1){               //find: search for the root of the submitted set or find the highest set that the currently submitted set is a part of 
            while(subset[n1].root != n1){     //while the root of the set is not equal to itself(which indicates we have found the top root this set)
                n1 = subset[n1].root;         //set the number n1 to the root of n1 and go through
            }
            return subset[n1].root;         //once you have found the root you may now return said number
        }

        void Union(int n1, int n2){     //this program is used for combining two subsets togeither
            int r1 = Find(n1);              //int r1 equals the root of n1     
            int r2 = Find(n2);              //int r2 equals the root of n2 

            if(subset[r1].rank  < subset[r2].rank){         //if the set r2 has a higher rank (more subsets) than r1
                subset[r1].root = r2;                       //then the root of r1 is set to r2
                subset[r2].rank = subset[r2].rank + subset[r1].rank;        //the rank of r2 increase by the rank of r2 plus itself because we are adding all the subsets of r1 when we are working on r2
            }
            else if(subset[r1].rank > subset[r2].rank){         //if the opposite happens, do the opposite
                subset[r2].root = r1;
                subset[r1].rank = subset[r1].rank + subset[r2].rank;
            }
            else{           //if both are the exact same rank then configure r2 so that it's root is r1 and increase r1's rank because we are now making r2 a consideration of how many subsets are in r1
                subset[r2].root = r1;
                subset[r1].rank = subset[r1].rank + subset[r2].rank;
            }
        }
    }

    
    static class Graph{         //class to represent the graph itself
        Edge edges[] = new Edge[25];           //array of edges kept by the program
        String vert[] = new String[25];          //array of string to represent the cometic representation the user selected vertices
        setCollection sC = new setCollection();       //setCollection to hold all possible sets
        int V, E;                   //number of vertices and number of edges
        
        Graph(){
            V = 0;          //no vertices when intializing
            E = 0;          //no edges when intializing
        }
        
        void createGraph(){             //this program will be used help create a graph
            Scanner in = new Scanner(System.in);        //this will read the users list of vertices
            System.out.println("Input vertices (all the vertices will be numbered based on the input order, starting from 0): ");
            String vex = in.nextLine();
            
            if(vex.lastIndexOf(",") == -1){    //this seperates whether the user used spaces or commas   
                int origStop = vex.length() - vex.lastIndexOf(" ");    //this will represent the stopping point for how far to go within the     
                while(vex.length() > origStop){                                                
                    String st = vex.substring(0, vex.indexOf(" "));                          
                    vert[V] = st;               //no matter the case, we add the vertix name into the vert
                    Set temp = new Set(V);
                    sC.subset[V] = temp;  // we add an actual set into sC to represent the new possible vertice to keep track of
                    sC.S++;
                    V++;                        //increase the the number of vertices
                    vex = vex.substring(vex.indexOf(" ")+1, vex.length());      //and create a substring to decrement throught the user's list of vertixes
                }
            }
            else{           
                int origStop = vex.length() - vex.lastIndexOf(",");         
                while(vex.length() > origStop){                           
                    String st = vex.substring(0, vex.indexOf(","));         
                    vert[V] = st;
                    Set temp = new Set(V);
                    sC.subset[V] = temp;
                    sC.S++;
                    V++;
                    vex = vex.substring(vex.indexOf(",")+1 ,vex.length());
                }
            }
            String last = vex;          //this represents the last vertix left to be implemented due to how much we cut from the list
            vert[V] = last;
            Set T = new Set(V);
            sC.subset[V] = T;
            sC.S++;
            V++;
            
          
            String ed;      //this for the inputs for edge
            System.out.println("Do you wish to add edge. Enter 1 to add an edge. Enter 2 to stop adding edges");            //this prompt will ask the user to either enter edges or stop adding edges. All this leads to a switch loop
            do{
                System.out.println("Do you wish to add edge. Enter 1 to add an edge. Enter 2 to stop adding edges");
                System.out.println("Choose: ");
                ed = in.nextLine();
            
                switch(ed.charAt(0)) {
                    case '1':           //choosing 1 sends the user to add edge which adds a single edge each time
                        addEdge();
                        break;
                    case '2':        
                        System.out.println("Bye!");             //pressing 2 exits out of this switch       
                        break;        
                    default:
                        System.out.println("invalid! try it again.");           
                }
            }while(ed.charAt(0) != '2');
            
        }
        
        void printKruskals(){
            int EK = 0;         //represents the number of edges in MST[], which are the edges important to Kruskal's Algorithim
            int KE = 0;         //KE will be used to represent traversal through edge later in this subclass
            Edge MST[] = new Edge[V];       //this array holds all edges that are used in the Minimum Spanning Tree
            for (int i = 0; i < E-1; i++){     //this bubble sort will sort edges[] by weight in increasing order
                for (int j = 0; j < E-i-1; j++){ 
                    if (edges[j].weight > edges[j+1].weight){
                        Edge temp = edges[j];
                        edges[j] = edges[j+1];
                        edges[j+1] = temp;
                    }
                }
            }
            
            while(EK < V-1){            //until the number of edges in MST is up to one less than the number vertices which is the number of edges in miminmum spanning tree
                Edge op = edges[KE];   //Edge will be our temp to examined if it will belong to the Minimum spanning tree
                KE++;                   //increase KE because later we wish to examine other edges to add to the tree
                
                int n1 = sC.Find(op.beg);           //find the root of vertix 1
                int n2 = sC.Find(op.end);           //find the root of vertix 2
                
                if(n1 != n2){                   //if the don't have the same root (meaning there are not part of the same larger subset)
                    MST[EK] = op;               //op is added to the edges of the minimum spanning tree
                    EK++;                       //the number of edges in minimum spanning tree increases
                    sC.Union(n1, n2);           //both vertices are added to the same set
                }
            }
            
            System.out.print(" " + " ");        //intial spacing to create y axis
            
            for(int i = 0; i < V; i++){
                System.out.print(" " + " " + " " + vert[i]);        //create y-axis with the vertix symbols
            }
            
            
            System.out.println();           //seperate from the x-axis
            boolean value = false;          //boolean for determining if there is an actual value at edge
            for(int i = 0; i < V; i++){         //go through each vertix individually
                System.out.print(vert[i]);      //
                for(int j = 0; j < V; j++){
                    for(int e = 0; e < EK; e++){
                        if((MST[e].beg == i && MST[e].end == j)||(MST[e].beg == j && MST[e].end == i)){         //if any of the current vertices combinations match the edges in MST
                            System.out.printf("%3d", MST[e].weight);        //there weight is printed
                            value = true;                               //value is set to true due to acually adding value
                        }
                    }
                    if(value != true){          //if an edge was not found for this combination of vertices
                       System.out.printf("%3d", 0);     //print a zero
                    }
                    else{
                        value = false;      //if true, reset back to false
                    }
                }
                System.out.println();       //go to the next line of the vertices
            }
            
        } 
        
        
        void addVertices(String s){         //takes the users string
            vert[V] = s;                    //adds the name to the list of vertix names
            Set temp = new Set(V);              //temp that will represent the users input
            sC.subset[V] = temp;      //adds a set that will represent it in the set collection array
            sC.S++;                   //number of sets in setCollection increases
            V++;                            //increases the current number vertix
        }
        
        
        void addEdge(){
            Scanner inp = new Scanner(System.in);               //scanner for users to enter there edge
            System.out.println("Input  edges  with  the format  of  (number_vertex1,  number_vertex2, weight): ");      //tells users how to input the edge example: (0, 1, 20) 
            String edge = inp.nextLine();       //records the users inputted string ....    
            
            String bg = edge.substring(1,edge.indexOf(","));            //...and prepares to break apart the input to get the first vertix, second vertix, and weight
            String ed = edge.substring(edge.indexOf(",")+2, edge.lastIndexOf(","));
            String weight = edge.substring(edge.lastIndexOf(",")+2, edge.length()-1);
            Edge temp = new Edge(Integer.parseInt(bg), Integer.parseInt(ed), Integer.parseInt(weight)); //a new edge is created from the parsed substrings formed from the users input
            edges[E] = temp;  //said edge is then added to the edge array
            E++;             //number of edges increases
        }
        
    }
    
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);         //scanner for user input
        Graph g = new Graph();                          //graph to be used in this program
        String res;                                     //represent the user input
        String ve;
        do{
            System.out.println("1. Build a graph: ");
            System.out.println("2. Print adjacency matrix of the minimum spanning tree: ");
            System.out.println("3. Insert Vertix: ");
            System.out.println("4. Insert an edge: ");
            System.out.println("5. Exit: ");
            System.out.println("Choose: ");
            res = input.nextLine();
            
            switch(res.charAt(0)) {
                case '1':
                    g.createGraph();        //takes the user to the location to create their graph                                         
                    break;
                case '2':
                    g.printKruskals();              //prints an adjancency martix of the minimum spanning tree
                    break;        
                case '3':
                    System.out.println("Input a vertex");           //adds the user vertix
                    ve = input.nextLine();
                    g.addVertices(ve);
                    break;
                case '4':
                    g.addEdge();                                //adds an edge
                    break;
                case '5':
                    System.out.println("Bye!");             //exit clas       
                    break;        
                default:
                    System.out.println("invalid! try it again.");           
            }
        } while (res.charAt(0) != '5');
        
    }
    
}
