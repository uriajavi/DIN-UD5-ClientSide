/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplicationud3example.ui.controller;

import javafx.stage.Stage;
import javafxapplicationud3example.businessLogic.UsersManager;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author jmarturi
 */
public class GenericControllerTest {
    
    public GenericControllerTest() {
    }

    /**
     * Test of setUsersManager method, of class GenericController.
     */
    @Test
    public void testSetUsersManager() {
        /*System.out.println("setUsersManager");
        UsersManager usersManager = null;
        GenericController instance = new GenericController();
        instance.setUsersManager(usersManager);*/
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getStage method, of class GenericController.
     */
    @Test
    public void testGetStage() {
        System.out.println("getStage");
        GenericController instance = new GenericController();
        Stage expResult = null;
        Stage result = instance.getStage();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setStage method, of class GenericController.
     */
    @Test
    public void testSetStage() {
        System.out.println("setStage");
        Stage stage = null;
        GenericController instance = new GenericController();
        instance.setStage(stage);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of showErrorAlert method, of class GenericController.
     */
    @Test
    public void testShowErrorAlert() {
        System.out.println("showErrorAlert");
        String errorMsg = "";
        GenericController instance = new GenericController();
        instance.showErrorAlert(errorMsg);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
