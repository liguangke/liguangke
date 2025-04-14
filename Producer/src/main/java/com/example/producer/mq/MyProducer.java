package com.example.producer.mq;

import org.apache.rocketmq.client.apis.ClientConfiguration;
import org.apache.rocketmq.client.apis.ClientException;
import org.apache.rocketmq.client.apis.ClientServiceProvider;
import org.apache.rocketmq.client.apis.StaticSessionCredentialsProvider;
import org.apache.rocketmq.client.apis.producer.Producer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.Duration;

/**
 * @author lgk
 * @classDesc: 功能描述(生产者)
 * @date 2025/2/5
 */
@Component
public class MyProducer {

    /**
     * 实例接入点，从控制台实例详情页的接入点页签中获取。
     * 如果是在阿里云ECS内网访问，建议填写VPC接入点。
     * 如果是在本地公网访问，或者是线下IDC环境访问，可以使用公网接入点。使用公网接入点访问，必须开启实例的公网访问功能。
     */
    @Value("${spring.mq.endpoints}")
    private String endpoints;
    /**
     * 消息发送的目标Topic名称，需要提前在控制台创建，如果不创建直接使用会返回报错。
     */
    @Value("${spring.mq.topic}")
    private String topic;
    @Value("${spring.mq.namespace}")
    private String namespace;
    @Value("${spring.mq.accessKey}")
    private String accessKey;
    @Value("${spring.mq.accessSecret}")
    private String accessSecret;

    @Bean("producer")
    public Producer producer() throws ClientException {
        ClientServiceProvider provider = ClientServiceProvider.loadService();
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
        /**
         * 初始化Producer时直接配置需要使用的Topic列表（这个参数可以配置多个Topic），实现提前检查错误配置、拦截非法配置启动。
         * 针对非事务消息 Topic，也可以不配置，服务端会动态检查消息的Topic是否合法。
         * 注意！！！事务消息Topic必须提前配置，以免事务消息回查接口失败，具体原理请参见事务消息。
         */
        org.apache.rocketmq.client.apis.producer.Producer producer = null;
        try {
            producer = provider.newProducerBuilder()
                    .setTopics(topic)
                    .setClientConfiguration(clientConfiguration)
                    .build();
        } catch (ClientException e) {
            throw new RuntimeException(e);
        }
        return producer;
    }
}
