/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplicationud3example.businessLogic;

import java.util.Collection;


/**
 * Interfaz que encapsula los métodos de negocio para la gestión de usuarios.
 * @author javi
 */
public interface UsersManager {
    
    public Collection getAllUsers() throws BusinessLogicException;
    public void isLoginExisting(String login) throws LoginExistsException;
    
}
