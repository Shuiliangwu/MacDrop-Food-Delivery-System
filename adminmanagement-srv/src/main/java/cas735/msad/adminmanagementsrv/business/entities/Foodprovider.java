package cas735.msad.adminmanagementsrv.business.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity

@Table
public class Foodprovider {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column
    private int foodproviderid;

    @Column
    private String username;

    @Column
    private String password;

    @Column
    private String firstname;

    @Column
    private String lastname;

    @Column
    private String etfemail;

    public int getFoodproviderid() {
        return this.foodproviderid;
    }

    public void setFoodproviderid(int foodproviderid) {
        this.foodproviderid = foodproviderid;
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
