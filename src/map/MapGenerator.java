/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package map;

import algorithms.Node;
import java.io.*;

/**
 *
 * @author Susmitha
 */
public class MapGenerator {
    
    static final int COLUMN=160,ROW=120,HARD_COUNT=8,HARD_SIZE=31;
    
    static final String NORMAL="1";
    static final String BLOCKED="0";
    static final String HARD="2";
    static final String HIGHWAY="a";
    static final String HARD_HIGHWAY="b";
    
    BufferedWriter fout;
    String[][] map=new String[ROW][COLUMN];
    int HRC[][]=new int[HARD_COUNT][2];
    int startX,startY,goalX,goalY,dirX,dirY;
    
    public MapGenerator() throws IOException{
        fout=new BufferedWriter(new FileWriter("CorrectedMap1.txt"));  
        fout.flush();
    }
    
    public MapGenerator(String filePath) throws IOException{
        fout=new BufferedWriter(new FileWriter(filePath));  
        fout.flush();
    }
    
    public MapGenerator(String filePath, int n) throws IOException{
        String[] tmp = filePath.split(".");
        fout=new BufferedWriter(new FileWriter(tmp[0]+"1"+tmp[1]));  
        fout.flush();
        generateMap();
        printMap();
        for(int i=1; i<n; i++){
            fout=new BufferedWriter(new FileWriter(tmp[0]+(i+1)+tmp[1]));  
            fout.flush();
            setStartCell();
            setGoalCell();
            printMap();
        }
    }
      
    public final void printMap() throws IOException{
        int i,j;
        generateMap();
        fout.write((startY+1)+","+(startX+1));
        fout.newLine();
        fout.write((goalY+1)+","+(goalX+1));
        fout.newLine();
        for(i=0;i<8;i++){
            fout.write((HRC[i][1]+1)+","+(HRC[i][0]+1));
            fout.newLine();
        }
        fout.write(ROW+","+COLUMN);
        fout.newLine();
        for(i=0;i<ROW;i++){
            for(j=0;j<COLUMN;j++){
                if(j==159)
                    fout.write(map[i][j]);
                else
                    fout.write(map[i][j]+",");
            }
            fout.newLine();
        }
        fout.close();
    }
    
    final void generateMap(){

        int c,x,y,blockCount;
        
        //Initialize map array m with 1 (Regular cell)
        for(int i=0; i<ROW; i++)
            for(int j=0; j<COLUMN; j++)
            map[i][j]=NORMAL;
        
        //Create four highway
        c=0;
        while(c<4){
            //If highway is created successfully the functions returns true else false
            if(createHighway(c+1)) c++;
        }
        
        c=0;
        while(c<HARD_COUNT){
            x=randomNumber(HARD_SIZE/2, COLUMN-(HARD_SIZE/2));
            y=randomNumber(HARD_SIZE/2, ROW-(HARD_SIZE/2));
            HRC[c][0]=x; HRC[c][1]=y;
            hardToTraverse(x, y);
            c++;
        }
        
        //Block cells
        c=0;
        blockCount=3840;
        
        while(c<blockCount){
            x=randomNumber(0,COLUMN);
            y=randomNumber(0,ROW);
            if(blockCell(x,y)) c++;
        }

        c=0;
        while(c<1){
            if(setStartCell()) c++;
        }

        c=0;
        while(c<1){
            if(setGoalCell()) c++;
        }
    
    }    
    
