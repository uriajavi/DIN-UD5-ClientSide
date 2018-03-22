/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplicationud3example.rest;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import java.util.ResourceBundle;


/**
 * Jersey REST client generated for REST resource:UserREST [user]. It contains 
 * methods needed to invoke a user RESTful web service residing in a Glassfish Server.<br>
 * 
 * USAGE:
 * <pre>
 *        UserRESTClient client = new UserRESTClient();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author javi
 */
public class UserRESTClient {
    private final WebTarget webTarget;
    private final Client client;
    //Get URI from properties' values file.
    private static final String BASE_URI = 
            ResourceBundle.getBundle("javafxapplicationud3example.config.parameters")
                          .getString("RESTful.baseURI");

    /**
     * Construct a UserRESTClient. It creates a RESTful web client and establishes
     * the path of the WebTarget object associated to the client.
     */
    public UserRESTClient() {
        //Create RESTful client
        client = javax.ws.rs.client.ClientBuilder.newClient();
        //Establish the path of the WebTarget object associated to the client
        webTarget = client.target(BASE_URI).path("users");
    }
    /**
     * Get a user's entity XML representation from the user RESTful web service and 
     * return it as a generic type object.
     * @param responseType The Class object of the returning instance. 
     * @param id The id of the instance in the server side. 
     * @return The object containing the data.
     * @throws ClientErrorException If there is an error while processing. The error is wrapped in a HTTP error response.  
     */
    public <T> T find_XML(Class<T> responseType, String id) throws ClientErrorException {
        WebTarget resource = webTarget;
        //Set the path for the request.
        resource = resource.path(java.text.MessageFormat.format("{0}", new Object[]{id}));
        //Make request and return data from the response
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }
    /**
     * Get a list of user's entities XML representation from the user RESTful web service and 
     * return it as a generic type object.
     * @param responseType The Class object of the returning instance.
     * @return A generic type, normally a list, containing the data.
     * @throws ClientErrorException If there is an error while processing. The error is wrapped in a HTTP error response.  
     */
    public <T> T findAll_XML(GenericType<T> responseType) throws ClientErrorException {
        WebTarget resource = webTarget;
        //Make request and return data from the response
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }
    /**
     * Get a list of department's entities XML representation from the user RESTful web service and 
     * return it as a generic type object.
     * @param responseType The Class object of the returning instance.
     * @return A generic type, normally a list, containing the data.
     * @throws ClientErrorException If there is an error while processing. The error is wrapped in a HTTP error response.  
     */
    public <T> T findAllDeps_XML(GenericType<T> responseType) throws ClientErrorException {
        WebTarget resource = webTarget;
        //Set the path for the request.
        resource = resource.path("departments");
        //Make request and return data from the response
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }
    /**
     * Create an user's entity XML representation and send it as a request to create it
     * to the user RESTful web service.
     * @param requestEntity The object containing data to be created.
     * @throws ClientErrorException If there is an error while processing. The error is wrapped in a HTTP error response.  
     */
    public void create_XML(Object requestEntity) throws ClientErrorException {
        //Make request
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));
    }
    /**
     * Create an user's entity XML representation and send it as a request to update it
     * to the user RESTful web service.
     * @param requestEntity The object containing data to be updated.
     * @throws ClientErrorException If there is an error while processing. The error is wrapped in a HTTP error response.  
     */
    public void update_XML(Object requestEntity) throws ClientErrorException {
        //Make request
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML).put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));
    }
    /**
     * Send a request to the user RESTful web service to delete a user identified by its id.
     * @param id The id of the user entity to be deleted.
     * @throws ClientErrorException If there is an error while processing. The error is wrapped in a HTTP error response.  
     */
    public void delete(String id) throws ClientErrorException {
        //Make request
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id})).request().delete();
    }
    /**
     * Close RESTful web service client.
     */
    public void close() {
        client.close();
    }
    
}
