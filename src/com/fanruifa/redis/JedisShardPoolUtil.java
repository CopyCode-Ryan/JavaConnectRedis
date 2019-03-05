package com.fanruifa.redis;

import java.util.List;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.util.Hashing;
import redis.clients.util.Sharded;

/**
 * 分布式Redis
 * 
 * @author Ryan
 *
 */
public class JedisShardPoolUtil {

	private static ShardedJedisPool jedisPool;

	public static ShardedJedisPool getJedisPool() throws IOException {
		if (jedisPool == null) {
			InputStream inputStream = JedisPoolUtil.class.getClassLoader()
					.getResourceAsStream("shard-redis-config.properties");
			System.out.println(inputStream);
			Properties properties = new Properties();
			properties.load(inputStream);
			JedisPoolConfig config = new JedisPoolConfig();
			int maxActive = Integer.valueOf(properties.getProperty("maxActive"));
			int maxIdle = Integer.valueOf(properties.getProperty("maxIdle"));
			long maxWait = Long.valueOf(properties.getProperty("maxWait"));
			boolean testOnBorrow = Boolean.valueOf(properties.getProperty("testOnBorrow"));
			String[] hosts = properties.getProperty("hosts").split(",");
			List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
			for (int i = 0; i < hosts.length; i++) {
				System.out.println(hosts[i]);
				String[] host = hosts[i].split(":");
				JedisShardInfo jedisShardInfo = new JedisShardInfo(host[0], Integer.valueOf(host[1]));
				shards.add(jedisShardInfo);
			}
			config.setMaxActive(maxActive);
			config.setMaxIdle(maxIdle);
			config.setMaxWait(maxWait);
			config.setTestOnBorrow(testOnBorrow);
			jedisPool = new ShardedJedisPool(config, shards, Hashing.MURMUR_HASH, Sharded.DEFAULT_KEY_TAG_PATTERN);
		}
		return jedisPool;
	}

	public static ShardedJedis getJedis() throws IOException {
		if (jedisPool == null) {
			jedisPool = getJedisPool();
		}
		return jedisPool == null ? null : jedisPool.getResource();
	}
}
