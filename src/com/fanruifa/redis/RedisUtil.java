package com.fanruifa.redis;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Redis工具类
 * 
 * @author Ryan
 *
 */
public class RedisUtil {

	public static JedisPool initPool() throws IOException {
		InputStream inputStream = RedisUtil.class.getClass().getResourceAsStream("/res/redis-config.properties");
		Properties properties = new Properties();
		properties.load(inputStream);
		JedisPoolConfig config = new JedisPoolConfig();
		int maxActive = Integer.valueOf(properties.getProperty("maxActive"));
		int maxIdle = Integer.valueOf(properties.getProperty("maxIdle"));
		long maxWait = Long.valueOf(properties.getProperty("maxWait"));
		boolean testOnBorrow = Boolean.valueOf(properties.getProperty("testOnBorrow"));
		String[] address = properties.getProperty("ip").split(":");
		int port = Integer.valueOf(address[1]);
		int timeout = Integer.valueOf(properties.getProperty("timeout"));
		config.setMaxActive(maxActive);
		config.setMaxIdle(maxIdle);
		config.setMaxWait(maxWait);
		config.setTestOnBorrow(testOnBorrow);
		JedisPool pool = new JedisPool(config, address[0], port, timeout);
		return pool;
	}

	public static Jedis Jedis(String host, int port) {
		Jedis jedis = new Jedis(host, port);
		return jedis;
	}
}
