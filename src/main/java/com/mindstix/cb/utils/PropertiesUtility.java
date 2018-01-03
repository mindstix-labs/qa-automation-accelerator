/*
 * Copyright (c) 2017 Mindstix Inc.
 */
package com.mindstix.cb.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import com.mindstix.cb.data.Signature;
import com.mindstix.cb.data.SignatureCoordinates;

/**
 * Loads different properties
 * <ul>
 * <li>Driver Properties</li>
 * <li>Selector Properties</li>
 * <li>Test Data Properties</li>
 * <ul>
 * 
 * @author Mindstix
 */
public final class PropertiesUtility {

	private final static Logger LOGGER = LoggerFactory.getLogger(PropertiesUtility.class);

	private static final Properties driverProperties;
	private static final Properties selectors;
	private static final Properties testData;

	private static Yaml yamlSignature;

	private static Signature signature;

	private static List<SignatureCoordinates> allSignatureCoordinates;
	private static boolean loadProperty = false;

	static {
		driverProperties = new Properties();
		selectors = new Properties();
		testData = new Properties();
		loadPoperties();
		loadSignatureData();
	}

	private PropertiesUtility() {

	}

	public static Properties getDriverProperties() {
		return driverProperties;
	}

	public static Properties getSelectors() {
		return selectors;
	}

	public static Properties getTestdata() {
		return testData;
	}

	/**
	 * Takes signature ID as parameter and Returns the list of coordinates of
	 * signature
	 * 
	 * @param signID
	 * @return AssociateUserData
	 */
	public static List<Point> getSignatureCoordinates(String signID) {
		for (SignatureCoordinates signatureCoordinates : allSignatureCoordinates) {
			if (signatureCoordinates.getSignatureID().equals(signID)) {
				return signatureCoordinates.getCoordinates();
			}
		}
		LOGGER.info("Signature coordinates not found with signature ID {}", signID);
		return null;
	}

	/**
	 * Method to load properties according to mentioned Web Site
	 *
	 */
	private static void loadPoperties() {
		InputStream dataInput = null;
		if (!loadProperty) {
			try {
				dataInput = new FileInputStream("src/test/resources/driverconfig.properties");
				driverProperties.load(dataInput);
				dataInput.close();
				dataInput = new FileInputStream("src/test/resources/selectors.properties");
				selectors.load(dataInput);
				dataInput.close();
				LOGGER.info("Loading Selector properties");
				dataInput = new FileInputStream("src/test/resources/testdata/testdata.properties");
				testData.load(dataInput);
				dataInput.close();
				LOGGER.info("Loading Test Data properties");
			} catch (Exception ex) {
				LOGGER.error("An Exception occurred!", ex);
			} finally {
				IOUtils.closeQuietly(dataInput);
			}
		}
	}

	/**
	 * Method to load Signature data
	 *
	 */
	private static void loadSignatureData() {
		InputStream dataInput = null;
		try {
			dataInput = new FileInputStream("src/test/resources/testdata/signaturedata.yml");
			Constructor signContructor = new Constructor(Signature.class);
			TypeDescription signDescription = new TypeDescription(SignatureCoordinates.class);
			signDescription.putMapPropertyType("signatures", SignatureCoordinates.class, Object.class);
			signContructor.addTypeDescription(signDescription);
			yamlSignature = new Yaml(signContructor);
			signature = (Signature) yamlSignature.load(dataInput);
			allSignatureCoordinates = signature.getSignatures();
			LOGGER.info("Loaded Signature Coordinates Data");
		} catch (IOException ex) {
			LOGGER.error("File not found!", ex);
		} finally {
			IOUtils.closeQuietly(dataInput);
		}

	}
}
