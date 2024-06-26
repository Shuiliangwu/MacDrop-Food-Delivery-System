package cas735.msad.adminmanagementsrv.business.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.sql.Date;


@Entity
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int locationid;

    private String name;

    private int currentworkload;

    private int historyworkload;

    public Location() {
    }

    public Location(String name, int currentworkload, int historyworkload) {
        this.name = name;
        this.currentworkload = currentworkload;
        this.historyworkload = historyworkload;
    }

    public int getLocationid() {
        return locationid;
    }

    public void setLocationid(int locationid) {
        this.locationid = locationid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
