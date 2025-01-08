package com.example.consumer.service;

//import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 * @author liguangke
 */
@Service
public class ConsumerService {

//    @RabbitListener(queues = "queue.name")
    public void consume(String message) {
        System.out.println("Received Message: " + message);
    }
}