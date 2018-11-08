/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplicationud3example.ui.controller;

import java.util.logging.Level;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.stage.WindowEvent;

/**
 * Controller UI class for Login view in users' management application. It contains 
 * event handlers and initialization code for the view defined in Login.fxml file.
 * @author javi
 */
public class LoginController extends GenericController{
   /**
    * User's login name UI text field.
    */
    @FXML
    private TextField tfUsuario;
    /**
    * User's password value UI entry field.
    */
    @FXML
    private TextField tfPassword;
    /**
     * Button to fire action to log in at the UI.
     */
    @FXML
    private Button btAceptar;
    /**
     * Method for initializing Login Stage. 
     * @param root The Parent object representing root node of view graph.
     */
    public void initStage(Parent root) {
        LOGGER.info("Initializing Login stage.");
        //Create a scene associated to the node graph root.
        Scene scene = new Scene(root);
        //Associate scene to primaryStage(Window)
        stage.setScene(scene);
        //Set window properties
        stage.setTitle("Login");
        stage.setResizable(false);
        //Set window's events handlers
        stage.setOnShowing(this::handleWindowShowing);
        //Set control events handlers (if not set by FXML)
        tfUsuario.textProperty().addListener(this::textChanged);
        tfPassword.textProperty().addListener(this::textChanged);
        //Show primary window
        stage.show();
    }
    /**
     * Window event method handler. It implements behavior for WINDOW_SHOWING type 
     * event.
     * @param event  The window event 
     */
    private void handleWindowShowing(WindowEvent event){
        LOGGER.info("Beginning LoginController::handleWindowShowing");
        //Aceptar button is disabled.
        btAceptar.setDisable(true);
        //Set all prompt texts.
        tfUsuario.setPromptText("Introduzca su id...");
        tfPassword.setPromptText("Introduzca su contraseña...");
        //Set tooltip for btAceptar
        btAceptar.setTooltip(
                new Tooltip("Pulse para validar credenciales"));
        btAceptar.setMnemonicParsing(true);
        btAceptar.setText("_Aceptar");
    }
    /**
     * Action event handler for Aceptar button. It validates that user and password
     * fields are filled. If they are not, an error message dialog is shown. 
     * Otherwise the users' data managing window is opened.
     * @param event The Action event 
     */
    @FXML
    private void handleButtonAceptarAction(ActionEvent event) {
        //Validates user and password fields.
        if(this.tfUsuario.getText().trim().equals("")||
           this.tfPassword.getText().trim().equals("")){
            //Shows error dialog.
            showErrorAlert("Los campos usuario y contraseña \n deben estar informados");
        }
        else{
            //Shows view from GestionUsuarios.fxml
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
                //stage.hide();
            }catch(Exception ex){
                showErrorAlert("No se ha podido abrir la ventana:\n"+
                                       ex.getLocalizedMessage());
                LOGGER.log(Level.SEVERE,
                        "UI LoginController: Error opening users managing window: {0}",
                        ex.getMessage());
            }
        }
    }
    /**
     * Text changed event handler. It validates that user and password fields 
     * has any content to enable/disable Aceptar button.
     * @param observable The value being observed.
     * @param oldValue The old value of the observable.
     * @param newValue The new value of the observable.
     */
    private void textChanged(ObservableValue observable,
             String oldValue,
             String newValue){
        //If text fields values are too long, show error message and disable 
        //accept button
        if(tfUsuario.getText().trim().length()>this.MAX_LENGTH ||
           tfPassword.getText().trim().length()>this.MAX_LENGTH){
            showErrorAlert("La longitud máxima del campo es de 255 caracteres.");
            btAceptar.setDisable(true);
        }
        //If text fields are empty disable accept buttton
        else if(tfUsuario.getText().trim().isEmpty()||
                tfPassword.getText().trim().isEmpty())
            btAceptar.setDisable(true);
        //Else, enable accept button
        else btAceptar.setDisable(false);
        
    }
}
