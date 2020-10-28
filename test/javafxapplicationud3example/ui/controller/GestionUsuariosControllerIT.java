/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplicationud3example.ui.controller;

import java.util.List;
import java.util.Random;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import javafx.stage.Stage;
import javafxapplicationud3example.ApplicationUD3Example;
import javafxapplicationud3example.transferObjects.DepartmentBean;
import javafxapplicationud3example.transferObjects.Profile;
import javafxapplicationud3example.transferObjects.UserBean;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Ignore;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isDisabled;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import static org.testfx.matcher.control.ButtonMatchers.isCancelButton;
import static org.testfx.matcher.control.ButtonMatchers.isDefaultButton;
import static org.testfx.matcher.control.ComboBoxMatchers.hasSelectedItem;
import static org.testfx.matcher.control.TextInputControlMatchers.hasText;


/**
 * Testing class for User's manager view and controller. 
 * Tests login view behavior using TestFX framework.
 * @author javi
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class GestionUsuariosControllerIT extends ApplicationTest {

    private static final String OVERSIZED_TEXT="XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"+
                                               "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"+
                                               "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"+
                                               "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"+
                                               "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"+
                                               "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"+
                                               "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"+
                                               "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX";
    private TextField tfLogin;
    private TextField tfNombre;
    private TableView table;
    private Button btModificar;
    private Button btCrear;
    private RadioButton rbUser;
    private RadioButton rbAdmin; 
    private ComboBox cbDepartamentos;
    /**
     * Starts application to be tested.
     * @param stage Primary Stage object
     * @throws Exception If there is any error
     */
    @Override 
    public void start(Stage stage) throws Exception {
        //start JavaFX application to be tested    
        new ApplicationUD3Example().start(stage);
        //lookup for some nodes to be used in testing
        tfLogin=lookup("#tfLogin").query();
        tfNombre=lookup("#tfNombre").query();
        btModificar=lookup("#btModificar").query();
        btCrear=lookup("#btCrear").query();
        table=lookup("#tbUsers").queryTableView();
        rbUser=(RadioButton)lookup("#rbUsuario").query();
        rbAdmin=(RadioButton)lookup("#rbAdmin").query();
        cbDepartamentos=lookup("#cbDepartamentos").queryComboBox();
    }
    /**
     * This method allows to see users' table view by interacting with login 
     * view.
     */
    @Test
    public void testA_initialInteraction(){
        clickOn("#tfUsuario");
        write("username");
        clickOn("#tfPassword");
        write("password");
        clickOn("#btAceptar");
        verifyThat("#usersViewPane", isVisible());
    }
    /**
     * Test of initial state of users' table view.
     */
    @Test
    public void testB_initialState() {
        verifyThat("#tfLogin",  hasText(""));
        verifyThat("#tfNombre",  hasText(""));
        verifyThat("#btCrear", isDisabled());
        verifyThat("#btModificar", isDisabled());
        verifyThat("#rbUsuario", (RadioButton b) -> b.isSelected());
        verifyThat("#rbAdmin",  (RadioButton b) -> ! b.isSelected());
        verifyThat("#btSalir", isEnabled());
        verifyThat("#btImprimir", isEnabled());
        verifyThat("#btSalir", isEnabled());
        verifyThat("#btHelp", isEnabled());
        verifyThat("#tbUsers", isVisible());
        verifyThat("#tbcolLogin", isVisible());
        verifyThat("#tbcolNombre", isVisible());
        verifyThat("#tbcolPerfil", isVisible());
        verifyThat("#tbcolDepartamento", isVisible());
        verifyThat("#tfLogin",  (TextField t) -> t.isFocused());
    }
    /**
     * Test button Create is enabled when login, name, profile 
     * and department are all filled.
     */
    @Test
    //@Ignore
    public void testC_createIsEnabledDisabledOnFieldsChange() {
        //doubleClickOn("#tfLogin");
        doubleClickOn(tfLogin);
        write("anylogin");
        verifyThat("#btCrear", isDisabled());
        doubleClickOn(tfNombre);
        write("anyname");
        verifyThat("#btCrear", isEnabled());
        //clear last word from text fields
        tfLogin.clear();
        tfNombre.clear();
    }
    /**
     * Test button Modify is enabled when login, name, profile 
     * and department are all filled and a table row is selected.
     */
    @Test
    //@Ignore
    public void testE_modifyIsEnabledDisabledOnFieldsChange() {
        //check that table view has rows
        assertNotEquals("Table has no data: Cannot test.",
                        table.getItems().size(),0);
        //fill text fields
        doubleClickOn(tfLogin);
        write("anylogin");
        verifyThat("#btModificar", isDisabled());
        doubleClickOn(tfNombre);
        write("anyname");
        verifyThat("#btModificar", isDisabled());
        //look for 1st row in table view and click it
        Node row=lookup(".table-row-cell").nth(0).query();
        assertNotNull("Row is null: table has not that row. ",row);
        clickOn(row);
        //clickOn("#tbUsers");
        verifyThat("#btModificar", isEnabled());
        //clear text fields
        tfLogin.clear();
        tfNombre.clear();
    }
    /**
     * Test that an alert to confirm deletion appears and it does not delete 
     * when cancel.
     */
    @Test
    //@Ignore
    public void testF_cancelOnDeleteUser() {
        //get row count
        int rowCount=table.getItems().size();
        assertNotEquals("Table has no data: Cannot test.",
                        rowCount,0);
        //look for 1st row in table view and click it
        Node row=lookup(".table-row-cell").nth(0).query();
        assertNotNull("Row is null: table has not that row. ",row);
        clickOn(row);
        verifyThat("#eliminar", isEnabled());//note that id is used instead of fx:id
        clickOn("#eliminar");
        verifyThat("¿Borrar la fila seleccionada?\n"
                                    + "Esta operación no se puede deshacer.",
                    isVisible());    
        clickOn(isCancelButton());
        assertEquals("A row has been deleted!!!",rowCount,table.getItems().size());
    }
    /**
     * Test that user is deleted as ok button is clicked on confirmation dialog.
     */
    @Test
    //@Ignore
    public void testG_deleteUser() {
        //get row count
        int rowCount=table.getItems().size();
        assertNotEquals("Table has no data: Cannot test.",
                        rowCount,0);
        //look for 1st row in table view and click it
        Node row=lookup(".table-row-cell").nth(0).query();
        assertNotNull("Row is null: table has not that row. ",row);
        clickOn(row);
        verifyThat("#eliminar", isEnabled());//note that id is used instead of fx:id
        clickOn("#eliminar");
        verifyThat("¿Borrar la fila seleccionada?\n"
                                    + "Esta operación no se puede deshacer.",
                    isVisible());    
        clickOn(isDefaultButton());
        assertEquals("The row has not been deleted!!!",
                    rowCount-1,table.getItems().size());
        verifyThat(tfLogin,  (TextField t) -> t.isFocused());
    }
    /**
     * Test that an alert is shown when attempting to create a user with login
     * already being used by other user.
      */
    @Test
    //@Ignore
    public void testH_userExistingOnCreate() { 
       //get row count
        int rowCount=table.getItems().size();
        assertNotEquals("Table has no data: Cannot test.",
                        rowCount,0);
        //get an existing login from table data
        String login=((UserBean)table.getItems().get(0)).getLogin();
        //write that login on text field
        tfLogin.clear();
        clickOn(tfLogin);
        write(login);
        doubleClickOn(tfNombre);
        write("anyname");
        clickOn(btCrear);
        verifyThat("El login de usuario ya existe.\n"
                           +"Debe teclear un login que no exista.",
                    isVisible());    
        clickOn(isDefaultButton());
        assertEquals("A row has been added!!!",rowCount,table.getItems().size());
        verifyThat(tfLogin,  (TextField t) -> t.isFocused());        
    }   
    /**
     * Test user creation.
      */
    @Test
    //@Ignore
    public void testI_createUser() { 
        //get row count
        int rowCount=table.getItems().size();
        //get an existing login from random generator
        String login="username"+new Random().nextInt();
        //write that login on text field
        tfLogin.clear();
        clickOn(tfLogin);
        write(login);
        doubleClickOn(tfNombre);
        write("anyname");
        clickOn(btCrear);
        assertEquals("The row has not been added!!!",rowCount+1,table.getItems().size());
        //look for user in table data model
        List<UserBean> users=table.getItems();
        assertEquals("The user has not been added!!!",
                users.stream().filter(u->u.getLogin().equals(login)).count(),1);
        
    }   
    /**
     * Test that an alert is shown when attempting to modify a user with login
     * already being used by other user.
      */
    @Test
    //@Ignore
    public void testJ_userExistingOnModifiyingLogin() { 
        //get row count
        int rowCount=table.getItems().size();
        assertNotEquals("Table has no data: Cannot test.",
                        rowCount,0);
        //look for 1st row in table view and click it
        Node row=lookup(".table-row-cell").nth(0).query();
        assertNotNull("Row is null: table has not that row. ",row);
        clickOn(row);
        //get selected item from table
        UserBean selectedUser=(UserBean)table.getSelectionModel()
                                     .getSelectedItem();
        //get login from last row 
        String login=((UserBean)table.getItems()
                                     .get(table.getItems().size()-1))
                                     .getLogin();
        //write that login on text field
        tfLogin.clear();
        clickOn(tfLogin);
        write(login);
        doubleClickOn(tfNombre);
        write("anyname");
        clickOn(btModificar);
        verifyThat("El login de usuario pertenece a otro usuario.\n"+
                   "Debe definir un login que no exista para ningún\n"+
                   " otro usuario.",
                    isVisible());    
        clickOn(isDefaultButton());
        assertEquals("The row has been modified!!!",
                    selectedUser,(UserBean)table.getSelectionModel()
                                     .getSelectedItem());
        verifyThat(tfLogin,  (TextField t) -> t.isFocused());
    }
    /**
     * Test user modification.
      */
    @Test
    //@Ignore
    public void testK_modifyUser() { 
        //get row count
        int rowCount=table.getItems().size();
        assertNotEquals("Table has no data: Cannot test.",
                        rowCount,0);
        //look for 1st row in table view and click it
        Node row=lookup(".table-row-cell").nth(0).query();
        assertNotNull("Row is null: table has not that row. ",row);
        clickOn(row);
        //get selected item and index from table
        UserBean selectedUser=(UserBean)table.getSelectionModel()
                                     .getSelectedItem();
        int selectedIndex=table.getSelectionModel().getSelectedIndex();
        //Modify user data
        UserBean modifiedUser=new UserBean();

        modifiedUser.setLogin(selectedUser.getLogin()+new Random().nextInt());
        tfLogin.clear();
        clickOn(tfLogin);
        write(modifiedUser.getLogin());

        modifiedUser.setNombre(selectedUser.getNombre()+new Random().nextInt());
        tfNombre.clear();
        doubleClickOn(tfNombre);
        write(modifiedUser.getNombre());

        if(rbUser.isSelected()){
            rbAdmin.setSelected(true);
            modifiedUser.setPerfil(Profile.ADMIN);
        }
        else{
            rbUser.setSelected(true);
            modifiedUser.setPerfil(Profile.USER);         
        }
        
        clickOn(cbDepartamentos);
        int departmentCount=cbDepartamentos.getItems().size();
        if (departmentCount>1){
            if(selectedUser.getDepartamento().equals(cbDepartamentos.getItems().get(0))) 
                press(KeyCode.DOWN);
            else
                press(KeyCode.UP);
        }
        modifiedUser.setDepartamento((DepartmentBean)cbDepartamentos
                                    .getSelectionModel().getSelectedItem());
        clickOn(btModificar);
        //Assert modification.
        assertEquals("The user has not been modified!!!",
                     modifiedUser,
                     (UserBean)table.getItems().get(selectedIndex));
        verifyThat("#tfLogin",  (TextField t) -> t.isFocused());
    }
    /**
     * Test user table row selection.
     */
    @Test
    //@Ignore
    public void testL_tableSelection() { 
        //get row count
        int rowCount=table.getItems().size();
        assertNotEquals("Table has no data: Cannot test.",
                        rowCount,0);
        //look for 1st row in table view and select it
        Node row=lookup(".table-row-cell").nth(0).query();
        assertNotNull("Row is null: table has not that row. ",row);
        clickOn(row);
        //get selected item and index from table
        UserBean selectedUser=(UserBean)table.getSelectionModel()
                                     .getSelectedItem();
        int selectedIndex=table.getSelectionModel().getSelectedIndex();
        //assertions
        verifyThat(tfLogin,hasText(selectedUser.getLogin()));
        verifyThat(tfNombre,hasText(selectedUser.getNombre()));
        if(selectedUser.getPerfil()==Profile.ADMIN)
            verifyThat(rbAdmin,r->r.isSelected());
        else 
            verifyThat(rbUser,r->r.isSelected());
        verifyThat(cbDepartamentos,hasSelectedItem(selectedUser.getDepartamento()));
        verifyThat(btCrear,isEnabled());
        verifyThat(btModificar,isEnabled());
        verifyThat("#eliminar",isEnabled());
        //deselect row
        press(KeyCode.CONTROL);
        clickOn(row);
        release(KeyCode.CONTROL);
        //assertions
        verifyThat(tfLogin,hasText(""));
        verifyThat(tfNombre,hasText(""));
        verifyThat(btCrear,isDisabled());
        verifyThat(btModificar,isDisabled());
        verifyThat("#eliminar",isDisabled());
        verifyThat(tfLogin,  (TextField t) -> t.isFocused());
        
    }
    /**
     * Test maximum length of text fields' content and the corresponding alert 
     * message.
     */
    @Test
    //@Ignore
    public void testY_textFieldsMaxLength() {
       /* StringBuffer testText=new StringBuffer();
        for(int i=0;i<=15;i++) testText.append("XXXXXXXXXXXXXXXX");
        String oversizedText=new String(testText);*/
        doubleClickOn(tfLogin);
        write(OVERSIZED_TEXT);
        verifyThat("La longitud máxima del campo es de 255 caracteres.",isVisible());
        clickOn(isDefaultButton());
        doubleClickOn(tfLogin);
        tfLogin.clear();

        doubleClickOn(tfNombre);
        write(OVERSIZED_TEXT);
        verifyThat("La longitud máxima del campo es de 255 caracteres.",isVisible());
        clickOn(isDefaultButton());
        doubleClickOn(tfNombre);
        tfNombre.clear();
    } 
}
