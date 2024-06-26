package cas735.msad.cartmanagementsrv;

public class AccountingBillingMessageQueueConfiguration {
    public static final String ROUTING_FOODCART = "accountingbillingfoodcart";
    public static final String EXCHANGE_NAME_FOODCART = "amq.direct";

    public static final String ROUTING_BOOKSTORECART = "accountingbillingbookstorecart";
    public static final String EXCHANGE_NAME_BOOKSTORECART = "amq.direct";
}
