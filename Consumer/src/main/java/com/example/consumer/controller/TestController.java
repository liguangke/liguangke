package com.example.consumer.controller;


import com.alibaba.arms.tracing.Span;
import com.alibaba.arms.tracing.Tracer;
import lombok.RequiredArgsConstructor;
import org.apache.rocketmq.client.apis.ClientConfiguration;
import org.apache.rocketmq.client.apis.ClientException;
import org.apache.rocketmq.client.apis.ClientServiceProvider;
import org.apache.rocketmq.client.apis.StaticSessionCredentialsProvider;
import org.apache.rocketmq.client.apis.message.Message;
import org.apache.rocketmq.client.apis.producer.SendReceipt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.time.Duration;

/**
 * @author lgk
 * @date 2024-12-30 17:07:12
 */
@RestController
@RequiredArgsConstructor
public class TestController {

    private static final Logger log = LoggerFactory.getLogger(TestController.class);
    public final RestTemplate restTemplate;
//    public final JedisPool myJedisPool;

    @RequestMapping("/test")
    public String test(@RequestParam(required = false) String name) {
        if ("error".equals(name)) {
            throw new RuntimeException("error");
        }
        Span span = Tracer.builder().getSpan();   //此处未创建新的Span。
        String traceId = span.getTraceId();
        String rpcId = span.getRpcId();
        log.info("traceId:{},rpcId:{}", traceId, rpcId);
        return "success";
    }

    @RequestMapping("/restConsumer")
    public String restProduce() {
        return restTemplate.getForObject("http://consumer-service/" + "test", String.class);
    }



//    @RequestMapping("/redis")
//    public String redisTest(@RequestParam(required = false) String key) {
//        Jedis jedis = myJedisPool.getResource();
//        if (key != null) {
//            jedis.set(key, "test");
//        }else {
//            jedis.set("foo10", "bar");
//            System.out.println(jedis.get("foo10"));
//        }
//        return "success";
//    }
}