    boolean createHighway(int n){
        int  x, y, l=0, c=0;
        String[][] tm = new String[ROW][COLUMN];
        for(int i=0; i<ROW; i++)
            for(int j=0; j<COLUMN; j++)
                tm[i][j]=".";
        
        int[][] TBM = new int[1000][2];
        
        int side=randomNumber(0, 4);
        
        switch(side){
            case 0: x=randomNumber(0,160); y=0;
                    dirX=0; dirY=1;
                    break;
            case 1: x=159; y=randomNumber(0,120);
                    dirX=-1; dirY=0;
                    break;
            case 2: x=randomNumber(0,160); y=119;
                    dirX=-0; dirY=-1;
                    break;
            default: x=0; y=randomNumber(0,120);
                    dirX=1; dirY=0;
                    break;
        }
        
        do{
            if(map[y][x].contains(HIGHWAY) || tm[y][x].equals("X")) return false;
            TBM[l][0]=x; TBM[l][1]=y;
            tm[y][x]="X";
            x+=dirX; y+=dirY;
            l++; c++;
            if(c>=19){
                redirect();
                c=0;
            }
        }while(x<COLUMN-1 && y<ROW-1 && x>0 && y>0 );
        
        TBM[l][0]=x; TBM[l][1]=y;
        tm[y][x]="X";
        if(l<100) return false;

        for(int i=0;i<=l;i++){
            map[TBM[i][1]][TBM[i][0]]=HIGHWAY+n;
        }
        return true;
    }
     
    void redirect(){
        int r=randomNumber(0,10);
        if(r==6 || r==7){
            if(dirX==0){
                dirX=1;
                dirY=0;
            }
            else if(dirY==0){
                dirX=0;
                dirY=1;
            }
        }

        else if(r==8 || r==9){
            if(dirX==0){
                dirX=-1;
                dirY=0;
            }
            else if(dirY==0){
                dirX=0;
                dirY=-1;
            }
        }
    }
    
    void hardToTraverse(int x,int y){
        int regionSize=HARD_SIZE/2;
        for(int i=y-regionSize;i<=y+HARD_SIZE/2;i++){
            for(int j=x-regionSize;j<=x+HARD_SIZE/2;j++){
                if(randomNumber(0,2)==1){
                    if(map[i][j].contains(HIGHWAY) || map[i][j].contains(HARD_HIGHWAY)){
                        switch (map[i][j]) {
                            case "a1":
                                map[i][j]="b1";
                                break;
                            case "a2":
                                map[i][j]="b2";
                                break;
                            case "a3":
                                map[i][j]="b3";
                                break;
                            case "a4":
                                map[i][j]="b4";
                                break;
                            default:
                                break;
                        }
                    } 
                    else map[i][j]=HARD;
                }
            }
        }
    }
    
    boolean blockCell(int x, int y){
        if(map[y][x].contains(HIGHWAY) || map[y][x].contains(HARD_HIGHWAY) || map[y][x].equals(BLOCKED)) return false;
            map[y][x]=BLOCKED;
        return true;
    }
    
    final boolean setStartCell(){
        startX=randomNumber(0,20);
        startY=randomNumber(0,20);
        if(randomNumber(0,2)==1)
            startX = COLUMN-startX-1;
        if(randomNumber(0,2)==1)
            startY = ROW-startY-1;
        
        if(map[startY][startX].equals(BLOCKED)) return false;
        return true;
    }
    
    final boolean setGoalCell(){
        goalX=randomNumber(0,20);
        goalY=randomNumber(0,20);
        if(randomNumber(0,2)==1)
            goalX = COLUMN-goalX-1;
        if(randomNumber(0,2)==1)
            goalY = ROW-goalY-1;
        
        if(map[goalY][goalX].equals(BLOCKED) || Math.sqrt(Math.pow((startX-goalX), 2) + Math.pow((startY-goalY), 2))<=100) return false;
        
        return true;
    }
    
    public Node[][] getMap(){
        Node[][] extendedMap = new Node[120][160];
        for(int i=0;i<ROW;i++){
            for(int j=0;j<COLUMN;j++){
                extendedMap[i][j]=new Node();
                extendedMap[i][j].status=map[i][j];
                extendedMap[i][j].x=j;
                extendedMap[i][j].y=i;
            }
        }
        return extendedMap;
    }

     int randomNumber(int min_n, int max_n){
       int range = max_n-min_n;
        return min_n + (int) Math.floor(Math.random() * range);
    }
}