package cas735.msad.accountingbillingsrv.business.entities;

import javax.persistence.Column;  
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;  
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

//The variables must be all lower case
//getter and setter are very important, they must exist or an empty list will be returned in REST.
@Entity
@DynamicUpdate
@Table
public class Communitymember {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column
    private int communitymemberid;

    @Column
    private String username;

    @Column
    private String password;

    @Column
    private String firstname;

    @Column
    private String lastname;

    @Column
    private String currentmdd;

    @Column
    private String etfemail;

    public int getCommunitymemberID() {
        return this.communitymemberid;
    }

    public void setCommunitymemberID(int communitymemberid) {
        this.communitymemberid = communitymemberid;
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

    public String getCurrentmdd() {
        return this.currentmdd;
    }

    public void setCurrentmdd(String currentmdd) {
        this.currentmdd = currentmdd;
    }

    public String getEtfemail() {
        return this.etfemail;
    }

    public void setEtfemail(String etfemail) {
        this.etfemail = etfemail;
    }


}
