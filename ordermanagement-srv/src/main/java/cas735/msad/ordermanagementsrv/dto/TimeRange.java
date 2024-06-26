package cas735.msad.ordermanagementsrv.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.NonNull;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor @AllArgsConstructor 
public class TimeRange {
    @NonNull String leftyear;
    @NonNull String  leftmonth;
    @NonNull String  leftday;
    @NonNull String  lefthour;
    @NonNull String  leftmin;
    @NonNull String  leftsec;  
    @NonNull String  rightyear;
    @NonNull String  rightmonth;
    @NonNull String  rightday;
    @NonNull String  righthour;
    @NonNull String  rightmin;
    @NonNull String  rightsec; 
}
