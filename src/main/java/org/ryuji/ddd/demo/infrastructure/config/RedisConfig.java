package org.ryuji.ddd.demo.infrastructure.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @program: showyou-core
 * @description:
 * @author: LiuXi
 * @create: 2021-08-12 00:43
 */
@Configuration
public class RedisConfig {
    @Value("${jedis.host}")
    private String host;

    @Value("${jedis.port}")
    private Integer port;

    @Value("${jedis.timeout}")
    private Integer timeout;

    @Value("${jedis.password}")
    private String password;

    @Value("${jedis.database}")
    private Integer database;

    @Value("${jedis.maxIdle}")
    private Integer maxIdle;

    @Value("${jedis.maxWaitMillis}")
    private Integer maxWaitMillis;

    @Value("${jedis.blockWhenExhausted}")
    private Boolean blockWhenExhausted;

    @Value("${jedis.JmxEnabled}")
    private Boolean JmxEnabled;

    @Bean
    public JedisPool jedisPoolFactory() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
        // 连接耗尽时是否阻塞, false报异常,true阻塞直到超时, 默认true
        jedisPoolConfig.setBlockWhenExhausted(blockWhenExhausted);
        // 是否启用pool的jmx管理功能, 默认true
        jedisPoolConfig.setJmxEnabled(JmxEnabled);

        JedisPool jedisPool = new JedisPool(jedisPoolConfig, host, port, timeout, password, database);

        return jedisPool;
    }
}