package com.fanruifa;

import com.fanruifa.redis.ClusterPoolUtil;
import com.fanruifa.redis.JedisPoolUtil;
import com.fanruifa.redis.JedisShardPoolUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.ShardedJedis;

public class Test {
	public static void main(String[] args) {
		Jedis jedis = null;
		ShardedJedis shardedJedis = null;
		JedisCluster cluster = null;
		try {
			jedis = JedisPoolUtil.getJedis();
			shardedJedis = JedisShardPoolUtil.getJedis();
			cluster = ClusterPoolUtil.getJedisCluster();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (jedis != null) {
			jedis.set("name", "123456723");
			System.out.println("name:::" + jedis.get("name"));
		}
		if (shardedJedis != null) {
			shardedJedis.set("a", "123456");
			System.out.println("bookid:::" + shardedJedis.get("bookid"));
		}
		if (cluster != null) {
			cluster.set("frf", "0000");
			System.out.println("bookid:::" + cluster.get("frf"));
		}
	}
}
