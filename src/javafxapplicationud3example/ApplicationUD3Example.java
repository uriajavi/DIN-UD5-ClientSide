/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplicationud3example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafxapplicationud3example.businessLogic.UsersManager;
import javafxapplicationud3example.businessLogic.UsersManagerFactory;
import static javafxapplicationud3example.businessLogic.UsersManagerFactory.REST_WEB_CLIENT_TYPE;
import static javafxapplicationud3example.businessLogic.UsersManagerFactory.TEST_MOCK_TYPE;
import javafxapplicationud3example.ui.controller.LoginController;

/**
 * Application class for UI example. Entry point for the JavaFX application.
 * @author javi
 */
public class ApplicationUD3Example extends Application {
    /**
     * Entry point for the JavaFX application. Loads, sets and shows primary window.
     * It also uses a factory to get a business logic object to be passed to views.
     * @param primaryStage The primary window of the application
     * @throws Exception 
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        //Create Bussines Logic Controller to be passed to UI controllers
        UsersManager bussinessLogicController=
                UsersManagerFactory.createUsersManager(REST_WEB_CLIENT_TYPE);
        //Uncomment this sentence if you want fake data for testing the UI 
        //bussinessLogicController=
        //        UsersManagerFactory.createUsersManager(TEST_MOCK_TYPE);
        //Load node graph from fxml file
        FXMLLoader loader=new FXMLLoader(
                getClass().getResource("ui/view/Login.fxml"));
        Parent root = (Parent)loader.load();
        //Get controller for graph 
        LoginController primaryStageController=
                ((LoginController)loader.getController());
        //Set a reference in UI controller para Bussiness Logic Controllesr
        primaryStageController.setUsersManager(bussinessLogicController);
        //Set a reference for Stage
        primaryStageController.setStage(primaryStage);
        //Initializes primary stage
        primaryStageController.initStage(root);
    }

    /**
     * Entry point for the Java application.
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
