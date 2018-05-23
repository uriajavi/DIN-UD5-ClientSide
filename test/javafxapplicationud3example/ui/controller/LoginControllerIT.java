/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplicationud3example.ui.controller;

import javafx.stage.Stage;
import javafxapplicationud3example.ApplicationUD3Example;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isDisabled;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import static org.testfx.matcher.control.TextInputControlMatchers.hasText;
/**
 * Testing class for Login view and controller. 
 * Tests login view behavior using TestFX framework.
 * @author javi
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LoginControllerIT extends ApplicationTest {
    /**
     * Starts application to be tested.
     * @param stage Primary Stage object
     * @throws Exception If there is any error
     */
    @Override public void start(Stage stage) throws Exception {
       new ApplicationUD3Example().start(stage);
    }
    /**
     * Stops application to be tested: it does nothing.
     */
    @Override public void stop() {}
    /**
     * Test of initial state of login view.
     */
    @Test
    public void test1_InitialState() {
        verifyThat("#tfUsuario", hasText(""));
        verifyThat("#tfPassword",hasText(""));
        verifyThat("#btAceptar", isDisabled());
    }
    /**
     * Test test that button Aceptar is disabled if user or password fields are empty.
    */ 
    @Test
    public void test2_AceptarIsDisabled() {
        clickOn("#tfUsuario");
        write("username");
        verifyThat("#btAceptar", isDisabled());
        eraseText(8);
        clickOn("#tfPassword");
        write("password");
        verifyThat("#btAceptar", isDisabled());
        eraseText(8);
        verifyThat("#btAceptar", isDisabled());
    }
    /**
     * Test test that button Aceptar is enabled when user and password fields are full.
    */ 
    @Test
    public void test3_AceptarIsEnabled() {
        clickOn("#tfUsuario");
        write("username");
        clickOn("#tfPassword");
        write("password");
        verifyThat("#btAceptar", isEnabled());
    }
    /**
     * Test test that user's manager view is opened when button Aceptar is 
     * clicked
    */ 
    @Test
    public void test4_UsersViewOpenedOnAceptarClick() {
        clickOn("#tfUsuario");
        write("username");
        clickOn("#tfPassword");
        write("password");
        clickOn("#btAceptar");
        verifyThat("#usersViewPane", isVisible());
    }
}
