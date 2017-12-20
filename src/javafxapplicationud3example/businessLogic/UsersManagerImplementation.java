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
import javax.ws.rs.core.GenericType;

/**
 * Clase que implementa la interfaz de la lógica de negocio devolviendo datos
 * de obtenidos del servicio REST para la entidad User.
 * @author javi
 */
public class UsersManagerImplementation implements UsersManager{
    //REST users web client
    private UserRESTClient webClient;
    private static final Logger LOGGER=Logger.getLogger("javafxapplicationud3example");

    /**
     * Crea un objeto UsersManagerImplementation. Se construye un cliente de un 
     * servicio RESTful del que se obtienen los datos para la aplicación cliente.
     */
    public UsersManagerImplementation(){
        webClient=new UserRESTClient();
    }

    @Override
    public Collection<UserBean> getAllUsers() throws BusinessLogicException {
        LOGGER.info("UsersManager: Finding all users from REST service (XML).");
        List<UserBean> users = webClient.findAll_XML(new GenericType<List<UserBean>>() {});
        return users;
    }

    @Override
    public Collection<DepartmentBean> getAllDepartments() throws BusinessLogicException {
        LOGGER.info("UsersManager: Finding all users from REST service (XML).");
        List<DepartmentBean> departments = 
                webClient.findAllDeps_XML(new GenericType<List<DepartmentBean>>() {});
        return departments;
    }

    @Override
    public void isLoginExisting(String login) throws LoginExistsException {
        if(this.webClient.find_XML(UserBean.class, login)!=null)
            throw new LoginExistsException("Ya existe un usuario con ese login");
    }

    @Override
    public void createUser(UserBean user) throws BusinessLogicException {
        LOGGER.log(Level.INFO,"UsersManager: Creating user {0}.",user.getLogin());
        webClient.create_XML(user);
    }

    @Override
    public void updateUser(UserBean user) throws BusinessLogicException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteUser(UserBean user) throws BusinessLogicException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
}
