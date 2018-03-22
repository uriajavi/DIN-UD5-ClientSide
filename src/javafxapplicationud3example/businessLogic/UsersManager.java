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
 * Business logic interface encapsulating business methods for users management.
 * @author javi
 */
public interface UsersManager {
    /**
     * This method returns a Collection of {@link UserBean}, containing all users data.
     * @return Collection The collection with all {@link UserBean} data for users. 
     * @throws BusinessLogicException If there is any error while processing.
     */
    public Collection<UserBean> getAllUsers() throws BusinessLogicException;
    /**
     * This method returns a collection of departments for users.
     * @return A collection of DepartmentBean.
     * @throws BusinessLogicException If there is any error while processing.
     */
    public Collection<DepartmentBean> getAllDepartments() throws BusinessLogicException;
    /**
     * This method adds a new created UserBean.
     * @param user The UserBean object to be added.
     * @throws BusinessLogicException If there is any error while processing.
     */
    public void createUser(UserBean user) throws BusinessLogicException;
    /**
     * This method updates data for an existing UserBean data for user. 
     * @param user The UserBean object to be updated.
     * @throws BusinessLogicException If there is any error while processing.
     */
    public void updateUser(UserBean user) throws BusinessLogicException;
    /**
     * This method deletes data for an existing user. 
     * @param user The UserBean object to be deleted.
     * @throws BusinessLogicException If there is any error while processing.
     */
    public void deleteUser(UserBean user) throws BusinessLogicException;
    /**
     * This method checks if a user's login already exists, throwing an Exception 
     * if that's the case.
     * @param login The login value to be checked.
     * @throws LoginExistsException The Exception thrown in case login already exists
     */
    public void isLoginExisting(String login) throws LoginExistsException;
    
}
