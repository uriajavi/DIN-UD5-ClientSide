/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplicationud3example.transferObjects;

import java.io.Serializable;
import java.util.Objects;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Data Transfer Object used in UI and client side for representing Department entity.
 * @author javi
 */
@XmlRootElement(name="department")
public class DepartmentBean implements Serializable {
    private Integer id;
    private String description;
    private String name;

    public DepartmentBean() {
    }

    public DepartmentBean(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
   @Override
    public boolean equals(Object object) {
        if (!(object instanceof DepartmentBean)) {
            return false;
        }
        DepartmentBean other = (DepartmentBean) object;
        if ((this.id == null && other.id != null) || 
            (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        if ((this.name == null && other.name != null) || 
            (this.name != null && !this.name.equals(other.name))) {
            return false;
        }
        if ((this.description == null && other.description != null) || 
            (this.description != null && !this.description.equals(other.description))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + Objects.hashCode(this.id);
        hash = 73 * hash + Objects.hashCode(this.description);
        hash = 73 * hash + Objects.hashCode(this.name);
        return hash;
    }
    
}
