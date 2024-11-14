package com.migration.selenium2katalon;

import java.io.*;
import java.nio.file.*;
import java.util.UUID;
import java.util.regex.*;

public class SeleniumObjectsToKatalonConverter {


	

	    public static void main(String[] args) {
	        System.out.println("Starting Conversion");

	        String inputFile = "/Users/ghazalashahin/Documents/SetoKSDemo/new/TestNgMavenFramework/src/test/java/migration/pages/DashboardPage.java";
	        String outputDir = "/Users/ghazalashahin/Documents/SetoKSDemo/new/SeleniumJavaMigration/Object Repository";

	        // Create output directory if it does not exist
	        try {
	            Files.createDirectories(Paths.get(outputDir));
	        } catch (IOException e) {
	            e.printStackTrace();
	        }

	        try {
	            String data = new String(Files.readAllBytes(Paths.get(inputFile)));

	            // Updated regex to match multiple locator types (xpath, name, id, cssSelector, className)
	            Pattern pattern = Pattern.compile(
	                    "public\\s+By\\s+(\\w+)\\s*=\\s*By\\.(name|id|xpath|cssSelector|className)\\([\"'](.+?)[\"']\\)\\s*;"
	            );
	            Matcher matcher = pattern.matcher(data);

	            while (matcher.find()) {
	                String elementName = matcher.group(1);
	                String locatorType = matcher.group(2);
	                String locatorValue = matcher.group(3);
	                String guid = UUID.randomUUID().toString();

	                // Convert locator type to Katalon's XML structure
	                String katalonObjectXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
	                        "<WebElementEntity>\n" +
	                        "   <description></description>\n" +
	                        "   <name>" + elementName + "</name>\n" +
	                        "   <tag></tag>\n" +
	                        "   <elementGuidId>" + guid + "</elementGuidId>\n" +
	                        "   <selectorCollection>\n" +
	                        "      <entry>\n" +
	                        "         <key>" + locatorType.toUpperCase() + "</key>\n" +
	                        "         <value>" + locatorValue + "</value>\n" +
	                        "      </entry>\n" +
	                        "   </selectorCollection>\n" +
	                        "   <selectorMethod>" + locatorType.toUpperCase() + "</selectorMethod>\n" +
	                        "   <smartLocatorEnabled>false</smartLocatorEnabled>\n" +
	                        "   <useRalativeImagePath>true</useRalativeImagePath>\n" +
	                        "</WebElementEntity>";

	                Path outputFilePath = Paths.get(outputDir, elementName + ".rs");
	                Files.write(outputFilePath, katalonObjectXML.getBytes());
	                System.out.println("Generated: " + outputFilePath);
	            }
	        } catch (IOException e) {
	            System.err.println("Error reading file: " + e.getMessage());
	        }
	    }
	}
