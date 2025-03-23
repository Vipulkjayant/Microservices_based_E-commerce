package com.rabbitworkflow.Config;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

   
    @Bean
    Queue stockQueue()
    {

        return new Queue("stockQueue.created",true);
    }

    @Bean
    Queue stockQueue2()
    {
        return new Queue("stockQueue.deleted", true);
    }

    

    @Bean
    Queue stockQueue3()
    {
        return new Queue("stockQueue.AllDeleted", true);
    }

    @Bean
    DirectExchange directExchange()
    {

        return new DirectExchange("direct-exchange");

    } 

    //In a single queue there are 3 routing-key types of message 

    // 1.product.created 2.product.deleted 3.product.AllDeleted

    @Bean
    Binding stockBinding(Queue stockQueue,DirectExchange directExchange)
    {

        return BindingBuilder.bind(stockQueue).to(directExchange).with("product.created");
        
    }

    @Bean
    Binding stockBinding2(Queue stockQueue2,DirectExchange directExchange)
    {
        return BindingBuilder.bind(stockQueue2).to(directExchange).with("product.deleted");
    }

    @Bean
    Binding stockBinding3(Queue stockQueue3,DirectExchange directExchange)
    {
        return BindingBuilder.bind(stockQueue3).to(directExchange).with("product.AllDeleted");
    }

    @Bean
    MessageConverter messageConverter()
    {
        return new Jackson2JsonMessageConverter();

    }

    @Bean
    public AmqpAdmin amqpAdmin(ConnectionFactory connectionFactory)
    {
        return new RabbitAdmin(connectionFactory);

    }
    
    @Bean
    public CommandLineRunner initializeAmqpAdmin(AmqpAdmin amqpAdmin) {
        return args -> {
            System.out.println("Initializing RabbitMQ Queues and Exchanges...");
            ((RabbitAdmin) amqpAdmin).initialize();
        };
    }


}
