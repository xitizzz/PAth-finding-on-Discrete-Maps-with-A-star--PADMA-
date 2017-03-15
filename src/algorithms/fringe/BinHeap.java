/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithms.fringe;

import algorithms.Node;

/**
 *
 * @author xitizzz
 */

public class BinHeap {
    private Node heap[];
    private int n;
    private final CompareF comp;
    
    public BinHeap(int capecity){
        heap= new Node[capecity+1];
        n=0;
        comp = new CompareF();
    }
    
    void resize(int capecity){
        Node[] tmp = new Node[capecity];
        for(int i=0; i<n ; i++){
            tmp[i] = heap[i];
        }
        heap = tmp;         
    }
    
    public void add(Node node){
        if(heap.length - 1 == n)
            resize(heap.length * 2);
        heap[++n] = node;
        heapifyUp(n);
    }
    
    public Node pop(){
        swap(1,n);
        Node ans = heap[n];
        n--;
        heapifyDown(1);
        heap[n+1] = null;
        return ans;
    }
    
    public void remove(Node node){
        for (int i=0; i<n ; i++) {
            if(heap[i].x==node.x && heap[i].y==node.y){
                swap(i,n);
                n--;
                heapifyDown(i);
                heap[n+1] = null;
            }
        }
    } 
    
    public void remove(){
        swap(1,n);
        //Node ans = heap[n];
        n--;
        heapifyDown(1);
        heap[n+1] = null;
        //return ans;
    }
    
    void heapifyUp(int x){
        while(x > 1 && priority(x/2, x)){
            swap(x, x/2);
            x = x/2;
        }
    }
    
    void heapifyDown(int x){
        while (2*x < n){
            int y = 2*x;
            if(y < n && priority(y, y+1)) 
                y++;
            if(!priority(x, y)) return;
            swap(x, y);
            x = y;
        }
    }
    
    void swap(int i, int j){
        Node tmp = heap[i];
        heap[i] = heap[j];
        heap[j] = tmp;
    }
    
    boolean priority(int i, int j){
        return comp.compare(heap[i], heap[j])==1;
    }
}


