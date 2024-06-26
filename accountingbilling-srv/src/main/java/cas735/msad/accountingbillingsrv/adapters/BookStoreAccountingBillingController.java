package cas735.msad.accountingbillingsrv.adapters;

import lombok.extern.slf4j.Slf4j;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cas735.msad.accountingbillingsrv.business.entities.BookStoreCart;
import cas735.msad.accountingbillingsrv.ports.BookStoreAccountingBillingService;
import cas735.msad.accountingbillingsrv.AccountingBillingMessageQueueConfiguration;

import org.springframework.beans.factory.annotation.Autowired;

@Component
@Slf4j
public class BookStoreAccountingBillingController {

    private final BookStoreAccountingBillingService bookStoreAccountingBillingService;

    @Autowired
    public BookStoreAccountingBillingController(BookStoreAccountingBillingService bookStoreAccountingBillingService){
        this.bookStoreAccountingBillingService = bookStoreAccountingBillingService;
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = AccountingBillingMessageQueueConfiguration.QUEUE_NAME_BOOKSTORECART, durable = "true"),
            exchange = @Exchange(
                    value = AccountingBillingMessageQueueConfiguration.EXCHANGE_NAME_BOOKSTORECART, ignoreDeclarationExceptions = "true"),
                    key = AccountingBillingMessageQueueConfiguration.ROUTING_BOOKSTORECART))
    public void receive(BookStoreCart req) {
        try {
            log.info("Reading message: '" + req + "'");
            bookStoreAccountingBillingService.bookStoreCartAccountingBilling(req);
        } catch (Exception e) {
            log.info("Error receiving message from BookStoreCartManagement");
            log.info(e.toString());
        }
    }
}
