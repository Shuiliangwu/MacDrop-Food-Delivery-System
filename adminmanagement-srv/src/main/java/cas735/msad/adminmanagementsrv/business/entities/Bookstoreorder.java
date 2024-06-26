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

//The variables must be all lower case
//getter and setter are very important, they must exist or an empty list will be returned in REST.
@Data
@Builder
@Getter
@Setter
@NoArgsConstructor @AllArgsConstructor
@Entity

@Table
public class Bookstoreorder {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column
    private int bookstoreorderid;

    @Column
    private String communitymemberid;

    @Column
    private String bookstoreoperatorid;

    @Column
    private String bookstoreitems;

    @Column
    private String mddprice; // double data type??

    @Column
    private String ordertime;

    public int getBookstoreorderid() {
        return bookstoreorderid;
    }

    public void setBookstoreorderid(int bookstoreorderid) {
        this.bookstoreorderid = bookstoreorderid;
    }

    public String getCommunitymemberid() {
        return communitymemberid;
    }

    public void setCommunitymemberid(String communitymemberid) {
        this.communitymemberid = communitymemberid;
    }

    public String getBookstoreoperatorid() {
        return bookstoreoperatorid;
    }

    public void setBookstoreoperatorid(String bookstoreoperatorid) {
        this.bookstoreoperatorid = bookstoreoperatorid;
    }

    public String getBookstoreitems() {
        return bookstoreitems;
    }

    public void setBookstoreitems(String bookstoreitems) {
        this.bookstoreitems = bookstoreitems;
    }

    public String getMddprice() {
        return mddprice;
    }

    public void setMddprice(String mddprice) {
        this.mddprice = mddprice;
    }

    public String getOrdertime() {
        return ordertime;
    }

    public void setOrdertime(String ordertime) {
        this.ordertime = ordertime;
    }
}
