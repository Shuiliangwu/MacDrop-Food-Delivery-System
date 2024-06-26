package cas735.msad.adminmanagementsrv.business.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import org.hibernate.annotations.DynamicUpdate;


//The variables must be all lower case
//getter and setter are very important, they must exist or an empty list will be returned in REST.
@Data
@Builder
@Getter
@Setter
@NoArgsConstructor @AllArgsConstructor
@Entity
@DynamicUpdate

@Table
public class Foodorder {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column
    private int foodorderid;

    @Column
    private String communitymemberid;

    @Column
    private String foodproviderid;

    @Column
    private String fooditems;

    @Column
    private String foodorderprice; // double data type??

    @Column
    private String ordertime;

    @Column
    private String bikerid;

    @Column
    private String dropofflocationid;

    @Column
    private String estimatedpickuptime;

    @Column
    private String status;

    public int getFoodorderid() {
        return foodorderid;
    }

    public void setFoodorderid(int foodorderid) {
        this.foodorderid = foodorderid;
    }

    public String getCommunitymemberid() {
        return communitymemberid;
    }

    public void setCommunitymemberid(String communitymemberid) {
        this.communitymemberid = communitymemberid;
    }

    public String getFoodproviderid() {
        return foodproviderid;
    }

    public void setFoodproviderid(String foodproviderid) {
        this.foodproviderid = foodproviderid;
    }

    public String getFooditems() {
        return fooditems;
    }

    public void setFooditems(String fooditems) {
        this.fooditems = fooditems;
    }

    public String getFoodorderprice() {
        return foodorderprice;
    }

    public void setFoodorderprice(String foodorderprice) {
        this.foodorderprice = foodorderprice;
    }

    public String getOrdertime() {
        return ordertime;
    }

    public void setOrdertime(String ordertime) {
        this.ordertime = ordertime;
    }

    public String getBikerid() {
        return bikerid;
    }

    public void setBikerid(String bikerid) {
        this.bikerid = bikerid;
    }

    public String getDropofflocationid() {
        return dropofflocationid;
    }

    public void setDropofflocationid(String dropofflocationid) {
        this.dropofflocationid = dropofflocationid;
    }

    public String getEstimatedpickuptime() {
        return estimatedpickuptime;
    }

    public void setEstimatedpickuptime(String estimatedpickuptime) {
        this.estimatedpickuptime = estimatedpickuptime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}


