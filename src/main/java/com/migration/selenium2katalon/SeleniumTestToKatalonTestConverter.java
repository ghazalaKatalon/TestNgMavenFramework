package com.migration.selenium2katalon;
import java.io.*;
import java.util.regex.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class SeleniumTestToKatalonTestConverter {



	    public static void main(String[] args) {
	        // Input file path (Selenium test)
	        String inputFilePath = "/Users/ghazalashahin/eclipse-workspace/TestNgMavenFramework1/src/test/java/migration/specs/LoginBasicTest.java";
	        String outputFilePath = "/Users/ghazalashahin/Documents/SetoKSDemo/new/SeleniumJavaMigration/Test Cases/test.groovy";

	                StringBuilder katalonScript = new StringBuilder();

	                // Add imports and initialization for Katalon script
	                katalonScript.append("import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI\n");
	                katalonScript.append("import internal.GlobalVariable as GlobalVariable\n\n");
	                katalonScript.append("WebUI.openBrowser('')\n");

	                try (BufferedReader br = new BufferedReader(new FileReader(inputFilePath))) {
	                    String line;
	                    while ((line = br.readLine()) != null) {
	                        line = line.trim(); // Trim spaces for easier parsing

	                        // Conversion logic
	                        if (line.startsWith("driver.get")) {
	                            katalonScript.append("WebUI.navigateToUrl(GlobalVariable.url)\n");
	                        } else if (line.startsWith("elementActions.enterText")) {
	                            String[] parts = extractLocatorAndData(line);
	                            katalonScript.append("WebUI.setText(findTestObject('")
	                                    .append(parts[0])
	                                    .append("'), GlobalVariable.")
	                                    .append(parts[1])
	                                    .append(")\n");
	                        } else if (line.startsWith("elementActions.clickElement")) {
	                            String locator = extractLocator(line);
	                            katalonScript.append("WebUI.click(findTestObject('")
	                                    .append(locator)
	                                    .append("'))\n");
	                        } else if (line.contains("Assert.assertTrue") && line.contains("isDisplayed")) {
	                            katalonScript.append("WebUI.verifyElementVisible(findTestObject('gitProfile'))\n");
	                        }
	                    }

	                    // Save the converted script to the output file
	                    try (FileWriter writer = new FileWriter(outputFilePath)) {
	                        writer.write(katalonScript.toString());
	                        System.out.println("Katalon test script has been successfully saved to: " + outputFilePath);
	                    } catch (IOException e) {
	                        System.err.println("Error saving the Katalon test script: " + e.getMessage());
	                    }

	                } catch (IOException e) {
	                    System.err.println("Error reading the Selenium test file: " + e.getMessage());
	                }
	            }

	            // Extract locator and data for WebUI.setText
	            private static String[] extractLocatorAndData(String line) {
	                String locator = line.substring(line.indexOf("dashboardPage.") + 13, line.indexOf(",")).trim();
	                String data = line.substring(line.lastIndexOf(",") + 1, line.indexOf(")")).trim();
	                return new String[]{locator.replaceFirst("^\\.", ""), data}; // 
	            }

	            // Extract locator for WebUI.click
	            private static String extractLocator(String line) {
	                return line.substring(line.indexOf("dashboardPage.") + 13, line.indexOf(")")).trim().replaceFirst("^\\.", ""); // Remove the leading dot
	            }
	        }


