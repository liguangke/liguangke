package com.example.producer.Controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.apis.ClientConfiguration;
import org.apache.rocketmq.client.apis.ClientException;
import org.apache.rocketmq.client.apis.ClientServiceProvider;
import org.apache.rocketmq.client.apis.StaticSessionCredentialsProvider;
import org.apache.rocketmq.client.apis.consumer.ConsumeResult;
import org.apache.rocketmq.client.apis.consumer.FilterExpression;
import org.apache.rocketmq.client.apis.consumer.FilterExpressionType;
import org.apache.rocketmq.client.apis.consumer.PushConsumer;
import org.apache.rocketmq.client.apis.message.Message;
import org.apache.rocketmq.client.apis.producer.Producer;
import org.apache.rocketmq.client.apis.producer.SendReceipt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Collections;

/**
 * @author liguangke
 */
@RestController
@RequiredArgsConstructor
@Slf4j
public class ProducerController {
    /**
     * 消息发送的目标Topic名称，需要提前在控制台创建，如果不创建直接使用会返回报错。
     */
    @Value("${spring.mq.topic}")
    private String topic;
    @Value("${spring.mq.tag}")
    private String tag;

    public final Producer producer;
    public final RestTemplate restTemplate;


    /**
     * Ribbon远程调用测试
     *
     * @return
     */
    @RequestMapping("/restProduce")
    public String restProduce() {
        return restTemplate.getForObject("http://consumer-service/" + "test", String.class);
    }
    /**
     * Ribbon远程调用测试
     *
     * @return
     */
    @RequestMapping("/rest")
    public String rest() {
        return "success";
    }

    @RequestMapping("/rocketSend")
    public void test2() {
        //普通消息发送。
        Message message = ClientServiceProvider.loadService().newMessageBuilder()
                .setTopic(topic)
                //设置消息索引键，可根据关键字精确查找某条消息。
                .setKeys("messageKey")
                //设置消息Tag，用于消费端根据指定Tag过滤消息。
                .setTag(tag)
                //消息体。
                .setBody("messageBody123".getBytes())
                //延迟消息 10分钟
//                .setDeliveryTimestamp(System.currentTimeMillis() + 10L * 60 * 1000)
                .build();
        try {
            //发送消息，需要关注发送结果，并捕获失败等异常。
            SendReceipt sendReceipt = producer.send(message);
            log.info("messageId:{}", sendReceipt.getMessageId());
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }
}