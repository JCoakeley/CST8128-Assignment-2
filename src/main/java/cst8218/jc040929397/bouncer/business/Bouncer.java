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
    protected Integer xPos;
    protected Integer yPos;
    protected Integer size;
    protected Integer currentTravel;
    protected Integer maxTravel;
    protected Integer mvtDirection;
    protected Integer dirChangeCount;

    public Integer getxPos() {
        return xPos;
    }

    public void setxPos(Integer xPos) {
        this.xPos = clamp(xPos, 0, X_LIMIT);
    }

    public Integer getyPos() {
        return yPos;
    }

    public void setyPos(Integer yPos) {
        this.yPos = clamp(yPos, 0, Y_LIMIT);
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = clamp(size, 1, SIZE_LIMIT);
    }

    public Integer getCurrentTravel() {
        return currentTravel;
    }

    public void setCurrentTravel(Integer currentTravel) {
        if (currentTravel == null) {
            this.currentTravel = null;
        } else if (maxTravel != null) {
            this.currentTravel = clamp(currentTravel, -maxTravel, maxTravel);
        } else {
            this.currentTravel = currentTravel;
        }
    }

    public Integer getMaxTravel() {
        return maxTravel;
    }

    public void setMaxTravel(Integer maxTravel) {
        this.maxTravel = clamp(maxTravel, 0, MAX_TRAVEL_LIMIT);
    }

    public Integer getMvtDirection() {
        return mvtDirection;
    }

    public void setMvtDirection(Integer mvtDirection) {
        if (mvtDirection == null) {
            this.mvtDirection = null;
        } else {
            this.mvtDirection = mvtDirection < 0 ? -1 : 1;
        }
    }

    public Integer getDirChangeCount() {
        return dirChangeCount;
    }

    public void setDirChangeCount(Integer dirChangeCount) {
        this.dirChangeCount = dirChangeCount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    /**
    * Updates the properties to simulate the passing of one unit of time.
    */
    public void timeStep() {
        if (maxTravel > 0){ // Only change bouncer's position if maxTravel != 0
            currentTravel += mvtDirection * TRAVEL_SPEED;
            
            // Switch directions if maxTravel limit is reached
            if (Math.abs(currentTravel) >= maxTravel){
                mvtDirection = -mvtDirection;
                dirChangeCount++;
                
                // When direction change limit is reach, reduce the maxTravel length
                // and reset the direction change count.
                if (dirChangeCount > MAX_DIR_CHANGES){
                    maxTravel -= DECREASE_RATE;
                    dirChangeCount = 0;
                }
            }
        }
    }
    
    public void updateNonNull(Bouncer oldBouncer) {
        if (this.xPos != null) {
            oldBouncer.setxPos(this.xPos);
        }

        if (this.yPos != null) {
            oldBouncer.setyPos(this.yPos);
        }

        if (this.size != null) {
            oldBouncer.setSize(this.size);
        }

        if (this.currentTravel != null) {
            oldBouncer.setCurrentTravel(this.currentTravel);
        }

        if (this.maxTravel != null) {
            oldBouncer.setMaxTravel(this.maxTravel);
        }

        if (this.mvtDirection != null) {
            oldBouncer.setMvtDirection(this.mvtDirection);
        }

        if (this.dirChangeCount != null) {
            oldBouncer.setDirChangeCount(this.dirChangeCount);
        }
    }
    
    public void applyDefaults() {
        setxPos(xPos == null ? 0 : xPos);
        setyPos(yPos == null ? 0 : yPos);
        setSize(size == null ? INITIAL_SIZE : size);
        setMaxTravel(maxTravel == null ? MAX_TRAVEL_LIMIT : maxTravel);
        setCurrentTravel(currentTravel == null ? 0 : currentTravel);
        setMvtDirection(mvtDirection == null ? 1 : mvtDirection);
        setDirChangeCount(dirChangeCount == null ? 0 : dirChangeCount);
    }
    
    private Integer clamp(Integer value, int min, int max) {
        if (value == null) {
            return null;
        }

        return Math.max(min, Math.min(max, value));
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
