package com.example.consumer.mq;

import org.apache.rocketmq.client.apis.ClientConfiguration;
import org.apache.rocketmq.client.apis.ClientException;
import org.apache.rocketmq.client.apis.ClientServiceProvider;
import org.apache.rocketmq.client.apis.StaticSessionCredentialsProvider;
import org.apache.rocketmq.client.apis.consumer.ConsumeResult;
import org.apache.rocketmq.client.apis.consumer.FilterExpression;
import org.apache.rocketmq.client.apis.consumer.FilterExpressionType;
import org.apache.rocketmq.client.apis.consumer.PushConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Collections;


/**
 * @author lgk
 * @classDesc: 功能描述(消费者)
 * @date 2025/1/26
 */
@Configuration
public class Consumer {

    private static final Logger log = LoggerFactory.getLogger(Consumer.class);
    /**
     * 实例接入点，从控制台实例详情页的接入点页签中获取。
     * 如果是在阿里云ECS内网访问，建议填写VPC接入点。
     * 如果是在本地公网访问，或者是线下IDC环境访问，可以使用公网接入点。使用公网接入点访问，必须开启实例的公网访问功能。
     */
    @Value("${spring.mq.endpoints}")
    private String endpoints;
    /**
     * 指定需要订阅哪个目标Topic，Topic需要提前在控制台创建，如果不创建直接使用会返回报错。
     */
    @Value("${spring.mq.topic}")
    private String topic;
    /**
     * 为消费者指定所属的消费者分组，Group需要提前在控制台创建，如果不创建直接使用会返回报错。
     */
    @Value("${spring.mq.group}")
    private String group;
    @Value("${spring.mq.namespace}")
    private String namespace;
    @Value("${spring.mq.tag}")
    private String tag;
    @Value("${spring.mq.accessKey}")
    private String accessKey;
    @Value("${spring.mq.accessSecret}")
    private String accessSecret;

    @Bean("myConsumer")
    public PushConsumer init() throws ClientException {

        final ClientServiceProvider provider = ClientServiceProvider.loadService();
        ClientConfiguration clientConfiguration = ClientConfiguration.newBuilder()
                .setEndpoints(endpoints)
                /**
                 * 如果使用公网接入点访问Serverless实例，需要设置实例ID。
                 */
                .setNamespace(namespace)
                /**
                 * 设置网络连接超时时间，单位毫秒。过少可能会导致发送超时。
                 */
                .setRequestTimeout(Duration.ofMillis(10000))
                /**
                 * 如果是使用公网接入点访问，configuration还需要设置实例的用户名和密码。用户名和密码在控制台访问控制的智能身份识别页签中获取。
                 * 如果是在阿里云ECS内网访问，无需填写该配置，服务端会根据内网VPC信息智能获取。
                 * 如果实例类型为Serverless实例，公网访问必须设置实例的用户名密码，当开启内网免身份识别时,内网访问可以不设置用户名和密码。
                 */
                .setCredentialProvider(new StaticSessionCredentialsProvider(accessKey, accessSecret))
                .build();
        //订阅消息的过滤规则，* 表示订阅所有Tag的消息。
        FilterExpression filterExpression = new FilterExpression(tag, FilterExpressionType.TAG);
        //初始化PushConsumer，需要绑定消费者分组ConsumerGroup、通信参数以及订阅关系。
        PushConsumer pushConsumer = provider.newPushConsumerBuilder()
                .setClientConfiguration(clientConfiguration)
                //设置消费者分组。
                .setConsumerGroup(group)
                //设置预绑定的订阅关系。
                .setSubscriptionExpressions(Collections.singletonMap(topic, filterExpression))
                //设置消费监听器。
                .setMessageListener(messageView -> {
                    //处理消息并返回消费结果。
                    log.info("Receive message: {}", messageView);
                    ByteBuffer body = messageView.getBody();
                    log.info("Message Body: {}", StandardCharsets.UTF_8.decode(body));
                    //消费确认
                    return ConsumeResult.SUCCESS;
                })
                .build();
        return pushConsumer;
    }
}
