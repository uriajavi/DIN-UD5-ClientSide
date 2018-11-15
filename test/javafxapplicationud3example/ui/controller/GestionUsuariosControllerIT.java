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
import static org.testfx.matcher.base.WindowMatchers.isShowing;
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
    public void testA_InitialInteraction(){
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
    public void testB_InitialState() {
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
    public void testD_CreateisEnabledWhenItShould() {
        doubleClickOn("#tfLogin");
        write("any login");
        verifyThat("#btCrear", isDisabled());
        doubleClickOn("#tfNombre");
        write("any name");
        verifyThat("#btCrear", isEnabled());
        //clear last word from text fields
        doubleClickOn("#tfLogin");
        write(" ");
        doubleClickOn("#tfNombre");
        write(" ");
    }
    /**
     * Test button Modify is enabled when login, name, profile 
     * and department are all filled and a table row is selected.
     */
    @Test
    public void testE_ModifyisEnabledWhenItShould() {
        doubleClickOn("#tfLogin");
        write("any login");
        verifyThat("#btModificar", isDisabled());
        doubleClickOn("#tfNombre");
        write("any name");
        verifyThat("#btModificar", isDisabled());
        clickOn("#tbUsers");
        verifyThat("#btModificar", isEnabled());
        //clear last word from text fields
        doubleClickOn("#tfLogin");
        write(" ");
        doubleClickOn("#tfNombre");
        write(" ");
    }
    /**
     * Test maximum length of text fields' content and the corresponding alert 
     * message.
     */
    @Test
    public void testC_TextFieldsMaxLength() {
       /* StringBuffer testText=new StringBuffer();
        for(int i=0;i<=15;i++) testText.append("XXXXXXXXXXXXXXXX");
        String oversizedText=new String(testText);*/
        doubleClickOn("#tfLogin");
        write(OVERSIZED_TEXT);
        verifyThat("La longitud máxima del campo es de 255 caracteres.",isVisible());
        clickOn("OK");
        doubleClickOn("#tfLogin");
        write(" ");
        doubleClickOn("#tfNombre");
        write(OVERSIZED_TEXT);
        verifyThat("La longitud máxima del campo es de 255 caracteres.",isVisible());
        clickOn("OK");
        doubleClickOn("#tfNombre");
        write(" ");
    }
    
}
