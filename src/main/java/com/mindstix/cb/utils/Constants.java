/*
 * Copyright (c) 2017 Mindstix Inc.
 */
package com.mindstix.cb.utils;

/**
 * 
 * @author Mindstix
 *
 */
public final class Constants {

	/**
	 * @see RedisUtility.java
	 */
	public static final int REDIS_PORT = 6379;
	public static final String USERS_KEY = "users";
	public static final int LOCK_TIMEOUT = 2000;
	public static final String LEADER_LOCK = "LEADER_LOCK";
	public static final int WAIT_TIMEOUT_IN_SEC = 600;
	public static final String FALLBACK_USER = "test1@mx.com";
	
	/**
	 * Constructor
	 */
	private Constants() {
	}
	
	public static enum Platform {
		LOCAL, BROWSERSTACK, SAUCELABS
	}

	public static enum Browser {
		CHROME, FIREFOX, IE
	}

	public static enum Mode {
		NORMAL, HEADLESS, GRID, ICOGNITO
	}
}
