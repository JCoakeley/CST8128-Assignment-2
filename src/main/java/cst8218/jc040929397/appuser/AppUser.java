/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cst8218.jc040929397.appuser;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Column;
import java.io.Serializable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.enterprise.inject.Instance;
import jakarta.enterprise.inject.spi.CDI;
import jakarta.security.enterprise.identitystore.PasswordHash;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;
import java.util.HashMap;

/**
 *
 * @author joey
 * 
 * APPUSER represents a user of the app which the following groups have permissions 
 * WebGroup, ApiGroup, BouncerAdmin. the groups are allowed to access different parts
 * of the web page. The Appuser table holds the id, the username, the password
 * and the group.
 */
@Entity
@Table(name = "APPUSER")
@NamedQueries({
    @NamedQuery(name = "AppUser.findAll",
                query = "SELECT a FROM AppUser a"),
    @NamedQuery(name = "AppUser.findById",
                query = "SELECT a FROM AppUser a WHERE a.id = :id"),
    @NamedQuery(name = "AppUser.findByUserid",
                query = "SELECT a FROM AppUser a WHERE a.userid = :userid"),
    @NamedQuery(name = "AppUser.findByGroupname",
                query = "SELECT a FROM AppUser a WHERE a.groupname = :groupname")
})
public class AppUser implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @NotNull
    @NotBlank
    @Size(max = 50)
    @Column(nullable = false, unique = true)
    private String userid;
    
    @Column(length = 512)
    private String password;
    
    @NotNull
    @NotBlank
    @Size(max = 50)
    @Column(nullable = false)
    private String groupname;

    //get the id of the user
    public Long getId() {
        return id;
    }
    
    //returns the user id/username
    public String getUserid() {
        return userid;
    }
    
    //returns the password 
    public String getPassword() {
        return "";
    }
    
    //returns the group of the user
    public String getGroupname() {
        return groupname;
    }
    
    //sets the id of the user
    public void setId(Long id) {
        this.id = id;
    }
    
    //sets the userid/username of the user
    public void setUserid(String userid) {
        this.userid = userid;
    }
    
    //sets the password of the user and hashes it
    public void setPassword(String password) {
        if (password == null || password.isEmpty()) {
            return;
        }
        
        Instance<? extends PasswordHash> instance =
                CDI.current().select(Pbkdf2PasswordHash.class);

        PasswordHash passwordHash = instance.get();

        HashMap<String, String> parameters = new HashMap<>();
        parameters.put(
                "Pbkdf2PasswordHash.Algorithm",
                "PBKDF2WithHmacSHA512"
        );
        parameters.put(
                "Pbkdf2PasswordHash.Iterations",
                "3072"
        );
        parameters.put(
                "Pbkdf2PasswordHash.SaltSizeBytes",
                "64"
        );
        parameters.put(
                "Pbkdf2PasswordHash.KeySizeBytes",
                "32"
        );

        passwordHash.initialize(parameters);
        this.password = passwordHash.generate(password.toCharArray());
    }
    
    //sets the group name of the user
    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    //creates a hash code that is used to be hashed
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }
    
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AppUser)) {
            return false;
        }
        AppUser other = (AppUser) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cst8218.coak0009.entity.AppUser[ id=" + id + " ]";
    }
    
}
