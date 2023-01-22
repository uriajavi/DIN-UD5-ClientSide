/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplicationud3example.businessLogic;

import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafxapplicationud3example.rest.UserRESTClient;
import javafxapplicationud3example.transferObjects.DepartmentBean;
import javafxapplicationud3example.transferObjects.UserBean;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.GenericType;

/**
 * This class implements {@link UsersManager} business logic interface using a 
 * RESTful web client to access bussines logic in an Java EE application server. 
 * @author javi
 */public class UsersManagerImplementation implements UsersManager{
    //REST users web client
    private UserRESTClient webClient;
    private static final Logger LOGGER=Logger.getLogger("javafxapplicationud3example");

    /**
     * Create a UsersManagerImplementation object. It constructs a web client for 
     * accessing a RESTful service that provides business logic in an application
     * server.
     */
    public UsersManagerImplementation(){
        webClient=new UserRESTClient();
    }
    /**
     * This method returns a Collection of {@link UserBean}, containing all users data.
     * @return Collection The collection with all {@link UserBean} data for users. 
     * @throws BusinessLogicException If there is any error while processing.
     */
    @Override
    public Collection<UserBean> getAllUsers() throws BusinessLogicException {
        List<UserBean> users =null;
        try{
            LOGGER.info("UsersManager: Finding all users from REST service (XML).");
            //Ask webClient for all users' data.
            users = webClient.findAll_XML(new GenericType<List<UserBean>>() {});
        }catch(Exception ex){
            LOGGER.log(Level.SEVERE,
                    "UsersManager: Exception finding all users, {0}",
                    ex.getMessage());
            throw new BusinessLogicException("Error finding all users:\n"+ex.getMessage());
        }
        return users;
    }
    /**
     * This method returns a collection of departments for users.
     * @return A collection of DepartmentBean.
     * @throws BusinessLogicException If there is any error while processing.
     */
    @Override
    public Collection<DepartmentBean> getAllDepartments() throws BusinessLogicException {
        List<DepartmentBean> departments = null;
        try{
            LOGGER.info("UsersManager: Finding all departments from REST service (XML).");
            //Ask webClient for all departments' data.
            departments = 
                webClient.findAllDeps_XML(new GenericType<List<DepartmentBean>>() {});
        }catch(Exception ex){
            LOGGER.log(Level.SEVERE,
                    "UsersManager: Exception finding all departments, {0}",
                    ex.getMessage());
            throw new BusinessLogicException("Error finding all departments:\n"+ex.getMessage());
        }
        return departments;
    }
    /**
     * This method checks if a user's login already exists, throwing an Exception 
     * if that's the case.
     * @throws LoginExistsException The Exception thrown in case login already exists
     */
    @Override
    public void isLoginExisting(String login) throws LoginExistsException,BusinessLogicException {
        try{
            if(this.webClient.find_XML(UserBean.class, login)!=null)
                throw new LoginExistsException("Ya existe un usuario con ese login");
        }catch(NotFoundException ex){
            //If there is a NotFoundException 404,that is,
            //the login does not exist, we catch the exception and do nothing. 
        }catch(WebApplicationException ex){
            LOGGER.log(Level.SEVERE,
                    "UsersManager: Exception checking login existence, {0}",
                    ex.getMessage());
            throw new BusinessLogicException("Error finding user:\n"+ex.getMessage());
        }
    }
    /**
     * This method adds a new created UserBean. This is done by sending a POST 
     * request to a RESTful web service.
     * @param user The UserBean object to be added.
     * @throws BusinessLogicException If there is any error while processing.
     */
    @Override
    public void createUser(UserBean user) throws BusinessLogicException {
        try{
            LOGGER.log(Level.INFO,"UsersManager: Creating user {0}.",user.getLogin());
            //Send user data to web client for creation. 
            webClient.create_XML(user);
        }catch(Exception ex){
            LOGGER.log(Level.SEVERE,
                    "UsersManager: Exception creating user, {0}",
                    ex.getMessage());
            throw new BusinessLogicException("Error creating user:\n"+ex.getMessage());
        }
    }
    /**
     * This method updates data for an existing UserBean data for user. 
     * This is done by sending a PUT request to a RESTful web service.
     * @param user The UserBean object to be updated.
     * @throws BusinessLogicException If there is any error while processing.
     */
    @Override
    public void updateUser(UserBean user) throws BusinessLogicException {
        try{
            LOGGER.log(Level.INFO,"UsersManager: Updating user {0}.",user.getLogin());
            webClient.update_XML(user);
        }catch(Exception ex){
            LOGGER.log(Level.SEVERE,
                    "UsersManager: Exception updating user, {0}",
                    ex.getMessage());
            throw new BusinessLogicException("Error updating user:\n"+ex.getMessage());
        }
    }
    /**
     * This method deletes data for an existing user. 
     * This is done by sending a DELETE request to a RESTful web service.
     * @param user The UserBean object to be deleted.
     * @throws BusinessLogicException If there is any error while processing.
     */
    @Override
    public void deleteUser(UserBean user) throws BusinessLogicException {
        try{
            LOGGER.log(Level.INFO,"UsersManager: Deleting user {0}.",user.getLogin());
            webClient.delete(user.getLogin());
        }catch(Exception ex){
            LOGGER.log(Level.SEVERE,
                    "UsersManager: Exception deleting user, {0}",
                    ex.getMessage());
            throw new BusinessLogicException("Error deleting user:\n"+ex.getMessage());
        }
    }

    
}
