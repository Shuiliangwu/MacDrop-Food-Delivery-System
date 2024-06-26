package cas735.msad.cartmanagementsrv.adapters;

import org.springframework.amqp.rabbit.core.RabbitTemplate;

import cas735.msad.cartmanagementsrv.ports.AccountingBillingService;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;

import cas735.msad.cartmanagementsrv.AccountingBillingMessageQueueConfiguration;
import cas735.msad.cartmanagementsrv.business.entities.BookStoreCart;
import cas735.msad.cartmanagementsrv.business.entities.FoodCart;

@Slf4j
@Service
public class AccountingBillingClient implements AccountingBillingService{

    private final RabbitTemplate rabbitTemplate;
    
    @Autowired
    public AccountingBillingClient(RabbitTemplate rabbitTemplate){
        this.rabbitTemplate = rabbitTemplate;
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
    }

    @Override
    public void sendFoodCartToAccountingBilling(FoodCart FoodCart) {
        log.info("Starting to send food cart to AccountingBillingService");
        rabbitTemplate.convertAndSend(AccountingBillingMessageQueueConfiguration.EXCHANGE_NAME_FOODCART, AccountingBillingMessageQueueConfiguration.ROUTING_FOODCART, FoodCart);
    }

    @Override
    public void sendBookStoreCartToAccountingBilling(BookStoreCart bookStoreCart) {
        log.info("Starting to send bookstore cart to AccountingBillingService");
        rabbitTemplate.convertAndSend(AccountingBillingMessageQueueConfiguration.EXCHANGE_NAME_BOOKSTORECART, AccountingBillingMessageQueueConfiguration.ROUTING_BOOKSTORECART, bookStoreCart);
    }
    
}
