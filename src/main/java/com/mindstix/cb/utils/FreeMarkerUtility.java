/*
 * Copyright (c) 2017 Mindstix Inc.
 */
package com.mindstix.cb.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.Version;

/**
 * This Class handles following operations 
 * <li>Configuring FreeMarker properties</li> 
 * <li>Processing FreeMarker templates</li> 
 * <li>Processing templates in file</li>
 * 
 * @author Mindstix
 */
public final class FreeMarkerUtility {

	private final static Logger LOGGER = LoggerFactory.getLogger(FreeMarkerUtility.class);

	private static Configuration configuration;
	private static Properties emailConfigs;

	/**
	 * private constructor
	 */
	private FreeMarkerUtility() {
	}

	static {
		emailConfigs = PropertiesUtility.getEmailconfigs();
		configureFreeMarkerProperties();
	}

	private static void configureFreeMarkerProperties() {
		LOGGER.info("Loading freemarker configurations...");
		configuration = new Configuration(new Version("2.3.27"));
		configuration.setClassForTemplateLoading(FreeMarkerUtility.class, emailConfigs.getProperty("freemarkerEmailTemplateFolder"));
		configuration.setDefaultEncoding("UTF-8");
		configuration.setLocale(Locale.US);
		configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
		LOGGER.info("Freemarker configurations loaded successfully!");
	}

	public static void processTemplateInFile(String templateName, boolean append) {
		try {
			LOGGER.info("Loading template {} ...", templateName);
			File file = new File(emailConfigs.getProperty("finalReportHTMLFile"));
			FileOutputStream fileOutputStream = new FileOutputStream(file, append);
			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8);
			Template template = configuration.getTemplate(templateName);
			template.dump(outputStreamWriter);
			outputStreamWriter.close();
			LOGGER.info("Template loaded and processed successfully!");
		} catch (Exception e) {
			LOGGER.error("Operation failed!", e);
		}
	}

	public static void processTemplateInFile(Map<String, Object> dataModel, String templateName, boolean append) {
		try {
			File file = new File(emailConfigs.getProperty("finalReportHTMLFile"));
			FileOutputStream fileOutputStream = new FileOutputStream(file, append);
			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8);
			Template template = configuration.getTemplate(templateName);
			template.process(dataModel, outputStreamWriter);
			outputStreamWriter.close();
			LOGGER.info("Template loaded and processed successfully!");
		} catch (Exception e) {
			LOGGER.error("Operation failed!", e);
		}
	}
}
