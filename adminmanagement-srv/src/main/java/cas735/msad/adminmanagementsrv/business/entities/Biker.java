package cas735.msad.adminmanagementsrv.business.entities;

import javax.persistence.*;

@Entity
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

    public Biker() {
    }

    public Biker(String username, String password, String firstname, String lastname, int currentworkload, int historyworkload, String etfemail) {
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.currentworkload = currentworkload;
        this.historyworkload = historyworkload;
        this.etfemail = etfemail;
    }

    public Biker(int bikerid, String username, String password, String firstname, String lastname, int currentworkload, int historyworkload, String etfemail) {
        this.bikerid = bikerid;
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.currentworkload = currentworkload;
        this.historyworkload = historyworkload;
        this.etfemail = etfemail;
    }

    public int getBikerid() {
        return bikerid;
    }

    public void setBikerid(int bikerid) {
        this.bikerid = bikerid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public int getCurrentworkload() {
        return currentworkload;
    }

    public void setCurrentworkload(int currentworkload) {
        this.currentworkload = currentworkload;
    }

    public int getHistoryworkload() {
        return historyworkload;
    }

    public void setHistoryworkload(int historyworkload) {
        this.historyworkload = historyworkload;
    }

    public String getEtfemail() {
        return etfemail;
    }

    public void setEtfemail(String etfemail) {
        this.etfemail = etfemail;
    }
}
