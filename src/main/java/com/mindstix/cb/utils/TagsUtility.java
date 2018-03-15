/*
 * Copyright (c) 2017 Mindstix Inc.
 */
package com.mindstix.cb.utils;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import cucumber.api.Scenario;

/**
 * TagsUtility is used to extract the specific tag or tags from given scenario
 * based on the tags pattern. Tags pattern are stored in testdata.properties
 * file.
 * @author Mindstix
 *
 */
public final class TagsUtility {

	/**
	 * private constructor
	 */
	private TagsUtility(){
		
	}
	
	private static List<String> tagsPattern;
	private static Properties testData;

	static {
		testData = PropertiesUtility.getTestdata();
	}

	/**
	 * Takes scenario and tagPatternKey as parameter and returns the tags matching with tags pattern
	 * 
	 * @param scenario
	 * @param tagPatternKey
	 * @return
	 */
	public static String findRequiredTagsFromList(Scenario scenario, String tagPatternKey) {
		tagsPattern = Arrays.asList(testData.getProperty(tagPatternKey).split(","));
		Collection<String> tags = scenario.getSourceTagNames();
		List<String> requiredTag = tags.stream()
				.filter(tag -> (tagsPattern.stream().filter(pattern -> tag.contains(pattern.trim())).count() == 1))
				.collect(Collectors.toList());
		return requiredTag.toString();
	}
}
