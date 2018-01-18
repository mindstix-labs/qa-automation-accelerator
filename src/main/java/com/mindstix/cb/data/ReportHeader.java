/*
 * Copyright (c) 2017 Mindstix Inc.
 */
package com.mindstix.cb.data;

/**
 * This class represents report header
 * 
 * @author Mindstix
 */
public class ReportHeader {
	private String date;

	/**
	 * @param date
	 */
	public ReportHeader(String date) {
		this.date = date;
	}

	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}
}
