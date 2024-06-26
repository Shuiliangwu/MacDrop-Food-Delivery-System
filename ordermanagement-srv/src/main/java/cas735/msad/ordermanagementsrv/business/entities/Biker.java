package cas735.msad.ordermanagementsrv.business.entities;

import javax.persistence.Column;  
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;  
import javax.persistence.Table;
//The variables must be all lower case
//getter and setter are very important, they must exist or an empty list will be returned in REST.
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import org.hibernate.annotations.DynamicUpdate;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor @AllArgsConstructor 
@Entity
@DynamicUpdate

@Table
public class Biker {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column
    private int bikerid;

    @Column
    private String username;

    @Column
    private String password;

    @Column
    private String firstname;

    @Column
    private String lastname;

    @Column
    private int currentworkload;

    @Column
    private int historyworkload;

    @Column
    private String etfemail;

    public int getBikerid() {
        return this.bikerid;
    }

    public void setBikerid(int bikerid) {
        this.bikerid = bikerid;
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

    public int getCurrentworkload() {
        return this.currentworkload;
    }

    public void setCurrentworkload(int currentworkload) {
        this.currentworkload = currentworkload;
    }

    public int getHistoryworkload() {
        return this.historyworkload;
    }

    public void setHistoryworkload(int historyworkload) {
        this.historyworkload = historyworkload;
    }

    public String getEtfemail() {
        return this.etfemail;
    }

    public void setEtfemail(String etfemail) {
        this.etfemail = etfemail;
    }

}
