/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplicationud3example.transferObjects;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Clase que encapsula los datos de un usuario para la transferencia entre UI y 
 * Bussiness Logic. Se usa además para el modelo de datos de la TableView de la UI.
 * @author Javier Martin Uria
 */
public class UserBean {
    private final SimpleStringProperty login;
    private final SimpleStringProperty nombre;
    private final SimpleIntegerProperty perfil;
    private final SimpleStringProperty departamento;
    
    public UserBean(String login,
                    String nombre,
                    Integer perfil,
                    String departamento){
        this.login=new SimpleStringProperty(login);
        this.nombre=new SimpleStringProperty(nombre);
        this.perfil=new SimpleIntegerProperty(perfil);
        this.departamento=new SimpleStringProperty(departamento);
    }
    
    public String getLogin(){
        return this.login.get();
    }
    public void setLogin(String login){
        this.login.set(login);
    }

    public String getNombre(){
        return this.nombre.get();
    }
    public void setNombre(String nombre){
        this.nombre.set(nombre);
    }
    public Integer getPerfil(){
        return this.perfil.get();
    }
    public void setPerfil(Integer perfil){
        this.perfil.set(perfil);
    }
    public String getDepartamento(){
        return this.departamento.get();
    }
    public void setDepartamento(String departamento){
        this.departamento.set(departamento);
    }
    
}
