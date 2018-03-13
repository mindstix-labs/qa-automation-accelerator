/*
 * Copyright (c) 2017 Mindstix Inc.
 */
package com.mindstix.cb.utils;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;

/**
 * This utility handles all the Redis database and Jedis related operations.
 * Following operations are handled by RedisUtility.java
 * <ol>
 * <li>Distributed coordination for running tests with specific set of users.
 * </li>
 * <li>Acting as a temporary storage for report data and notification stages in
 * Jenkins pipeline</li>
 * </ol>
 * Please refer following URLs for more information about Redis and Jedis
 * <ol>
 * <li><a href="https://redis.io/commands">Redis Commands</a></li>
 * <li><a href="http://www.baeldung.com/jedis-java-redis-client-library"> Jedis:
 * java redis client library</a></li>
 * </ol>
 * 
 * @author Mindstix
 */
public final class RedisUtility {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(RedisUtility.class);

	private static JedisPool pool;
	private static Jedis jedis;

	private static boolean isRedisAlive;

	/**
	 * private constructor
	 */
	private RedisUtility() {
	}

	static {
		configureJedisPool();
	}

	/**
	 * This method is used to configure Jedis pool. It configures the host and
	 * port of Jedis pool. It takes redisHost from command line. If not passed from command line 
	 * it takes localhost as default redis host. You can change the default redisHost in build.gradle.
	 * For remaining Redis configurations please @see Constants.java
	 * 
	 */
	private static void configureJedisPool() {
		try {
			LOGGER.info("Configuring jedis pool");
			pool = new JedisPool(System.getProperty("env.redisHost"), Constants.REDIS_PORT);
			jedis = pool.getResource();
			isRedisAlive = true;
		} catch (Exception e) {
			LOGGER.error("Failed to configure redis", e);
			isRedisAlive = false;
		}
	}

	/**
	 * This method is used to acquire username which are stored in Redis set. If
	 * usernames are available in set then it randomly pops one user else it
	 * waits for 10 minutes for user to be available. If after 10 minutes user
	 * is not available then it throws RunTimeException. It takes set-key as
	 * parameter
	 * 
	 * @param userKey
	 * @return userName
	 */
	public static String acquireUser(String userKey) {
		if (!isRedisAlive) {
			String userName = Constants.FALLBACK_USER;
			LOGGER.warn("Returning fallback user {} as jedis is not configured", userName);
			return userName;
		}

		LOGGER.info("Acquiring user");
		String userName = null;
		List<Object> result = null;
		Transaction transaction;
		for (int i = 0; i < Constants.WAIT_TIMEOUT_IN_SEC; i++) {
			transaction = jedis.multi();
			transaction.spop(userKey);
			result = transaction.exec();
			if (result != null) {
				break;
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				LOGGER.error("InterruptedException", e);
			}
		}
		if (result == null) {
			throw new RuntimeException("Time limit exceeded to acquire the user");
		} else {
			userName = result.get(0).toString();
		}
		LOGGER.info("User {} acquired", userName);
		return userName;
	}

	/**
	 * This method is used to add or release user back to redis set. It takes
	 * userKey and username as parameter.
	 * 
	 * @param userKey
	 * @param userName
	 */
	public static void releaseUser(String userKey, String userName) {
		if (userName != null && isRedisAlive) {
			LOGGER.info("Releasing user {}.", userName);
			Transaction transaction = jedis.multi();
			transaction.sadd(userKey, userName);
			transaction.exec();
			LOGGER.info("User {} released.", userName);
		}
	}

}
