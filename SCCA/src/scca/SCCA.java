/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scca;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author marianacro
 */
public class SCCA extends Application {
    
    @Override
    public void start(Stage primaryStage) throws IOException {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentacion/GUIPaginaInicio.fxml"));
      Parent root = loader.load();
      Scene scene = new Scene(root);
      primaryStage.setScene(scene);
      primaryStage.setResizable(false);
      primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
