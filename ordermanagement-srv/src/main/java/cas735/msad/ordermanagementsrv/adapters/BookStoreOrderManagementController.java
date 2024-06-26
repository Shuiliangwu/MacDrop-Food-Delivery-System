package cas735.msad.ordermanagementsrv.adapters;

import lombok.extern.slf4j.Slf4j;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.web.bind.annotation.RestController;
import org.springframework.stereotype.Component;

import cas735.msad.ordermanagementsrv.dto.BookStoreOrderList;
import cas735.msad.ordermanagementsrv.ports.BookStoreOrderManagementService;
import cas735.msad.ordermanagementsrv.OrderManagementQueueConfiguration;

@RestController
@Component
@Slf4j
public class BookStoreOrderManagementController {

    public final BookStoreOrderManagementService bookStoreOrderManagementService;

    @Autowired
    public BookStoreOrderManagementController(BookStoreOrderManagementService bookStoreOrderManagementService){
        this.bookStoreOrderManagementService = bookStoreOrderManagementService;
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = OrderManagementQueueConfiguration.QUEUE_NAME_BOOKSTOREORDER, durable = "true"),
            exchange = @Exchange(
                    value = OrderManagementQueueConfiguration.EXCHANGE_NAME_BOOKSTOREORDER, ignoreDeclarationExceptions = "true"),
                    key = OrderManagementQueueConfiguration.ROUTING_BOOKSTOREORDER))
    public void receive(BookStoreOrderList req) {
        try {
            log.info("Reading message: '" + req + "'");
            bookStoreOrderManagementService.createBookStoreOrder(req);
        } catch (Exception e) {
            log.info("Error receiving message from AccountingBillingService");
            log.info(e.toString());
        }
    }
}
