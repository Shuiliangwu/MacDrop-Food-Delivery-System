package cas735.msad.usermanagementsrv.business.entities;

import javax.persistence.Column;  
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;  
import javax.persistence.Table;

import lombok.NonNull;
//The variables must be all lower case
//getter and setter are very important, they must exist or an empty list will be returned in REST.
@Entity

@Table
public class Admin {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column
    private int adminid;

    @Column
    @NonNull
    private String username;

    @Column
    @NonNull
    private String password;

    @Column
    @NonNull
    private String firstname;

    @Column
    @NonNull
    private String lastname;

    @Column
    @NonNull
    private String etfemail;

    public int getAdminid() {
        return this.adminid;
    }

    public void setAdminid(int adminid) {
        this.adminid = adminid;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return this.firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return this.lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEtfemail() {
        return this.etfemail;
    }

    public void setEtfemail(String etfemail) {
        this.etfemail = etfemail;
    }
 
}
