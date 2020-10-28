/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplicationud3example.transferObjects;

import java.io.Serializable;
import java.util.Objects;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Data Transfer Object used in UI and client side for representing User entity.
 * It is also used as data model for a TableView in the UI.
 * @author javi
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
   @Override
    public boolean equals(Object object) {
        if (!(object instanceof UserBean)) {
            return false;
        }
        UserBean other = (UserBean) object;
        if ((this.login == null && other.login != null) || 
            (this.login != null && !this.login.get().equals(other.login.get()))) {
            return false;
        }
        if ((this.nombre == null && other.nombre != null) || 
            (this.nombre != null && !this.nombre.get().equals(other.nombre.get()))) {
            return false;
        }
        if ((this.perfil == null && other.perfil != null) || 
            (this.perfil != null && !this.perfil.get().equals(other.perfil.get()))) {
            return false;
        }
        if ((this.departamento == null && other.departamento != null) || 
            (this.departamento != null && 
                !this.departamento.get().equals(other.departamento.get()))) {
            return false;
        }        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + Objects.hashCode(this.login);
        hash = 41 * hash + Objects.hashCode(this.nombre);
        hash = 41 * hash + Objects.hashCode(this.perfil);
        hash = 41 * hash + Objects.hashCode(this.departamento);
        return hash;
    }
}
