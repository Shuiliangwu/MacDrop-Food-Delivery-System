package cas735.msad.accountingbillingsrv.adapters;

import org.springframework.stereotype.Service;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;

import cas735.msad.accountingbillingsrv.OrderManagementQueueConfiguration;
import cas735.msad.accountingbillingsrv.dto.BookStoreOrderList;
import cas735.msad.accountingbillingsrv.ports.BookStoreOrderManagementService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
@Service
public class BookStoreOrderManagementClient implements BookStoreOrderManagementService{
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public BookStoreOrderManagementClient(RabbitTemplate rabbitTemplate){
        this.rabbitTemplate = rabbitTemplate;
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
    }

    @Override
    public void createBookStoreOrder(BookStoreOrderList bookStoreOrderList) {
        log.info("Sending bookstore order list to OrderManagementService...");
        rabbitTemplate.convertAndSend(OrderManagementQueueConfiguration.EXCHANGE_NAME_BOOKSTOREORDER, OrderManagementQueueConfiguration.ROUTING_BOOKSTOREORDER, bookStoreOrderList);
        log.info("Bookstore order list sent");
    }
}
