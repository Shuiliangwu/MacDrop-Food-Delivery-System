package cas735.msad.accountingbillingsrv;

public class OrderManagementQueueConfiguration {
    public static final String QUEUE_NAME_FOODORDER = "foodordermanagementqueue";
    public static final String ROUTING_FOODORDER = "foodordermanagement";
    public static final String EXCHANGE_NAME_FOODORDER = "amq.direct";

    public static final String QUEUE_NAME_BOOKSTOREORDER = "bookstoreordermanagementqueue";
    public static final String ROUTING_BOOKSTOREORDER = "bookstoreordermanagement";
    public static final String EXCHANGE_NAME_BOOKSTOREORDER = "amq.direct";
}
