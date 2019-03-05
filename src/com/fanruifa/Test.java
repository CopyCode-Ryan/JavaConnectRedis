package com.fanruifa;

import com.fanruifa.redis.JedisPoolUtil;

import redis.clients.jedis.Jedis;

public class Test {
	public static void main(String[] args) {
		Jedis jedis = null;
		try {
			jedis = JedisPoolUtil.getJedisPool().getResource();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (jedis != null) {
			jedis.set("name", "123456723");
			System.out.println("name:::" + jedis.get("name"));
		}
	}
}
