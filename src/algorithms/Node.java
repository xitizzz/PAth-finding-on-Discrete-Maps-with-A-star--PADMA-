/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithms;

//import java.io.OutputStream;
import java.util.ArrayList;

/**
 *
 * @author xitizzz
 */
public class Node{
    
    public int x, y;
    public double f, g, h=0;       
    public String status;
    public Node parent;
    public boolean closed=false, isStart=false, isGoal=false, isExpanded=false, isExplored=false;
    
    private static final int COLUMN = 160, ROW = 120;
    private static final int[][] DIR = {{0,-1},{1,-1},{1,0},{1,1},{0,1},{-1,1},{-1,0},{-1,-1}};
    
    public Node(){
    
    }
    
    public Node(int x, int y){
        this.x = x;
        this.y = y;
    }
    
    public boolean isEqual(Node s){
        if (this.x!=s.x || this.y!=s.y) {
        } 
        else {
            return true;
        }
        return false;
    }
    
    public ArrayList<Node> getSuccessor(){
        ArrayList<Node> neighbors = new ArrayList<>(8);
        for(int i=0;i<8;i++){
            if(this.x+DIR[i][0]<COLUMN && this.x+DIR[i][0]>=0 && this.y+DIR[i][1]<ROW && this.y+DIR[i][1]>=0)
                neighbors.add(new Node(this.x+DIR[i][0],this.y+DIR[i][1]));   
        }
        return neighbors;
    }
    
    public void printNode(){
        System.out.println("("+this.x+","+this.y+")");    
    }
}
    
