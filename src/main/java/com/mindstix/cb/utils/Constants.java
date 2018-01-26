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
