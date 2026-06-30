/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cst8218.jc040929397.bouncer.business;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;

/**
 *
 * @author joey
 */
@Entity
public class Bouncer implements Serializable {
    
    public static final int INITIAL_SIZE = 10;
    public static final int X_LIMIT = 1000;
    public static final int Y_LIMIT = 500;
    public static final int SIZE_LIMIT = 100;
    public static final int MAX_TRAVEL_LIMIT = 1000;
    public static final int MAX_DIR_CHANGES = 8;
    public static final int DECREASE_RATE = 1;
    public static final int TRAVEL_SPEED = 10;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    protected Integer xPos;
    @NotNull
    protected Integer yPos;
    @NotNull
    protected Integer size;
    @NotNull
    protected Integer currentTravel;
    @NotNull
    protected Integer maxTravel;
    @NotNull
    protected Integer mvtDirection;
    @NotNull
    protected Integer dirChangeCount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Bouncer)) {
            return false;
        }
        Bouncer other = (Bouncer) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "business.Bouncer[ id=" + id + " ]";
    }
    
}
