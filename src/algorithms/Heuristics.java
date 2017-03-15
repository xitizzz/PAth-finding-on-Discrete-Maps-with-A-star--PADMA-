/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithms;

/**
 *
 * @author xitizzz
 */
public class Heuristics {
    
    double heuristicZero(){
        return 0;
    }
    
    double heuristicOne(Node s1, Node s2){
        return Math.sqrt(Math.pow((s1.x-s2.x), 2)+Math.pow((s1.y-s2.y), 2))*0.25;
    }
    
    double heuristicTwo(Node s1, Node s2){
        return (Math.abs(s1.x-s2.x)+Math.abs(s1.y-s2.y))*0.25;
    }
    
    double heuristicThree(Node s1, Node s2){
        return Math.sqrt(Math.pow((s1.x-s2.x), 2) + Math.pow((s1.y-s2.y), 2));
    }
    
    double heuristicFour(Node s1, Node s2){
        double diag = Math.min(Math.abs(s1.x-s2.x), Math.abs(s1.y-s2.y))*Math.sqrt(2);
        double straight = Math.abs(Math.abs(s1.x-s2.x) - Math.abs(s1.y-s2.y)); 
        return diag+straight;
    }
    
    double heuristicFive(Node s1, Node s2){
        return 0;
    }
    
    double heuristicSix(Node s1, Node s2){
        return 0;
    }
    
    
    
    
    
}
