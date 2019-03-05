package com.fanruifa.redis;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 集群式Redis
 * 
 * @author Ryan
 *
 */
public class ClusterPoolUtil {

	private static JedisCluster jedisCluster;

	public static JedisCluster getJedisCluster() throws IOException {
		if (jedisCluster == null) {
			InputStream inputStream = JedisPoolUtil.class.getClassLoader()
					.getResourceAsStream("shard-redis-config.properties");
			Properties properties = new Properties();
			properties.load(inputStream);
			JedisPoolConfig config = new JedisPoolConfig();
			int maxActive = Integer.valueOf(properties.getProperty("maxActive"));
			int maxIdle = Integer.valueOf(properties.getProperty("maxIdle"));
			long maxWait = Long.valueOf(properties.getProperty("maxWait"));
			boolean testOnBorrow = Boolean.valueOf(properties.getProperty("testOnBorrow"));
			String[] hosts = properties.getProperty("hosts").split(",");
			Set<HostAndPort> nodes = new HashSet<HostAndPort>();
			for (int i = 0; i < hosts.length; i++) {
				String[] host = hosts[i].split(":");
				nodes.add(new HostAndPort(host[0], Integer.valueOf(host[1])));
			}
			config.setMaxTotal(maxActive);
			config.setMaxIdle(maxIdle);
			config.setMaxWaitMillis(maxWait);
			config.setTestOnBorrow(testOnBorrow);
			int timeout = Integer.valueOf(properties.getProperty("timeout"));
			jedisCluster = new JedisCluster(nodes, timeout, config);
		}
		return jedisCluster;
	}

}
