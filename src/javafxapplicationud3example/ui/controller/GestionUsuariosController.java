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
import java.util.logging.Level;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafxapplicationud3example.businessLogic.BusinessLogicException;
import javafxapplicationud3example.businessLogic.LoginExistsException;
import javafxapplicationud3example.transferObjects.DepartmentBean;
import javafxapplicationud3example.transferObjects.Profile;
import javafxapplicationud3example.transferObjects.UserBean;
import javax.swing.WindowConstants;
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
 * @author javi
 */
public class GestionUsuariosController extends GenericController{
    /**
     * Create user data button.
     */
    @FXML
    private Button btCrear;
    /**
     * Modify user data button.
     */
    @FXML
    private Button btModificar;
    /**
     * Delete user data button.
     */
    @FXML
    private Button btEliminar;
    /**
     * Quit application button.
     */
    @FXML
    private Button btSalir;
    /**
    * User's login name UI text field.
    */
    @FXML
    private TextField tfLogin;
    /**
    * User's name UI text field.
    */
    @FXML
    private TextField tfNombre;
    /**
    * USER profile option button.
    */
    @FXML
    private RadioButton rbUsuario;
    /**
    * ADMIN profile option button.
    */
    @FXML
    private RadioButton rbAdmin;
    /**
    * User's profile options group.
    */
    @FXML
    private ToggleGroup tgPerfil;
    /**
    * Department's combo box.
    */
    @FXML
    private ComboBox cbDepartamentos;
    /**
    * User's data table view.
    */
    @FXML
    private TableView tbUsers;
    /**
    * User's login data table column.
    */
    @FXML
    private TableColumn tbcolLogin;
    /**
    * User's name data table column.
    */
    @FXML
    private TableColumn tbcolNombre;
    /**
    * User's profile data table column.
    */
    @FXML
    private TableColumn tbcolPerfil;
    /**
    * User's department data table column.
    */
    @FXML
    private TableColumn tbcolDepartamento;
    /**
    * User's department data table column.
    */
    @FXML
    private TableColumn tbcolStatus;
    /**
     * User's table data model.
     */
    private ObservableList<UserBean> usersData;
    /**
     * Help view showing button.
     */
    @FXML
    private Button btHelp;
    /**
     * Method for initializing GestionUsuarios Stage. 
     * @param root The Parent object representing root node of view graph.
     */
    public void initStage(Parent root) {
        try{
            Scene scene = new Scene(root);
            stage = new Stage();
            //Set stage properties
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.setTitle("Gestion de Usuarios");
            stage.setResizable(false);
            stage.setOnShowing(this::handleWindowShowing);
            //Add property change listeners for controls
            tfLogin.textProperty().addListener(this::handleTextChanged);
            tfNombre.textProperty().addListener(this::handleTextChanged);
            tbUsers.getSelectionModel().selectedItemProperty()
                    .addListener(this::handleUsersTableSelectionChanged);
            
            //These following lines are an example workaround to allow Menu Help 
            //to fire an action.It is preferable to avoid this but if you need it...
            
            //Obtains the layout containing the menu bar from the scene node graph
            HBox hBoxMenu= (HBox)root.getChildrenUnmodifiable().get(0);
            //Get the menu bar from the children of the layout got before
            MenuBar menuBar= (MenuBar)hBoxMenu.getChildren().get(0);
            //Get the second menu from the menu bar
            Menu menuHelp=menuBar.getMenus().get(1);
            //Add a listener for the showing property that fires the action event
            //on the first menu item of that menu
            menuHelp.showingProperty().addListener(
                (observableValue, oldValue, newValue) -> {
                    if (newValue) {
                        menuHelp.getItems().get(0).fire();
                    }
                }
            );
            //Set department combo data model.
            ObservableList<DepartmentBean> departments=
                    FXCollections.observableArrayList(usersManager.getAllDepartments());
            cbDepartamentos.setItems(departments);
            //Add focus event handler.
            //tfLogin.focusedProperty().addListener(this::focusChanged);
            //Set factories for cell values in users table columns.
            tbcolLogin.setCellValueFactory(
                    new PropertyValueFactory<>("login"));
            tbcolDepartamento.setCellValueFactory(
                    new PropertyValueFactory<>("departamento"));
            tbcolNombre.setCellValueFactory(
                    new PropertyValueFactory<>("nombre"));
            tbcolPerfil.setCellValueFactory(
                    new PropertyValueFactory<>("perfil"));
            
            //Following code show how a editable checkbox cell must be implemented.
            //YOU MUST SEE ALSO UserBean class for column status property support!!!.
            
            //First, set the column cell factory:
            tbcolStatus.setCellFactory(
                    CheckBoxTableCell.<UserBean>forTableColumn(tbcolStatus));
            //then, set the value cell factory:
            tbcolStatus.setCellValueFactory(
                    new PropertyValueFactory<>("status"));
            //Create an observable list for users table.
            usersData=FXCollections.observableArrayList(usersManager.getAllUsers());
            //Now the key step!!!: add a listener for checkbox value changes noting that the
            //CheckBoxTableCell renders the CheckBox 'live', meaning that the 
            //CheckBox is always interactive. A side-effect of this is that the 
            //usual editing callbacks (such as on edit commit) will not be called. 
            //If you want to be notified of changes, it is recommended to directly 
            //observe the boolean properties that are manipulated by the CheckBox 
            //(see description for CheckBoxTableCell in javadoc)
            //So we iterate on table items adding listeners for property being 
            //represented by the checkbox.
            //We use the lambda implementation to access the user object in which
            //the status property is.
            usersData.forEach(
                    user -> user.statusProperty()
                                .addListener((observable, oldValue, newValue) -> {
                        LOGGER.log(Level.INFO,
                                     "Status property changed.newvalue {0}",
                                      newValue.toString());
                        LOGGER.log(Level.INFO,             
                                     "User modified: {0}",
                                      user.getNombre());
                    })
            );
            //for(UserBean user: usersData)
            //    user.statusProperty()
            //        .addListener(this::handleStatusPropertyChange);
           
            //Set table model.
            tbUsers.setItems(usersData);
            //finally,set table as editable
            tbUsers.setEditable(true);
            //Show window.
            stage.show();
        }catch(BusinessLogicException e){
            showErrorAlert("No se ha podido abrir la ventana.\n"+
                            e.getMessage());
        }
    }
    
