/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package algorithms;


import algorithms.fringe.Fringe;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;
import map.*;
import path.PathWriter;


/**
 *
 * @author xitizzz
 */

public class Astar {
    
    private static final int WIDTH = 160, HEIGHT = 120;
    
    Fringe fringe;
    MapParser mapParser;
    Node start, goal;
    Heuristics heuristics;
    
    ArrayList<Node> successor;
    Stack<Node> path;
    Node[][] map = new Node[HEIGHT][WIDTH];  
    
    public int expandedNodeCount=0, exploredNodeCount=0;
    String outputFilePath;
    double weight;
    int heuristicCode;
    
    
    public Astar(String InputfilePath, String outputFilePath, int heuristicCode, double weight) throws IOException{
        
        fringe = new Fringe();
        path = new Stack();
        
        mapParser = new MapParser(InputfilePath);
        mapParser.parseMap();
        heuristics = new Heuristics();
        start = mapParser.getStart();
        goal = mapParser.getGoal();
        goal.printNode();
        
        this.outputFilePath = outputFilePath;
        this.weight = weight;
        this.heuristicCode = heuristicCode;

        for(int i=0;i<HEIGHT;i++){
            for(int j=0;j<WIDTH;j++){
                map[i][j]=mapParser.getNode(j, i);
            }
        }
        
        map[goal.y][goal.x].isGoal = true;
        map[start.y][start.x].isStart = true;
        map[start.y][start.x].g = 0;
        map[start.y][start.x].parent = map[start.y][start.x];
        
        fringe.addNode(map[start.y][start.x]);
        
        
        //System.out.print("Satrt Node "); map[start.y][start.x].printNode();
        //System.out.print("Goal Node "); map[goal.y][goal.x].printNode();
        
    }
    
    public boolean findPath() throws IOException{
        Node s;
        while(!fringe.isEmpty()){
            s = fringe.popNode();
            expandedNodeCount++;
            s.isExpanded=true;
            //System.out.println();
            //System.out.print("Expanding g-value= "+s.g+" "); s.printNode();
            if(s.isGoal){
                traceBack(s);
                return true;
            }
                
            s.closed=true;
            successor = s.getSuccessor();
            for (Node t: successor){
                if(!map[t.y][t.x].closed){
                    updateNode(s, map[t.y][t.x]);
                } 
            }
            map[s.y][s.x]=s;
        } 
        return false;
    }
    
    void updateNode(Node s1, Node s2){
        if((s1.g + cost(s1,s2)) < s2.g){
            //System.out.println("Cost " + cost(s1,s2) );
            exploredNodeCount++;
            s2.isExplored=true;
            s2.g = s1.g + cost(s1,s2);
            switch(heuristicCode){
                case 0:
                    s2.h = heuristics.heuristicZero();
                    break;
                case 1:
                    s2.h = heuristics.heuristicOne(s2, map[goal.y][goal.x]);
                    break;
                case 2:
                    s2.h = heuristics.heuristicTwo(s2, map[goal.y][goal.x]);
                    break;
                case 3:
                    s2.h = heuristics.heuristicThree(s2, map[goal.y][goal.x]);
                    break;
                case 4:
                    s2.h = heuristics.heuristicFour(s2, map[goal.y][goal.x]);
                    break;
                case 5:
                    s2.h = heuristics.heuristicTwo(s2, map[goal.y][goal.x]);
                    break;
                case 6:
                    s2.h = heuristics.heuristicTwo(s2, map[goal.y][goal.x]);
                    break;
                default:
                    s2.h=0;
                    break;
            }
            
            s2.f = s2.g + weight*s2.h;
            
            s2.parent = s1;
            if(fringe.contains(s2))
                fringe.updateNode(s2);
            else
                fringe.addNode(s2);
        }
    }
    
    double cost(Node s1, Node s2){
        
        double cost=0;
        
        if(Math.abs(s1.x+s1.y-s2.x-s2.y)==1){
            if(s1.status.contains("a") && s2.status.contains("a")){
                cost += 0.25;
                return cost;    
            }
        
            if(s1.status.contains("b") && s2.status.contains("b")){
                cost += 0.5;
                return cost;    
            }
        
            if(s1.status.contains("a") && s2.status.contains("b")){
                cost += 0.375;
                return cost;    
            }
        
            if(s1.status.contains("b") && s2.status.contains("a")){
                cost += 0.375;
                return cost;    
            }
        }
        
        switch (s1.status) {
            case "1":
            case "a":
                cost +=0.5;
                break;
            case "2":
            case "b":
                cost += 1;
                break;
            case "0":
                cost += 9999999;
                break;
            default:
                break;
        }
        
        switch (s2.status) {
            case "1":
            case "a":
                cost +=0.5;
                break;
            case "2":
            case "b":
                cost += 1;
                break;
            case "0":
                cost += 9999999;
                break;
            default:
                break;
        }
        
        if(Math.abs(s1.x+s1.y-s2.x-s2.y)==2)
            cost *= Math.sqrt(2);
      
        return cost;       
    }
    
    void traceBack(Node s) throws IOException{
        PathWriter p = new PathWriter(outputFilePath);
        double cost = s.g;
        System.out.println("Node Expanded = " + expandedNodeCount);
        System.out.println("Node Explored = " + exploredNodeCount);
        while(!s.isStart){
            path.push(s);
            s=s.parent;
        }
        path.push(s);
        Stack<Node> tmp=(Stack<Node>)path.clone();
        p.writePath(tmp, cost);
    }
    
    public Stack<Node> getPath(){
        return path;
    }
    
    public Node getNode(int y, int x){
        return map[y][x];
    }
    
    public Node[][] getMap(){
        return map;
    }
}
