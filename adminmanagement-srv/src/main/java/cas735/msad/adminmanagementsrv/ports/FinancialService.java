package cas735.msad.adminmanagementsrv.ports;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Map;

public interface FinancialService {

    BigDecimal getBikerCenterCost(String fromTime, String toTime);
    BigDecimal getBookStoreCost(String fromTime, String toTime);
    Map<String, BigDecimal> getFinancialReport(String fromTime, String toTime);
}
