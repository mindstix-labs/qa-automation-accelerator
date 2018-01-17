/*
 * Copyright (c) 2017 Mindstix Inc.
 */
package com.mindstix.cb.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import com.mindstix.cb.data.Report;

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

	public static void setupReportHeader() {
		LOGGER.info("Setting up report header!");
		Map<String, Object> emailHeader = new HashMap<String, Object>();
		SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy hh:mm:ss z");
		Date localDate = new Date();
		String date = formatter.format(localDate).toString();
		emailHeader.put("Date", date);
		FreeMarkerUtility.processTemplateInFile(emailHeader, "emailheader.ftl", false);
	}

	public static void setupReportTable() {
		LOGGER.info("Setting up report table!");
		File reportDirectory = new File(PropertiesUtility.getEmailconfigs().getProperty("reortDataLocation"));
		Constructor reportConstructor = new Constructor(Report.class);
		Map<String, Object> dataModel = new HashMap<String, Object>();
		File[] files = reportDirectory.listFiles();
		if (files != null && files.length > 1) {
			for (File reportFile : files) {
				try {
					if (reportFile.isFile()) {
						LOGGER.info("Loading report data from file {}", reportFile.getName());
						InputStream inputStream = new FileInputStream(reportFile);
						Yaml yaml = new Yaml(reportConstructor);
						Report report = (Report) yaml.load(inputStream);
						if (report != null) {
							dataModel.put("report", report.getReport());
							FreeMarkerUtility.processTemplateInFile(dataModel, "reportData.ftl", true);
						}
					}
				} catch (Exception e) {
					LOGGER.error("Operation failed while reading file {}", reportFile.getName(), e);
				}
			}
		} else {
			throw new RuntimeException("Report is not avilable for current build");
		}
	}

	public static void setupReportFooter() {
		LOGGER.info("Setting up report footer!");
		FreeMarkerUtility.processTemplateInFile("emailfooter.ftl", true);
	}

	public static void emailReportTo(String to) {
		try {
			String report = new String(Files.readAllBytes(Paths.get(PropertiesUtility.getEmailconfigs().getProperty("finalReportHTMLFile"))), "UTF-8");
			MailUtility.sendMail(to, "Report from Last Build", report);
		} catch (IOException e) {
			LOGGER.error("Operation failed while fetching report", e);
		}
	}
}
