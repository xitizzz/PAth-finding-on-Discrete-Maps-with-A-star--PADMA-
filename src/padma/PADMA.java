/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package padma;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author xitizzz
 */
public class PADMA extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("PADMA_FXML.fxml"));
        //FXMLLoader fc = FXML
        Scene scene = new Scene(root);
        //StackPane leftHalf = new StackPane();
        //leftHalf.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        
        
        
        stage.setScene(scene);
        stage.show();
        stage.setMaximized(true);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
