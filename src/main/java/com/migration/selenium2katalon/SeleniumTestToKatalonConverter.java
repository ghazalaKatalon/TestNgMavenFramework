package com.migration.selenium2katalon;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class SeleniumTestToKatalonConverter {




	    public static void main(String[] args) {
	        // Define input and output paths
	        String inputFilePath = "/Users/ghazalashahin/Documents/SetoKSDemo/new/TestNgMavenFramework/src/test/java/migration/specs/LoginBasicTest.java";
	        
	        // Define the output directory (Test Cases folder in the Katalon project)
	        String outputDirectoryPath = "/Users/ghazalashahin/Documents/SetoKSDemo/new/SeleniumJavaMigration/Test Cases";
	        
	        // Make sure to escape spaces in paths when handling file/directory creation
	        outputDirectoryPath = outputDirectoryPath.replace(" ", "\\ ");

	        try {
	            // Read the Selenium test class
	            List<String> seleniumTestLines = Files.readAllLines(Paths.get(inputFilePath));
	            List<String> katalonTestLines = new ArrayList<>();

	            // Get the class name from the input file by finding the class declaration line
	            String className = null;
	            for (String line : seleniumTestLines) {
	                if (line.trim().startsWith("public class ")) {
	                    className = line.split(" ")[2];
	                    break;
	                }
	            }

	            if (className == null) {
	                System.err.println("Error: Could not find class name in the input file.");
	                return;
	            }

	            // Define the output file path with the class name
	            String outputFilePath = outputDirectoryPath + "/" + className + ".groovy";

	            // Create directories if not exists
	            Path outputPath = Paths.get(outputFilePath);
	            if (Files.notExists(outputPath.getParent())) {
	                Files.createDirectories(outputPath.getParent());
	            }

	            // Start writing Katalon import statements and script setup
	            katalonTestLines.add("import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint");
	            katalonTestLines.add("import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase");
	            katalonTestLines.add("import static com.kms.katalon.core.testdata.TestDataFactory.findTestData");
	            katalonTestLines.add("import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject");
	            katalonTestLines.add("import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI");
	            katalonTestLines.add("import internal.GlobalVariable as GlobalVariable");
	            katalonTestLines.add("import org.openqa.selenium.Keys as Keys");
	            katalonTestLines.add("");

	            // Parse the Selenium test and convert it to Katalon syntax
	            for (String line : seleniumTestLines) {
	                line = line.trim();

	                if (line.startsWith("driver.get")) {
	                    // Convert driver.get("URL") to WebUI.navigateToUrl("URL")
	                    String url = line.substring(line.indexOf("\""), line.lastIndexOf("\"") + 1);
	                    katalonTestLines.add("WebUI.openBrowser('')");
	                    katalonTestLines.add("WebUI.navigateToUrl(" + url + ")");
	                } else if (line.contains("elementActions.enterText")) {
	                    // Convert enterText to WebUI.setText
	                    String[] parts = line.split(",");
	                    String element = parts[0].substring(parts[0].lastIndexOf("(") + 1).trim();
	                    String text = parts[1].substring(0, parts[1].lastIndexOf(")")).trim();
	                    katalonTestLines.add("WebUI.setText(findTestObject('" + element + "'), " + text + ")");
	                } else if (line.contains("elementActions.clickElement")) {
	                    // Convert clickElement to WebUI.click
	                    String element = line.substring(line.lastIndexOf("(") + 1, line.lastIndexOf(")")).trim();
	                    katalonTestLines.add("WebUI.click(findTestObject('" + element + "'))");
	                } else if (line.contains("Assert.assertTrue")) {
	                    // Convert Assert.assertTrue to WebUI.verifyElementPresent
	                    String condition = line.substring(line.indexOf("(") + 1, line.lastIndexOf(",")).trim();
	                    katalonTestLines.add("WebUI.verifyElementPresent(" + condition + ", 10)");
	                } else if (line.contains("setup.tearDown")) {
	                    // Convert teardown to WebUI.closeBrowser
	                    katalonTestLines.add("WebUI.closeBrowser()");
	                } else if (line.contains("String username") || line.contains("String password")) {
	                    // Pass through username and password declaration lines
	                    katalonTestLines.add(line.replace("String", ""));
	                }
	            }

	            // Write the Katalon test case to a .groovy file
	            Files.write(Paths.get(outputFilePath), katalonTestLines);
	            System.out.println("Conversion successful! Output saved to: " + outputFilePath);

	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	}
