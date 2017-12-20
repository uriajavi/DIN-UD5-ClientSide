/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplicationud3example.businessLogic;

import java.util.Collection;
import javafxapplicationud3example.transferObjects.DepartmentBean;
import javafxapplicationud3example.transferObjects.UserBean;


/**
 * Interfaz que encapsula los métodos de negocio para la gestión de usuarios.
 * @author javi
 */
public interface UsersManager {
    
    public Collection<UserBean> getAllUsers() throws BusinessLogicException;
    public Collection<DepartmentBean> getAllDepartments() throws BusinessLogicException;
    public void createUser(UserBean user) throws BusinessLogicException;
    public void updateUser(UserBean user) throws BusinessLogicException;
    public void deleteUser(UserBean user) throws BusinessLogicException;
    public void isLoginExisting(String login) throws LoginExistsException;
    
}
