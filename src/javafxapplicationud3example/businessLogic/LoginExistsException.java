/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplicationud3example.businessLogic;

/**
 * Exception thrown when a Login already exists.
 * @author javi
 */
public class LoginExistsException extends BusinessLogicException {

    public LoginExistsException(String msg) {
        super(msg);
    }
    
}
