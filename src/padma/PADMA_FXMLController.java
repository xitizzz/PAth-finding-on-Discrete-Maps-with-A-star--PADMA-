/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package padma;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.FileChooser;
import javafx.scene.input.MouseEvent;

import map.*;
import algorithms.*;
import java.util.Stack;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import path.*;


/**
 *
 * @author xitizzz
 */
public class PADMA_FXMLController implements Initializable{
    
    Node [][] extendedMap = new Node[120][160];
    //String [][] simpleMap = new String[120][160];
    Stack<Node> path = new Stack();
    
    MapGenerator mapGenerator;
    MapParser mapParser;
    Astar astar;
    
    Node start, goal;
    boolean mapLoaded=false;
    
    //@FXML Parent gridPane;
    //@FXML DisplayGridController display;
    //@FXML DisplayGrid
    //@FXML private GridPane gridContainer
    //@FXML private GridPane controlPane;
    @FXML private GridPane gridContainer;
    @FXML private TextField cm;
    @FXML private RadioButton uniformCost;
    @FXML private RadioButton aStar;
    @FXML private RadioButton weightedAstar;
    @FXML private TextField rm;
    @FXML private TextField w;
    @FXML private Label heuristic;
    @FXML private Label hAndW;
    @FXML private ComboBox hu;
    
    final ToggleGroup alg = new ToggleGroup();
   // FileChooser fileChooser = new FileChooser();

    
    
    @FXML
    private void createMap(ActionEvent event) {
        try{
            String tmp[] = cm.getText().split("\\.");
            mapGenerator= new MapGenerator(tmp[0]+".txt");
            cm.setText(tmp[0]+".txt");
            rm.setText(tmp[0]+".txt");
            mapGenerator.printMap();
            mapParser = new MapParser(cm.getText());
            mapParser.parseMap();
        }
        catch(Exception e){
            e.printStackTrace(System.out);
        }
        
        extendedMap = mapParser.getMap();
        start=mapParser.getStart();
        goal=mapParser.getGoal();
        
        //extendedMap = mapGenerator.getMap();
        
        visualizeMap();
        mapLoaded = true; 
    }
    
    @FXML
    private void readMap(ActionEvent event) {
        System.out.println("padma.PADMA_FXMLController.readMap()");
        try{
            mapParser= new MapParser(rm.getText());
            mapParser.parseMap();
        }
        catch(Exception e){
            e.printStackTrace(System.out);
            System.out.println("Problem With IORead");
        }
        
        extendedMap=mapParser.getMap();
        start=mapParser.getStart();
        goal=mapParser.getGoal();
        visualizeMap();
        mapLoaded = true;
    }
    
    @FXML
    private void setUniform(ActionEvent event) {
        if(uniformCost.isSelected()){
            hu.setValue("Heuristic 0");
            hu.setDisable(true);
            w.setText("0");
            w.setDisable(true);
            heuristic.setVisible(false);
            hAndW.setVisible(false);
        }
    }
    
    @FXML
    private void setAstar(ActionEvent event) {
        if(aStar.isSelected()){
            hu.setDisable(false);
            w.setText("1");
            w.setDisable(true);
            heuristic.setVisible(true);
            hAndW.setVisible(false);
        }
    }
    
    @FXML
    private void setWeightedAstar(ActionEvent event) {
        if(weightedAstar.isSelected()){
            hu.setDisable(false);
            w.setText("1");
            w.setDisable(false);
            heuristic.setVisible(false);
            hAndW.setVisible(true);
        }
    }
    
