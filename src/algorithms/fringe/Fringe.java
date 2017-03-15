/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithms.fringe;

import algorithms.Node;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 *
 * @author xitizzz
 */
public class Fringe {
    
    PriorityQueue<Node> heap; 
    
    //BinHeap heap;
    
    public Fringe(){
        Comparator<Node> comparator = new CompareF();
        heap = new PriorityQueue<>(20000, comparator);
        //heap = new BinHeap(200);
    }
    
    public void addNode(Node s){
        heap.add(s);
    } 
    
    public Node popNode(){
        return heap.poll();
    }
    
    public void removeNode(Node s){
        heap.remove(s);
    }
    
    public boolean isEmpty(){
        return heap.isEmpty();
    }
    
    public boolean contains(Node s){
        for (Node n : heap) {
            if(s.x==n.x && s.y==n.y)
                return true;
        }
        return false;
    }
    
    public void updateNode(Node s){
        for (Node n : heap) {
            if(s.x==n.x && s.y==n.y){
                heap.remove(n);
                heap.add(s);
                return;
            }
        }
    }
    
}