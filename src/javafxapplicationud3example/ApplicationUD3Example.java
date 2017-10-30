/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplicationud3example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafxapplicationud3example.ui.controller.LoginController;

/**
 * Application class for UI example.
 * @author javi
 */
public class ApplicationUD3Example extends Application {
    /**
     * Entry point for the application. Loads, sets and shows primary window.
     * @param primaryStage The primary window of the application
     * @throws Exception 
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        //Load node graph from fxml file
        FXMLLoader loader=new FXMLLoader(
                getClass().getResource("ui/view/Login.fxml"));
        Parent root = (Parent)loader.load();
        //Get controller for graph and set a reference for Stage
        LoginController controller=
                ((LoginController)loader.getController());
        controller.setStage(primaryStage);
        //Initializes primary stage
        controller.initStage(root);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