    /**
     * Status property change handler. Note that in this method WE CANNOT GET
     * THE USER OBJECT FOR THE STATUS PROPERTY!!!.
     * @param observable
     * @param oldValue
     * @param newValue 
     */
    private void handleStatusPropertyChange(ObservableValue observable, 
                                            Boolean oldValue, Boolean newValue){
        LOGGER.log(Level.INFO,
                        "Status property changed.newvalue {0}",
                       newValue.toString());
    }
    /**
     * Initializes window state. It implements behavior for WINDOW_SHOWING type 
     * event.
     * @param event  The window event 
     */
    private void handleWindowShowing(WindowEvent event){
        LOGGER.info("Beginning GestionUsuariosController::handleWindowShowing");
        //Select USER profile by default.
        tgPerfil.selectToggle(rbUsuario);
        //Select first department by default
        cbDepartamentos.getSelectionModel().selectFirst();
        //Create, modify and delete buttons are disabled.
        btCrear.setDisable(true);
        btModificar.setDisable(true);
        btEliminar.setDisable(true);
        //Set prompt text for login and name text fields.
        tfLogin.setPromptText("Introduzca un id...");
        tfNombre.setPromptText("Introduzca nombre y apellidos...");
        //Login text field gets the focus.
        tfLogin.requestFocus();
    }
    /**
     * A focus change event event handler. This is an example that only logs a message.
     * @param observable the observable focus property.
     * @param oldValue the old boolean value for the property.
     * @param newValue the new boolean value for the property.
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
     * Text change event handler for login and name text fields.
     * It enables or disables create and modify buttons depending on those fields state.
     * @param observable the property being observed: TextProperty of TextField.
     * @param oldValue   old String value for the property.
     * @param newValue   new String value for the property.
     */
    private void handleTextChanged(ObservableValue observable,
             String oldValue,
             String newValue) {
        //Validate maximum length for login & name fields
        //If text fields values are too long, show error message and disable 
        //accept button
        if(tfNombre.getText().trim().length()>this.MAX_LENGTH ||
           tfLogin.getText().trim().length()>this.MAX_LENGTH){
            showErrorAlert("La longitud máxima del campo es de 255 caracteres.");
            btCrear.setDisable(true);
            btModificar.setDisable(true);
        }
        //Validate login & name fields are not empty
        else if(tfNombre.getText().trim().isEmpty()||
           tfLogin.getText().trim().isEmpty() ){
            //If they are empty, disable create ans modify buttons
            btCrear.setDisable(true);
            btModificar.setDisable(true);
        }else{
            //If they are not empty, enables create button and, if there is also
            // a row selected in users table, enable modify button.
            btCrear.setDisable(false);
            if(!tbUsers.getSelectionModel().isEmpty())
                btModificar.setDisable(false);
        }
        //Set color for text in login to black (it can be red if it wasn't valid).
        tfLogin.setStyle("-fx-text-inner-color: black;");
    }
    /**
     * Users table selection changed event handler. It enables or disables buttons
     * depending on selection state of the table.
     * @param observable the property being observed: SelectedItem Property
     * @param oldValue   old UserBean value for the property.
     * @param newValue   new UserBean value for the property.
     */
    private void handleUsersTableSelectionChanged(ObservableValue observable,
             Object oldValue,
             Object newValue) {
        //If there is a row selected, move row data to corresponding fields in the
        //window and enable create, modify and delete buttons
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
        //If there is not a row selected, clean window fields 
        //and disable create, modify and delete buttons
            tfLogin.setText("");
            tfNombre.setText("");
            //cbDepartamentos.getSelectionModel().clearSelection();
            tgPerfil.selectToggle(rbUsuario);
            btCrear.setDisable(true);
            btModificar.setDisable(true);
            btEliminar.setDisable(true);
        }
        //Focus login field
        tfLogin.requestFocus();
    }
    /**
     * Action event handler for create button. It validates new user data, send it
     * to the business logic tier and updates user table view with new user data.
     * @param event The ActionEvent object for the event.
     */
    @FXML
    private void handleCrearAction(ActionEvent event){
        try{
            //Check if the is already a user with the login value defined in 
            //the window
            usersManager.isLoginExisting(tfLogin.getText().trim());
            //If the login does not exist, add new user data to a new UserBean
            Profile perfil=Profile.USER;
            if(rbAdmin.isSelected())perfil=Profile.ADMIN;
            UserBean user=new UserBean(tfLogin.getText().trim(),
                                 tfNombre.getText().trim(),
                                 perfil,
                                 (DepartmentBean)cbDepartamentos.getSelectionModel().getSelectedItem(),true);
            //Send user data to business logic tier
            this.usersManager.createUser(user);
            //Add to user data to TableView model
            tbUsers.getItems().add(user);
            //Clean fields
            tfLogin.setText("");
            tfNombre.setText("");
            //cbDepartamentos.getSelectionModel().clearSelection();
            tgPerfil.selectToggle(rbUsuario);
            btCrear.setDisable(true);
            btModificar.setDisable(true);
        }catch(LoginExistsException e){
            //If Login exist show error message, focus login field and set its text 
            //color to red.
            showErrorAlert("El login de usuario ya existe.\n"+
                           "Debe teclear un login que no exista.");
            tfLogin.requestFocus();
            tfLogin.setStyle("-fx-text-inner-color: red;");
            LOGGER.severe("El login de usuario ya existe.");
        }catch(BusinessLogicException e){
            //If there is an error in the business logic tier show message and
            //log it.
            showErrorAlert("Error al crear usuario:\n"+
                            e.getMessage());
            LOGGER.log(Level.SEVERE,
                        "UI GestionUsuariosController: Error creating user: {0}",
                        e.getMessage());
        }
    }
    /** 
     * Action event handler for modify button. It validates user data, send it
     * to the business logic tier and updates user table view with new user data.
     * @param event The ActionEvent object for the event.
     */
    @FXML
    private void handleModificarAction(ActionEvent event){
        try{
            //Get selected user data from table view.
            UserBean selectedUser=((UserBean)tbUsers.getSelectionModel()
                                                    .getSelectedItem());
            //Check if login value for selected row in table 
            //is equal to login field content.
            if(!selectedUser.getLogin().equals(tfLogin.getText())){
                //If not, validate login existence.
                    usersManager.isLoginExisting(tfLogin.getText().trim());
                    selectedUser.setLogin(tfLogin.getText().trim());
            }
            //update selected row data in table view 
            selectedUser.setNombre(tfNombre.getText().trim());
            Profile perfil=Profile.USER;
            if(rbAdmin.isSelected())perfil=Profile.ADMIN;
            selectedUser.setPerfil(perfil);
            selectedUser.setDepartamento((DepartmentBean)cbDepartamentos.getSelectionModel()
                                                                    .getSelectedItem());
            //If login value does not exist: 
            //send data to modify user data in business tier
            this.usersManager.updateUser(selectedUser);
            //Clean entry text fields
            tfLogin.setText("");
            tfNombre.setText("");
            //cbDepartamentos.getSelectionModel().clearSelection();
            tgPerfil.selectToggle(rbUsuario);
            btCrear.setDisable(true);
            btModificar.setDisable(true);
            //Deseleccionamos la fila seleccionada en la tabla
            tbUsers.getSelectionModel().clearSelection();
            //Refrescamos la tabla para que muestre los nuevos datos
            tbUsers.refresh();
        }catch(LoginExistsException e){
            //If Login exist show error message, focus login field and set its text 
            //color to red.
            showErrorAlert("El login de usuario pertenece a otro usuario.\n"+
                           "Debe definir un login que no exista para ningún\n"+
                           " otro usuario.");
            tfLogin.requestFocus();
            tfLogin.setStyle("-fx-text-inner-color: red;");
        }catch(BusinessLogicException e){
            //If there is an error in the business logic tier show message and
            //log it.
            showErrorAlert("Error al modificar usuario:\n"+
                            e.getMessage());
            LOGGER.log(Level.SEVERE,
                        "UI GestionUsuariosController: Error updating user: {0}",
                        e.getMessage());
        }
    }
    /**
     * Action event handler for delete button. It asks user for confirmation on delete,
     * sends delete message to the business logic tier and updates user table view.
     * @param event The ActionEvent object for the event.
     */
    @FXML
    private void handleEliminarAction(ActionEvent event){
        try{
            LOGGER.info("Deleting user...");
            //Get selected user data from table view model
            UserBean selectedUser=((UserBean)tbUsers.getSelectionModel()
                                                        .getSelectedItem());
            //Ask user for confirmation on delete
            Alert alert=new Alert(Alert.AlertType.CONFIRMATION,
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
                //cbDepartamentos.getSelectionModel().clearSelection();
                tgPerfil.selectToggle(rbUsuario);
                btCrear.setDisable(true);
                btModificar.setDisable(true);
                //Clear selection and refresh table view 
                tbUsers.getSelectionModel().clearSelection();
                tbUsers.refresh();
            }
        }catch(BusinessLogicException e){
            //If there is an error in the business logic tier show message and
            //log it.
            showErrorAlert("Error al borrar usuario:\n"+
                            e.getMessage());
            LOGGER.log(Level.SEVERE,
                        "UI GestionUsuariosController: Error deleting user: {0}",
                        e.getMessage());
        }
    }
    /**
     * Action event handler for print button. It shows a JFrame containing a report.
     * This JFrame allows to print the report.
     * @param event The ActionEvent object for the event.
     */
    @FXML
    private void handleImprimirAction(ActionEvent event){
        try {
            LOGGER.info("Beginning printing action...");
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
            //Create and show the report window. The second parameter false value makes 
            //report window not to close app.
            JasperViewer jasperViewer = new JasperViewer(jasperPrint,false);
            jasperViewer.setVisible(true);
           // jasperViewer.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        } catch (JRException ex) {
            //If there is an error show message and
            //log it.
            showErrorAlert("Error al imprimir:\n"+
                            ex.getMessage());
            LOGGER.log(Level.SEVERE,
                        "UI GestionUsuariosController: Error printing report: {0}",
                        ex.getMessage());
        }
    }
    /**
     * Action event handler for help button. It shows a Stage containing a scene 
     * with a web viewer showing a help page for the window.
     * @param event The ActionEvent object for the event.
     */
    @FXML
    private void handleHelpAction(ActionEvent event){
        try{
            LOGGER.info("Loading help view...");
            //Load node graph from fxml file
            FXMLLoader loader=
                new FXMLLoader(getClass().getResource("/javafxapplicationud3example/ui/view/Help.fxml"));
                Parent root = (Parent)loader.load();
                HelpController helpController=
                        ((HelpController)loader.getController());
                //Initializes and shows help stage
                helpController.initAndShowStage(root);
        }catch(Exception ex){
                //If there is an error show message and
                //log it.
                showErrorAlert("Error al mostrar ventana de ayuda:\n"+
                                ex.getMessage());
                LOGGER.log(Level.SEVERE,
                            "UI GestionUsuariosController: Error loading help window: {0}",
                            ex.getMessage());
        }

    }
    /**
     * Action event handler for exit button. It closes the application.
     * @param event The ActionEvent object for the event.
     */
    @FXML
    private void handleSalirAction(ActionEvent event){
        LOGGER.info("Closing application.");
        //Closes application.
        Platform.exit();
    }    
}
