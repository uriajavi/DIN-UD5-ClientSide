/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplicationud3example.ui.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafxapplicationud3example.businessLogic.UsersManager;
import javafxapplicationud3example.businessLogic.BusinessLogicException;
import javafxapplicationud3example.businessLogic.LoginExistsException;
import javafxapplicationud3example.transferObjects.DepartmentBean;
import javafxapplicationud3example.transferObjects.Profile;
import javafxapplicationud3example.transferObjects.UserBean;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

/**
 * Controller class for users' management view . 
 * It contains event handlers and initialization code for the view defined in 
 * GestionUsuarios.fmxl file.
 * @author Javier Martín Uría
 */
public class GestionUsuariosController{
    private static final Logger LOGGER=Logger.getLogger("javafxapplicationud3example.ui.controller");
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
    private TextField tfNombre;
    @FXML
    private RadioButton rbUsuario;
    @FXML
    private RadioButton rbAdmin;
    @FXML
    private ToggleGroup tgPerfil;
    @FXML
    private ComboBox cbDepartamentos;
    @FXML
    private TableView tbUsers;
    @FXML
    private TableColumn tbcolLogin;
    @FXML
    private TableColumn tbcolNombre;
    @FXML
    private TableColumn tbcolPerfil;
    @FXML
    private TableColumn tbcolDepartamento;
    //Referencia para el objeto Ventana de la UI que controla esta clase
    private Stage stage;
    //Referencia para el objeto de la capa de lógica de negocio
    private UsersManager usersManager;
    //Modelo de datos de la tabla de usuarios
    private ObservableList<UserBean> usersData;
    
    public Stage getStage(){
        return stage;
    }
    
    public void setStage(Stage stage){
        this.stage=stage;
    }
    
    public void setUsersManager(UsersManager usersManager){
        this.usersManager=usersManager;
    }
    /**
     * Method for initializing GestionUsuarios Stage. 
     * @param root The Parent object representing root node of view graph.
     */
    public void initStage(Parent root) {
        try{
            Scene scene = new Scene(root);
            stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.setTitle("Gestion de Usuarios");
            stage.setResizable(false);
            stage.setOnShowing(this::handleWindowShowing);
            tfLogin.textProperty().addListener(this::handleTextChanged);
            tfNombre.textProperty().addListener(this::handleTextChanged);
            tbUsers.getSelectionModel().selectedItemProperty()
                    .addListener(this::handleUsersTableSelectionChanged);
 
            //Establecer datos de la combo de departamentos
            ObservableList<DepartmentBean> departments=
                    FXCollections.observableArrayList(usersManager.getAllDepartments());
            cbDepartamentos.setItems(departments);
            //Seleccionar un departamento por defecto
            //cbDepartamentos.getSelectionModel().select("Electrónica");
            //Seleccionar perfil usuario
            tgPerfil.selectToggle(rbUsuario);
            //Añadir manejador para eventos de foco
            //tfLogin.focusedProperty().addListener(this::focusChanged);
            //Establecer las factorías para los valores de celda de las columnas de
            //la tabla
            tbcolLogin.setCellValueFactory(
                    new PropertyValueFactory<>("login"));
            tbcolDepartamento.setCellValueFactory(
                    new PropertyValueFactory<>("departamento"));
            tbcolNombre.setCellValueFactory(
                    new PropertyValueFactory<>("nombre"));
            tbcolPerfil.setCellValueFactory(
                    new PropertyValueFactory<>("perfil"));
            //Crear una lista observable de Users para la tabla
                usersData=FXCollections.observableArrayList(usersManager.getAllUsers());
            //Establecer el modelo de datos de la tabla
            tbUsers.setItems(usersData);
            stage.show();
        }catch(BusinessLogicException e){
            Alert alert=new Alert(Alert.AlertType.ERROR,
                            "No se ha podido abrir la ventana.\n"+
                            e.getMessage(),
                            ButtonType.OK);
            alert.getDialogPane().getStylesheets().add(
            getClass().getResource("/javafxapplicationud3example/ui/view/customCascadeStyleSheet.css").toExternalForm());
            alert.showAndWait();
            //stage.close();
        }
    }
    
