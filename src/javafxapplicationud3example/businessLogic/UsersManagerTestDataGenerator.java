/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplicationud3example.businessLogic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;
import java.util.logging.Logger;
import javafxapplicationud3example.transferObjects.DepartmentBean;
import javafxapplicationud3example.transferObjects.Profile;
import javafxapplicationud3example.transferObjects.UserBean;

/**
 * This class implements {@link UsersManager} business logic interface generating
 * fake data for testing purposes. 
 * @author javi
 */
public class UsersManagerTestDataGenerator implements UsersManager {
    private static final Logger LOGGER=Logger.getLogger("javafxapplicationud3example");
    // List for storing users data.
    private ArrayList<UserBean> users;
    private ArrayList<DepartmentBean> departments;
    /**
     * This methods builds 25 instances of {@link UserBean} objects into an ArrayList.
     */
    public UsersManagerTestDataGenerator(){
        LOGGER.info("Building fake users data for testing UI.");
        users=new ArrayList();
        departments=new ArrayList();
        //Create DepartmentBean fake data for users data.
        DepartmentBean department=new DepartmentBean();
        department.setId(1);
        department.setName("Informática");
        department.setDescription("Departamento de Informática");
        departments.add(department);
        //Create 25 UserBean fake data objects.
        for(int i=0;i<25;i++)
            users.add(new UserBean("login"+i,"nombre"+i,Profile.USER,department));
    }
    /**
     * This method returns a Collection of {@link UserBean}, containing all users data.
     * @return Collection The collection with all {@link UserBean} data for users. 
     */
    @Override
    public Collection getAllUsers() throws BusinessLogicException{
        LOGGER.info("Getting all fake users data for UI.");
        return users;
    }
    /**
     * Check if a user's login already exists, throwing an Exception if that's the case.
     * @throws LoginExistsException 
     */
    @Override
    public void isLoginExisting(String login) throws LoginExistsException {
        LOGGER.info("Validating Login existence.");
        if (users.stream().filter(user->user.getLogin().equals(login)).count()!=0){
            LOGGER.severe("Login already exists.");
            throw new LoginExistsException("Login ya existe.");
        }
    }
    /**
     * This method returns a collection of departments for fake data test.
     * @return A collection of DepartmentBean.
     * @throws BusinessLogicException 
     */
    @Override
    public Collection<DepartmentBean> getAllDepartments() throws BusinessLogicException {
        LOGGER.info("Getting all fake departments data for UI.");
        return departments;
    }
    /**
     * This method adds a new created UserBean to a test collection.
     * @param user The UserBean object to be added.
     * @throws BusinessLogicException 
     */
    @Override
    public void createUser(UserBean user) throws BusinessLogicException {
        users.add(user);
    }
    /**
     * This method updates a UserBean into the test collection. 
     * @param user The UserBean object to be updated.
     * @throws BusinessLogicException 
     */
    @Override
    public void updateUser(UserBean user) throws BusinessLogicException {
     /*It does nothing
     * because the objects in the collection are updated in the UI controller through
     * an observable list of UserBeans, that have also observable properties. That 
     * observable list is the same test collection containing the same objects as 
     * users list in this class.
     */   
    }
    /**
     * This method deletes a UserBean data from the test collection.
     * @param user The UserBean object to be deleted.
     * @throws BusinessLogicException 
     */
    @Override
    public void deleteUser(UserBean user) throws BusinessLogicException {
        users.remove(user);
    }
    
}