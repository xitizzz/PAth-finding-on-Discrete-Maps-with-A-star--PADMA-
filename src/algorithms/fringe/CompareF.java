/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithms.fringe;

import algorithms.Node;
import java.util.Comparator;

/**
 *
 * @author xitizzz
 */
public class CompareF implements Comparator<Node> {
    
    @Override
    public int compare(Node s1, Node s2){
        if(s1.f>s2.f)
            return 1;
        if(s2.f>s1.f)
            return -1;
        return 0;
    }
}