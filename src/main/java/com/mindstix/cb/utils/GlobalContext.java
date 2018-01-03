/*
 * Copyright (c) 2017 Mindstix Inc.
 */
package com.mindstix.cb.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Used to hold information across entire test run - like all orders placed.
 * Useful for reporting.
 *
 * @author Mindstix
 */
public final class GlobalContext {
	private final static Logger LOGGER = LoggerFactory.getLogger(GlobalContext.class);
	private static List<String> allOrderNumbers;

	static {
		allOrderNumbers = Collections.synchronizedList(new ArrayList<>());
	}

	private GlobalContext() {

	}

	public static void addOrderNumberToList(String orderNumber) {
		allOrderNumbers.add(orderNumber);
	}

	public static synchronized void printAllOrderNumbers() {
		LOGGER.info("All Order Numbers:");
		for (String orderNumber : allOrderNumbers) {
			LOGGER.info("{}", orderNumber);
		}
	}
}
