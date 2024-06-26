package cas735.msad.adminmanagementsrv.ports;

import cas735.msad.adminmanagementsrv.dto.MddCompensation;

public interface ApprovalsService {

    String getDailyFoodOrderBillStatement(String id, String date);
    void approveDailyFoodOrderTransactions(String id, String date);
    void approveWeeklyBookStoreMddCompensationInvoice(MddCompensation mddCompensation);

    
}
