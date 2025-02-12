package com.example.consumer.Jedis;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.jmx.support.RegistrationPolicy;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author lgk
 * @classDesc: 功能描述()
 * @date 2025/2/6
 */
//@Configuration
@EnableMBeanExport(registration = RegistrationPolicy.IGNORE_EXISTING)
public class MyJedisPool {
    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    @Value("${spring.redis.password}")
    private String password;

    @Value("${spring.redis.timeout}")
    private int timeout;

    @Value("${spring.redis.jedis.pool.max-idle}")
    private int maxIdle;

    @Value("${spring.redis.jedis.pool.max-active}")
    private int maxTotal;

    @Value("${spring.redis.jedis.pool.test-on-borrow}")
    private boolean testOnBorrow;

    @Value("${spring.redis.jedis.pool.test-on-return}")
    private boolean testOnReturn;
    @Bean
    public JedisPool jedisPool() {
        JedisPoolConfig config = new JedisPoolConfig();
        // 最大空闲连接数，需自行评估，不超过Redis实例的最大连接数。
        config.setMaxIdle(maxIdle);
        // 最大连接数，需自行评估，不超过Redis实例的最大连接数。
        config.setMaxTotal(maxTotal);
        config.setTestOnBorrow(testOnBorrow);
        config.setTestOnReturn(testOnReturn);

        return new JedisPool(config, host, port, timeout, password);
    }
}
