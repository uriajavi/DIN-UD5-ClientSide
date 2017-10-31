/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplicationud3example.businessLogic;

/**
 * Excepción que indica un error no concretado en la capa de lógica. 
 * @author javi
 */
public class BusinessLogicException extends Exception {
    public BusinessLogicException(String msg){
        super(msg);
    }
}
