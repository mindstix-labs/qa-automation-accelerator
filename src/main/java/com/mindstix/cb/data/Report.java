/*
 * Copyright (c) 2017 Mindstix Inc.
 */
package com.mindstix.cb.data;

import java.util.List;

/**
 * Represents Report
 * 
 * @author Mindstix
 */
public class Report {
	private List<ReportData> reportData;

	/**
	 * @return the reportData
	 */
	public List<ReportData> getReport() {
		return reportData;
	}

	/**
	 * @param reportData
	 *            the reportData to set
	 */
	public void setReport(List<ReportData> reportData) {
		this.reportData = reportData;
	}
}
