/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplicationud3example.ui.controller;

import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
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
    @FXML
    private CheckBox ckSelectAll;
    @FXML
    private RadioButton rbUsuario;
    @FXML
    private RadioButton rbAdmin;
    @FXML
    private ToggleGroup tgPerfil;
    @FXML
    private ComboBox cbDepartamentos;
    
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
        stage.showAndWait();
    }
    
    /**
     * Initializes window state. It implements behavior for WINDOW_SHOWING type 
     * event.
     * @param event  The window event 
     */
    private void handleWindowShowing(WindowEvent event){
        logger.info("Beginning GestionUsuariosController::handleWindowShowing");
        //Establecer datos de la combo de departamentos
        ObservableList<String> departmentNames=
                FXCollections.observableArrayList("FOL",
                                                  "Informática",
                                                  "Electrónica",
                                                  "Imagen y Sonido");
        cbDepartamentos.setItems(departmentNames);
        //Seleccionar un departamento por defecto
        cbDepartamentos.getSelectionModel().select("Electrónica");
        //Obtener valor seleccionado en la combo y moverlo a un campo de texto
        tfLogin.setText(cbDepartamentos.getValue().toString());
        //Los botones Crear, Modificar y Eliminar se 
        //deshabilitan.
        //btEliminar.disableProperty()
        //Seleccionar Select All
        ckSelectAll.setSelected(true);
        //Seleccionar perfil administrador
        tgPerfil.selectToggle(rbAdmin);
        //Añadir manejador para eventos de foco
        tfLogin.focusedProperty().addListener(this::focusChanged);
        
    }
    /**
     * Manejador de evento para un cambio de foco.
     */
    private void focusChanged(ObservableValue observable,
             Boolean oldValue,
             Boolean newValue){
        if(newValue)
            logger.info("onFocus");
        else if(oldValue)
            logger.info("onBlur");
    }
    @FXML
    private void handleTextChanged(KeyEvent event) {
        logger.info("Text Changed event.");
    }
    @FXML
    private void handleEliminarAction(ActionEvent event){
        //Enfocar campo de login y poner el texto en rojo
        tfLogin.requestFocus();
        tfLogin.setStyle("-fx-text-inner-color: red;");
    }

}
