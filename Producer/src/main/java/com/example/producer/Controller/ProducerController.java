package com.example.producer.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author liguangke
 */
@RestController
@RequestMapping("/producer")
@RequiredArgsConstructor
public class ProducerController {

    public final RabbitTemplate rabbitTemplate;

    public final RestTemplate restTemplate;


    /**
     * Ribbon远程调用测试
     *
     * @return
     */
    @PostMapping("/restProduce")
    public String restProduce() {
        return restTemplate.getForObject("http://consumer-service/" + "test", String.class);
    }
    /**
     * Ribbon远程调用测试
     *
     * @return
     */
    @PostMapping("/rest")
    public String rest() {
        return "success";
    }

}