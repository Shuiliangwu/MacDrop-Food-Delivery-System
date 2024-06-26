package cas735.msad.ordermanagementsrv.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor @AllArgsConstructor
public class MddCompensation {
    String listinvoice;
    String starttime;
    String endtime;
    String totalmdd;
    String message;
    String bookstoreoperatorid;
    String bookstoreoperatoremail;
}
