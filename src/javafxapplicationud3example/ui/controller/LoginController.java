/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplicationud3example.ui.controller;

import java.io.IOException;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafxapplicationud3example.businessLogic.UsersManager;

/**
 * Clase que define los manejadores de eventos de la interfaz definida mediante
 * el archivo Login.fmxl: una UI para login mediante autenticación básica. 
 * @author Javier Martín Uría
 */
public class LoginController {
    private static final Logger logger=Logger.getLogger("javafxapplicationud3example.ui.controller");
    @FXML
    private TextField tfUsuario;
    @FXML
    private TextField tfPassword;
    @FXML
    private Button btAceptar;
    //Referencia para el objeto de la capa de lógica de negocio
    private UsersManager usersManager;

    public void setUsersManager(UsersManager usersManager){
        this.usersManager=usersManager;
    }

    private Stage stage;
    
    public Stage getStage(){
        return stage;
    }
    
    public void setStage(Stage stage){
        this.stage=stage;
    }
    /**
     * Method for initializing Login Stage. 
     * @param root The Parent object representing root node of view graph.
     */
    public void initStage(Parent root) {
        logger.info("Initializing Login stage.");
        //Create a scene associated to the node graph root.
        Scene scene = new Scene(root);
        //Associate scene to primaryStage(Window)
        stage.setScene(scene);
        //Set window properties
        stage.setTitle("Login");
        stage.setResizable(false);
        //Set window's events handlers
        stage.setOnShowing(this::handleWindowShowing);
        //tfPassword.addEventHandler(InputMethodEvent.INPUT_METHOD_TEXT_CHANGED, this::handleTextChanged);
        //tfPassword.setOnKeyTyped(this::handleTextChanged);
        //Set control events handlers (if not set by FXML)
        tfUsuario.textProperty().addListener(this::textChanged);
        tfPassword.textProperty().addListener(this::textChanged);
        //Show primary window
        stage.show();
    }
    /**
     * Initializes window state. It implements behavior for WINDOW_SHOWING type 
     * event.
     * @param event  The window event 
     */
    private void handleWindowShowing(WindowEvent event){
        logger.info("Beginning LoginController::handleWindowShowing");
        //El botón Aceptar se deshabilita
        btAceptar.setDisable(true);
        //Establecer prompt text
        tfUsuario.setPromptText("Introduzca su id...");
        tfPassword.setPromptText("Introduzca su contraseña...");
        //Establecer tooltip para btAceptar
        btAceptar.setTooltip(
                new Tooltip("Pulse para validar credenciales"));
        btAceptar.setMnemonicParsing(true);
        btAceptar.setText("_Aceptar");
        
    }
    /**
     * Manejador de evento de pulsación sobre el botón de Aceptar. Valida que el 
     * usuario y la contraseña están informados. Si no lo están muestra un mensaje 
     * informativo al usuario. Si lo están muestra la ventana definida en 
     * GestionUsuarios.fxml. 
     * @param event El evento Action producido 
     */
    @FXML
    private void handleButtonAceptarAction(ActionEvent event) {
        logger.info("Aceptar Action event.");
        //Valida que el usuario y la contraseña están informados
        if(this.tfUsuario.getText().trim().equals("")||
           this.tfPassword.getText().trim().equals("")){
            //Si no están informados muestro mensaje de error
            Alert alert=new Alert(AlertType.ERROR,
                                  "Los campos usuario y contraseña \n deben estar informados",
                                   ButtonType.OK);
            alert.getDialogPane().getStylesheets().add(
                    getClass().getResource("/javafxapplicationud3example/ui/view/customCascadeStyleSheet.css").toExternalForm());
            alert.showAndWait();
        }
        else{
            //Si lo están muestra la ventana definida en GestionUsuarios.fxml
            try{
                //Load node graph from fxml file
                FXMLLoader loader=
                        new FXMLLoader(getClass().getResource("/javafxapplicationud3example/ui/view/GestionUsuarios.fxml"));
                Parent root = (Parent)loader.load();
                //Get controller for graph 
                GestionUsuariosController controller=
                        ((GestionUsuariosController)loader.getController());
                controller.setUsersManager(usersManager);
                //Initializes stage
                controller.initStage(root);
                //hides login stage
                stage.hide();
            }catch(IOException ex){
                Alert alert=new Alert(AlertType.ERROR,
                                      "No se ha podido abrir la ventana:"+
                                       ex.getMessage(),
                                       ButtonType.OK);
                alert.getDialogPane().getStylesheets().add(
                    getClass().getResource("/javafxapplicationud3example/ui/view/customCascadeStyleSheet.css").toExternalForm());
                alert.showAndWait();
            }
        }
    }
    /**
     * Manejador de evento de cambio de texto. Valida que el 
     * usuario y la contraseña están informados. Si no lo están deshabilita el 
     * botón Aceptar. Si están informados habilita el botón.
     * @param event El evento de método de entrada (InputMethodEvent) producido 
     */
    private void textChanged(ObservableValue observable,
             String oldValue,
             String newValue){
        if(tfUsuario.getText().trim().isEmpty()||
           tfPassword.getText().trim().isEmpty())
            btAceptar.setDisable(true);
        else btAceptar.setDisable(false);
        
    }
}
