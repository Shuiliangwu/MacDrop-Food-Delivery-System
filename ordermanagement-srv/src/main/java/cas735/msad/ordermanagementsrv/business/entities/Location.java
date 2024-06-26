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
public class Location {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column
    private int locationid;

    @Column
    private String name;

    @Column
    private int currentworkload;

    @Column
    private int historyworkload;

    public int getLocationid() {
        return this.locationid;
    }

    public void setLocationid(int locationid) {
        this.locationid = locationid;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
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

}