    @FXML
    private void runAstar(ActionEvent event) {
        if(!mapLoaded){
            // temp code
            heuristic.setText("Please Load a Map");
            return;
        }
        try{
        System.out.println(rm.getText());     
        String[] tmp;
        tmp=rm.getText().split("\\.");
        System.out.println(tmp[0]);
        String pathFile=tmp[0].concat("_path.txt");
        int h=0;
        //temp code
            //System.out.println(hu.getValue());
        try{
        switch(hu.getValue().toString()){
            case "Heuristic 0": h=0;break;
            case "Heuristic 1": h=1; break;
            case "Heuristic 2": h=2; break;
            case "Heuristic 3": h=3; break;
            case "Heuristic 4": h=4; break;
            case "Heuristic 5": h=5; break;
            default: h=0; break;
        }
        }
        catch(Exception e){
            e.printStackTrace(System.out);
        }
        
        
        astar = new Astar(rm.getText(), pathFile, h, Double.parseDouble(w.getText()) );
        astar.findPath();
        path = astar.getPath();
        
        extendedMap = astar.getMap();
        start=mapParser.getStart();
        goal=mapParser.getGoal();
        visualizeMap();
        
        //temp code
        heuristic.setText("Path Cost " + extendedMap[goal.y][goal.x].g + " " +astar.expandedNodeCount+" "+astar.exploredNodeCount);
        heuristic.setVisible(true);
        
        }
        catch(Exception e){
            e.printStackTrace(System.out);
        }
    }

//    @FXML
//    void mouseEntered(MouseEvent e){
//        //
//        javafx.scene.Node source = (javafx.scene.Node)e.getSource() ;
//        Integer colIndex = GridPane.getColumnIndex(source);
//        Integer rowIndex = GridPane.getRowIndex(source);
//        heuristic.setText(colIndex+" "+rowIndex);
//    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb){
        
        uniformCost.setToggleGroup(alg);
        aStar.setToggleGroup(alg);
        weightedAstar.setToggleGroup(alg);
        
        createGrid();
        
    }
    
    void createGrid(){
        for(int i=0;i<120;i++){
            for(int j=0;j<160;j++){
                Rectangle r=new Rectangle(4, 4,Color.WHITE);
                r.setStrokeWidth(0.1);
                r.setStroke(Color.BLACK);
                gridContainer.add(r,j,i);
            }
        }
    }
    
    void visualizeMap(){
        for(int i=0;i<120;i++){
            for(int j=0;j<160;j++){
                Rectangle r=new Rectangle(4, 4,Color.WHITE);
                if(extendedMap[i][j].status.equals("0")) r.setFill(Color.BLACK);
                else if(extendedMap[i][j].status.equals("2")) r.setFill(Color.LIGHTGREY);
                else if(extendedMap[i][j].status.contains("a")) r.setFill(Color.BLUE);
                else if(extendedMap[i][j].status.contains("b")) r.setFill(Color.AQUA);
                
                r.setStrokeWidth(0.1);
                r.setStroke(Color.BLACK);
                r.setOnMouseClicked(new EventHandler<MouseEvent>(){
                    @Override
                    public void handle(MouseEvent event) {
                            //Temp code
                            heuristic.setText("Row: "+ GridPane.getRowIndex(r)+" Column: "+ GridPane.getColumnIndex(r)+" f-value= "+extendedMap[GridPane.getRowIndex(r)][GridPane.getColumnIndex(r)].f );
                    }
                    });
                
                gridContainer.add (r, j, i);
            }
        }
        
        highLightPath();
        Rectangle r1=new Rectangle(4,4,Color.BLUEVIOLET);
        r1.setStrokeWidth(0.1);
        r1.setStroke(Color.BLACK);
        Rectangle r2=new Rectangle(4,4,Color.CHARTREUSE);
        r2.setStrokeWidth(0.1);
        r2.setStroke(Color.BLACK);
        gridContainer.add (r1, start.x, start.y );
        gridContainer.add (r2, goal.x, goal.y);
    }
    
    void highLightPath(){
        if (!path.isEmpty()) markExplored();
        while(!path.isEmpty()){
            Node s = path.pop();
            Rectangle r=new Rectangle(4, 4,Color.RED);
            r.setStrokeWidth(0.1);
            r.setStroke(Color.BLACK);
            gridContainer.add (r, s.x, s.y);
        }
    }
    
    void markExplored(){
        for(int i=0;i<120;i++){
            for(int j=0;j<160;j++){
                if(astar.getNode(i, j).isExpanded){
                    //System.out.println("mm");
                    Rectangle r = new Rectangle(4,4,Color.ORANGE);
                    r.setOpacity(0.5);
                    gridContainer.add(r,j,i);
                }
            }
        }
    }
}
