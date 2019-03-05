package com.fanruifa.redis;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 单机版Redis
 * 
 * @author Ryan
 *
 */
public class JedisPoolUtil {

	private static JedisPool jedisPool;

	public static JedisPool getJedisPool() throws IOException {
		if (jedisPool == null) {
			InputStream inputStream = JedisPoolUtil.class.getClassLoader().getResourceAsStream("redis-config.properties");
			System.out.println(inputStream);
			Properties properties = new Properties();
			properties.load(inputStream);
			JedisPoolConfig config = new JedisPoolConfig();
			int maxActive = Integer.valueOf(properties.getProperty("maxActive"));
			int maxIdle = Integer.valueOf(properties.getProperty("maxIdle"));
			long maxWait = Long.valueOf(properties.getProperty("maxWait"));
			boolean testOnBorrow = Boolean.valueOf(properties.getProperty("testOnBorrow"));
			String host = properties.getProperty("host");
			int port = Integer.valueOf(properties.getProperty("port"));
			int timeout = Integer.valueOf(properties.getProperty("timeout"));
			config.setMaxActive(maxActive);
			config.setMaxIdle(maxIdle);
			config.setMaxWait(maxWait);
			config.setTestOnBorrow(testOnBorrow);
			jedisPool = new JedisPool(config, host, port, timeout);
		}
		return jedisPool;
	}

	public static Jedis getJedis() throws IOException {
		if(jedisPool == null) {
			jedisPool = getJedisPool();
		}
		return jedisPool == null ? null : jedisPool.getResource();
	}
}
