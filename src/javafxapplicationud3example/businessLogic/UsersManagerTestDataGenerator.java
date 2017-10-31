/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplicationud3example.businessLogic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Logger;
import javafxapplicationud3example.transferObjects.UserBean;

/**
 * Clase que implementa la interfaz de la lógica de negocio devolviendo datos
 * de prueba para la presentación en la UI.
 * @author javi
 */
public class UsersManagerTestDataGenerator implements UsersManager {
    private static final Logger logger=Logger.getLogger("javafxapplicationud3example");
    private ArrayList<UserBean> users;
    /**
     * Construye unos datos de prueba para la UI.
     */
    public UsersManagerTestDataGenerator(){
        users=new ArrayList();
        for(int i=0;i<25;i++)
            users.add(new UserBean("login"+i,"nombre"+i,i%2,"departamento"+i));
    }
    /**
     * Obtiene los datos de todos los usuarios
     * @return Collection<UserBean> 
     */
    @Override
    public Collection getAllUsers() throws BusinessLogicException{
        logger.info("Getting All Users for UI.");
        return users;
    }
    /**
     * Comprueba si un login ya existe.
     * @throws LoginExistsException 
     */
    @Override
    public void isLoginExisting(String login) throws LoginExistsException {
        logger.info("Validating Login existance.");
        if (users.stream()
                 .filter(user->user.getLogin().equals(login))
                 .count()!=0){
            logger.severe("Login already exists.");
            throw new LoginExistsException("Login ya existe.");
        }                
    }
    
}