    /**
     * Initializes window state. It implements behavior for WINDOW_SHOWING type 
     * event.
     * @param event  The window event 
     */
    private void handleWindowShowing(WindowEvent event){
        LOGGER.info("Beginning GestionUsuariosController::handleWindowShowing");
            //Los botones Crear, Modificar y Eliminar se 
            //deshabilitan.
            btCrear.setDisable(true);
            btModificar.setDisable(true);
            btEliminar.setDisable(true);
            //Establecemos el propmt text de los campos login y nombre
            tfLogin.setPromptText("Introduzca un id...");
            tfNombre.setPromptText("Introduzca nombre y apellidos...");
            //Se enfoca el campo login
            tfLogin.requestFocus();
    }
    /*
     * Manejador de evento para un cambio de foco.
     */
    private void focusChanged(ObservableValue observable,
             Boolean oldValue,
             Boolean newValue){
        if(newValue)
            LOGGER.info("onFocus");
        else if(oldValue)
            LOGGER.info("onBlur");
    }
    /**
     * Manejador de los cambios de texto en los campos de Login y Nombre.
     * Habilita/Deshabilita los botones Añadir y Modificar en función del 
     * estado de los campos.
     * @param observable the property being observed: TextProperty of TextField.
     * @param oldValue   old String value for the property.
     * @param newValue   new String value for the property.
     * 
     */
    private void handleTextChanged(ObservableValue observable,
             String oldValue,
             String newValue) {
        //Validar que los campos Login, Nombre, Perfil
        //y Departamento están informados.
        if(tfNombre.getText().trim().isEmpty()||
           tfLogin.getText().trim().isEmpty()){
            //En el caso de que no lo estén 
            //deshabilitar los botones Crear y Modificar.
            btCrear.setDisable(true);
            btModificar.setDisable(true);
        }else{
        //En el caso de que estén informados, 
        //habilitar el botón Crear y si hay una fila 
        //seleccionada en la tabla habilitar también
        //el botón Modificar. 
            btCrear.setDisable(false);
            if(tbUsers.getSelectionModel().getSelectedItem()!=null)
                btModificar.setDisable(false);
        }
        tfLogin.setStyle("-fx-text-inner-color: black;");

    }
    /**
     * Manejador del evento de cambio de selección en la tabla de Usuarios.
     * Habilita/Deshabilita los botones Crear, Modificar y Eliminar en función del 
     * estado.
     * @param observable the property being observed: SelectedItem Property
     * @param oldValue   old UserBean value for the property.
     * @param newValue   new UserBean value for the property.
     */
    private void handleUsersTableSelectionChanged(ObservableValue observable,
             Object oldValue,
             Object newValue) {
        //Si se ha seleccionado una fila de la tabla, 
        //informar los campos Login, Nombre, 
        //Departamento y Perfil con los valores de la 
        //fila seleccionada. Habilitar los botones Crear,
        //Modificar y Eliminar. 
        if(newValue!=null){
            UserBean user=(UserBean)newValue;
            tfLogin.setText(user.getLogin());
            tfNombre.setText(user.getNombre());
            cbDepartamentos.getSelectionModel().select(user.getDepartamento());
            if(user.getPerfil().equals(Profile.ADMIN))tgPerfil.selectToggle(rbAdmin);
            else tgPerfil.selectToggle(rbUsuario);
            btCrear.setDisable(false);
            btModificar.setDisable(false);
            btEliminar.setDisable(false);
        }else{
        //Si se ha deseleccionado una fila de la tabla, 
        //vaciar los campos  Login, Nombre, 
        //Departamento y Perfil. Deshabilitar los 
        //botones Crear, Modificar y Eliminar.
            tfLogin.setText("");
            tfNombre.setText("");
            cbDepartamentos.getSelectionModel().clearSelection();
            tgPerfil.selectToggle(rbUsuario);
            btCrear.setDisable(true);
            btModificar.setDisable(true);
            btEliminar.setDisable(true);
        }
        tfLogin.requestFocus();
    }
    @FXML
    private void handleCrearAction(ActionEvent event){
        //Validar que no existe ya un usuario con el 
        //valor del campo login.
        try{
            usersManager.isLoginExisting(tfLogin.getText().trim());
            //Si no existe, agregar un usuario con los datos de los campos, 
            //actualizar la tabla de usuarios, vaciar todos los campos y 
            //deshabilitar los botones Crear y Modificar
            Profile perfil=Profile.USER;
            if(rbAdmin.isSelected())perfil=Profile.ADMIN;
            //User to add
            UserBean user=new UserBean(tfLogin.getText(),
                                 tfNombre.getText(),
                                 perfil,
                                 (DepartmentBean)cbDepartamentos.getSelectionModel().getSelectedItem());
            //add user to serverside
            this.usersManager.createUser(user);
            //add to TableView
            tbUsers.getItems().add(user);
            tfLogin.setText("");
            tfNombre.setText("");
            cbDepartamentos.getSelectionModel().clearSelection();
            tgPerfil.selectToggle(rbUsuario);
            btCrear.setDisable(true);
            btModificar.setDisable(true);
        }catch(BusinessLogicException e){
            Alert alert=new Alert(Alert.AlertType.ERROR,
                            e.getMessage(),
                            ButtonType.OK);
            alert.getDialogPane().getStylesheets().add(
            getClass().getResource("/javafxapplicationud3example/ui/view/customCascadeStyleSheet.css").toExternalForm());
            alert.showAndWait();
            tfLogin.requestFocus();
            tfLogin.setStyle("-fx-text-inner-color: red;");
        }
    }
    @FXML
    private void handleModificarAction(ActionEvent event){
        try{
            //Obtener el elemento seleccionado de la tabla
            UserBean selectedUser=((UserBean)tbUsers.getSelectionModel()
                                                    .getSelectedItem());
            //Comprobar si el login de la fila seleccionada 
            //coincide con el valor del campo login:
            if(!selectedUser.getLogin().equals(tfLogin.getText())){
                //Si no coincide Validar que no existe ya el login
                try{
                    usersManager.isLoginExisting(tfLogin.getText().trim());
                    selectedUser.setLogin(tfLogin.getText().trim());
                }catch(LoginExistsException e){
                    Alert alert=new Alert(Alert.AlertType.ERROR,
                                    e.getMessage(),
                                    ButtonType.OK);
                    alert.getDialogPane().getStylesheets().add(
                    getClass().getResource("/javafxapplicationud3example/ui/view/customCascadeStyleSheet.css").toExternalForm());
                    alert.showAndWait();
                    tfLogin.requestFocus();
                    tfLogin.setStyle("-fx-text-inner-color: red;");
                    return;
                }            
            }
            //Si no existe, modificar el usuario seleccionado en la tabla 
            //con los datos de los campos
            selectedUser.setNombre(tfNombre.getText().trim());
            Profile perfil=Profile.USER;
            if(rbAdmin.isSelected())perfil=Profile.ADMIN;
            selectedUser.setPerfil(perfil);
            selectedUser.setDepartamento((DepartmentBean)cbDepartamentos.getSelectionModel()
                                                                    .getSelectedItem());
            //modify user in serverside
            this.usersManager.updateUser(selectedUser);
            //Clean editing fields
            tfLogin.setText("");
            tfNombre.setText("");
            cbDepartamentos.getSelectionModel().clearSelection();
            tgPerfil.selectToggle(rbUsuario);
            btCrear.setDisable(true);
            btModificar.setDisable(true);
            //Deseleccionamos la fila seleccionada en la tabla
            tbUsers.getSelectionModel().clearSelection();
            //Refrescamos la tabla para que muestre los nuevos datos
            tbUsers.refresh();
        }catch(BusinessLogicException e){
            Alert alert=new Alert(Alert.AlertType.ERROR,
                            e.getMessage(),
                            ButtonType.OK);
            alert.getDialogPane().getStylesheets().add(
            getClass().getResource("/javafxapplicationud3example/ui/view/customCascadeStyleSheet.css").toExternalForm());
            alert.showAndWait();
        }
    }
    @FXML
    private void handleEliminarAction(ActionEvent event){
        Alert alert=null;
        try{
            //Obtener el elemento seleccionado de la tabla
            UserBean selectedUser=((UserBean)tbUsers.getSelectionModel()
                                                        .getSelectedItem());
            //Pedir confirmación para eliminar la fila seleccionada
            alert=new Alert(Alert.AlertType.CONFIRMATION,
                                    "¿Borrar la fila seleccionada?\n"
                                    + "Esta operación no se puede deshacer.",
                                    ButtonType.OK,ButtonType.CANCEL);
            alert.getDialogPane().getStylesheets().add(
                    getClass().getResource("/javafxapplicationud3example/ui/view/customCascadeStyleSheet.css").toExternalForm());
            Optional<ButtonType> result = alert.showAndWait();
            //If OK to deletion
            if (result.isPresent() && result.get() == ButtonType.OK) {
                //delete user from server side
                this.usersManager.deleteUser(selectedUser);
                //removes selected item from table
                tbUsers.getItems().remove(selectedUser);
                tbUsers.refresh();
                //clears editing fields
                tfLogin.setText("");
                tfNombre.setText("");
                cbDepartamentos.getSelectionModel().clearSelection();
                tgPerfil.selectToggle(rbUsuario);
                btCrear.setDisable(true);
                btModificar.setDisable(true);
                //Deseleccionamos la fila seleccionada en la tabla
                tbUsers.getSelectionModel().clearSelection();
                //Refrescamos la tabla para que muestre los nuevos datos
                tbUsers.refresh();
                }
        }catch(BusinessLogicException e){
            alert=new Alert(Alert.AlertType.ERROR,
                                e.getMessage(),
                                ButtonType.OK);
            alert.getDialogPane().getStylesheets().add(
            getClass().getResource("/javafxapplicationud3example/ui/view/customCascadeStyleSheet.css").toExternalForm());
            alert.showAndWait();
        }
    }
    @FXML
    private void handleImprimirAction(ActionEvent event){
        try {
            JasperReport report=
                JasperCompileManager.compileReport(getClass()
                    .getResourceAsStream("/javafxapplicationud3example/ui/report/newReport1.jrxml"));
            //Data for the report: a collection of UserBean passed as a JRDataSource 
            //implementation 
            JRBeanCollectionDataSource dataItems=
                    new JRBeanCollectionDataSource((Collection<UserBean>)this.tbUsers.getItems());
            //Map of parameter to be passed to the report
            Map<String,Object> parameters=new HashMap<>();
            //Fill report with data
            JasperPrint jasperPrint = JasperFillManager.fillReport(report,parameters,dataItems);
            //Create and show the report window.
            JasperViewer jasperViewer = new JasperViewer(jasperPrint);
            jasperViewer.setVisible(true);
        } catch (JRException ex) {
                Alert alert=new Alert(Alert.AlertType.ERROR,
                                ex.getMessage(),
                                ButtonType.OK);
                alert.getDialogPane().getStylesheets().add(
                getClass().getResource("/javafxapplicationud3example/ui/view/customCascadeStyleSheet.css").toExternalForm());
                alert.showAndWait();
        }
    }
    @FXML
    private void handleHelpAction(ActionEvent event){
        try{
            //Load node graph from fxml file
            FXMLLoader loader=
                new FXMLLoader(getClass().getResource("/javafxapplicationud3example/ui/view/Help.fxml"));
                Parent root = (Parent)loader.load();
                HelpController helpController=
                        ((HelpController)loader.getController());
                //Initializes and shows help stage
                helpController.initAndShowStage(root);
            }catch(IOException ex){
                Alert alert=new Alert(Alert.AlertType.ERROR,
                                      "No se ha podido abrir la ventana:"+
                                       ex.getMessage(),
                                       ButtonType.OK);
                alert.getDialogPane().getStylesheets().add(
                    getClass().getResource("/javafxapplicationud3example/ui/view/customCascadeStyleSheet.css").toExternalForm());
                alert.showAndWait();
            }

    }    
    @FXML
    private void handleSalirAction(ActionEvent event){
        //Se cierra la aplicación
        Platform.exit();
    }
    /**
     * Shows an error message in an alert dialog.
     * @param errorMsg The error message to be shown.
     */
    private void showErrorAlert(String errorMsg){
        //Shows error dialog.
        Alert alert=new Alert(Alert.AlertType.ERROR,
                              errorMsg,
                              ButtonType.OK);
        alert.getDialogPane().getStylesheets().add(
              getClass().getResource("/javafxapplicationud3example/ui/view/customCascadeStyleSheet.css").toExternalForm());
        alert.showAndWait();
        
    }

}
