/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package map;

import algorithms.*;
import java.io.*;
import java.util.regex.*;


	
/**
 *
 * @author xitizzz
 */

public class MapParser {
    
    static final int COLUMNS=160,ROWS=120,HARD_COUNT=8,HARD_SIZE=31;
    
    BufferedReader br;
    String line;
    
    /**
     *  Provide map to all classes that needs it.
     */
    public String [][] map;
    public int startX, startY;
    public int goalX, goalY;
    public int[][] HRC = new int[8][2];
    public int rows,columns;


    public MapParser(String fileName ) throws IOException{
        br=new BufferedReader(new FileReader(fileName));
    }
    
    
    public  void parseMap() throws IOException{
        
        String[] tmp; 
        tmp = br.readLine().split(",");
        startY = Integer.parseInt(tmp[0])-1;
        startX = Integer.parseInt(tmp[1])-1;
        
        tmp = br.readLine().split(",");
        goalY = Integer.parseInt(tmp[0])-1;
        goalX = Integer.parseInt(tmp[1])-1;
        
        for(int i=0;i<8;i++){
            tmp = br.readLine().split(",");
            HRC[i][1]=Integer.parseInt(tmp[0])-1;
            HRC[i][0]=Integer.parseInt(tmp[1])-1;
        }
        
        tmp = br.readLine().split(",");
        rows = Integer.parseInt(tmp[0]);
        columns = Integer.parseInt(tmp[1]);
        
        map=new String[rows][columns];
        
        for(int i=0;i<rows;i++){
            tmp = br.readLine().split(",");
            for(int j=0;j<columns;j++){
                 map[i][j]=tmp[j];
            }
        }
        map[goal.y][goal.x].isGoal = true;
        map[start.y][start.x].isStart = true;
        map[start.y][start.x].g = 0;
        map[start.y][start.x].parent = map[start.y][start.x];
        System.out.println("map.MapParser.parseMap()");
    }
        
        public Node getStart(){
            Node n=new Node();
           // n.isStart=true;
            n.x=startX; n.y=startY;
            return n;
        }
    
        public Node getGoal(){
            Node n=new Node();
            //line=br.readLine();
            n.x=goalX; n.y=goalY;
            //n.isGoal=true;
            return n;
        }
    
        public Node getNode(int x, int y){
            Node n=new Node();
            n.x=x;
            n.y=y;
            n.g=9999999;
            n.parent=null;
            n.status = map[y][x];
            return n;
        }
         
        
        public Node[][] getMap(){
            Node[][] extendedMap = new Node[120][160];
            for(int i=0;i<rows;i++){
                for(int j=0;j<columns;j++){
                    extendedMap[i][j]=new Node();
                    extendedMap[i][j].status=map[i][j];
                    extendedMap[i][j].x=j;
                    extendedMap[i][j].y=i;
                }
            }
            return extendedMap;
        }

}