package com.fanruifa;

import com.fanruifa.redis.RedisUtil;

import redis.clients.jedis.Jedis;

public class Test {
	public static void main(String[] args) {
		Jedis jedis = RedisUtil.Jedis("127.0.0.1", 6379);
		jedis.set("name", "1234567");
		System.out.println("name:::" + jedis.get("name"));
	}
}
