/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplicationud3example.ui.controller;

import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Clase que define los manejadores de eventos de la interfaz definida mediante
 * el archivo GestionUsuarios.fmxl: una UI para mantenimiento de datos de usuarios. 
 * @author Javier Martín Uría
 */
public class GestionUsuariosController{
    private static final Logger logger=Logger.getLogger("javafxapplicationud3example.ui.controller");
    @FXML
    private Button btCrear;
    @FXML
    private Button btModificar;
    @FXML
    private Button btEliminar;
    @FXML
    private Button btSalir;
    @FXML
    private TextField tfLogin;

    private Stage stage;
    
    public Stage getStage(){
        return stage;
    }
    
    public void setStage(Stage stage){
        this.stage=stage;
    }
    /**
     * Method for initializing GestionUsuarios Stage. 
     * @param root The Parent object representing root node of view graph.
     */
    public void initStage(Parent root) {
        Scene scene = new Scene(root);
        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.setTitle("Gestion de Usuarios");
        stage.setResizable(false);
        stage.setOnShowing(this::handleWindowShowing);
        tfLogin.setOnKeyTyped(this::handleTextChanged);
        stage.showAndWait();
    }
    
    /**
     * Initializes window state. It implements behavior for WINDOW_SHOWING type 
     * event.
     * @param event  The window event 
     */
    private void handleWindowShowing(WindowEvent event){
        logger.info("Beginning GestionUsuariosController::handleWindowShowing");
        //Los botones Crear, Modificar y Eliminar se 
        //deshabilitan.
        //btEliminar.disableProperty()
        
    }
    @FXML
    private void handleTextChanged(KeyEvent event) {
        logger.info("Text Changed event.");
    }    

}
