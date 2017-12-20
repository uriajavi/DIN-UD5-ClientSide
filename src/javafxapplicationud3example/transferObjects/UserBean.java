/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplicationud3example.transferObjects;

import java.io.Serializable;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Clase que encapsula los datos de un usuario para la transferencia entre UI y 
 * Bussiness Logic. Se usa además para el modelo de datos de la TableView de la UI.
 * @author Javier Martin Uria
 */
@XmlRootElement(name="user")
public class UserBean implements Serializable {
    private final SimpleStringProperty login;
    private final SimpleStringProperty nombre;
    private final SimpleObjectProperty<Profile> perfil;
    private final SimpleObjectProperty<DepartmentBean> departamento;

    public UserBean() {
        this.login=new SimpleStringProperty();
        this.nombre=new SimpleStringProperty();
        this.perfil=new SimpleObjectProperty();
        this.departamento=new SimpleObjectProperty();
    }
    
    public UserBean(String login,
                    String nombre,
                    Profile perfil,
                    DepartmentBean departamento){
        this.login=new SimpleStringProperty(login);
        this.nombre=new SimpleStringProperty(nombre);
        this.perfil=new SimpleObjectProperty(perfil);
        this.departamento=new SimpleObjectProperty(departamento);
    }
    
    public String getLogin(){
        return this.login.get();
    }
    public void setLogin(String login){
        this.login.set(login);
    }
    @XmlElement(name="name")
    public String getNombre(){
        return this.nombre.get();
    }
    public void setNombre(String nombre){
        this.nombre.set(nombre);
    }
    @XmlElement(name="profile")
    public Profile getPerfil(){
        return this.perfil.get();
    }
    public void setPerfil(Profile perfil){
        this.perfil.set(perfil);
    }
    @XmlElement(name="department")
    public DepartmentBean getDepartamento(){
        return this.departamento.get();
    }
    public void setDepartamento(DepartmentBean departamento){
        this.departamento.set(departamento);
    }
    
}
