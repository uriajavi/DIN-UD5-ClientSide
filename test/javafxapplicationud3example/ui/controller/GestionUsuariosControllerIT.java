/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplicationud3example.ui.controller;

import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import javafx.stage.Stage;
import javafxapplicationud3example.ApplicationUD3Example;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isDisabled;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import static org.testfx.matcher.control.TextInputControlMatchers.hasText;


/**
 * Testing class for User's manager view and controller. 
 * Tests login view behavior using TestFX framework.
 * @author javi
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class GestionUsuariosControllerIT extends ApplicationTest {
    /**
     * Starts application to be tested.
     * @param stage Primary Stage object
     * @throws Exception If there is any error
     */
    @Override 
    public void start(Stage stage) throws Exception {
            new ApplicationUD3Example().start(stage);
    }
    /**
     * This method allows to see users' table view by interacting with login 
     * view.
     */
    @Test
    public void test1_InitialInteraction(){
            clickOn("#tfUsuario");
            write("username");
            clickOn("#tfPassword");
            write("password");
            clickOn("#btAceptar");
    }
    /**
     * Test of initial state of users' table view.
     */
    @Test
    public void test2_InitialState() {
        verifyThat("#usersViewPane", isVisible());
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
    public void test3_CreateisEnabledWhenItShould() {
        clickOn("#tfLogin");
        write("any login");
        verifyThat("#btCrear", isDisabled());
        clickOn("#tfNombre");
        write("any user name");
        verifyThat("#btCrear", isEnabled());
    }
    /**
     * Test button Modify is enabled when login, name, profile 
     * and department are all filled and a table row is selected.
     */
    @Test
    public void test4_ModifyisEnabledWhenItShould() {
        clickOn("#tfLogin");
        write("any login");
        verifyThat("#btModificar", isDisabled());
        clickOn("#tfNombre");
        write("any user name");
        verifyThat("#btModificar", isDisabled());
        clickOn("#tbUsers");
        verifyThat("#btModificar", isEnabled());
    }
    
}
