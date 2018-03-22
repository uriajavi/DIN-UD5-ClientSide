/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplicationud3example.businessLogic;

import javax.naming.OperationNotSupportedException;

/**
 * This class is a factory for {@link UsersManager} interface implementing objects.
 * @author javi
 */
public class UsersManagerFactory {
    /**
     * UsersManager type for client of a RESTful web service. 
     */
    public static final String REST_WEB_CLIENT_TYPE="REST_WEB_CLIENT";
    /**
     * UsersManager type for a manager producing fake data for test purposes. 
     */
    public static final String TEST_MOCK_TYPE="TEST_MOCK";
    /**
     * Factory creation method. It returns different {@link UsersManager} interface implementing 
     * objects depending on the type parameter value.
     * @param type Type of implementation for object being returned.
     * @return An object implementing UsersManager according to type.
     * @throws OperationNotSupportedException If type is not supported.
     */
    public static UsersManager createUsersManager(String type) 
            throws OperationNotSupportedException{
        //The object to be returned.
        UsersManager userManager=null;
        //Evaluate type parameter.
        switch(type){
            case REST_WEB_CLIENT_TYPE:
                //If rest web client type is asked for, use UsersManagerImplementation.
                userManager=new UsersManagerImplementation();
                break;
            case TEST_MOCK_TYPE:
                //If rest fake data test type is asked for, use UsersManagerTestDataGenerator.
                userManager=new UsersManagerTestDataGenerator();
                break;
            default:
                //If type is not one of the types accepted.
                throw new OperationNotSupportedException("Users manager type not supported.");
        }
        return userManager;
    }
    
}
