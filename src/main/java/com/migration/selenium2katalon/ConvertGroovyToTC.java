package com.migration.selenium2katalon;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;  // Add this import to fix the compilation error

public class ConvertGroovyToTC {

    public static void main(String[] args) {
        // Input file path (Selenium test)
        String inputFilePath = "/Users/ghazalashahin/eclipse-workspace/TestNgMavenFramework1/src/test/java/migration/specs/LoginBasicTest.java";
        String groovyOutputFilePath = "/Users/ghazalashahin/Documents/SetoKSDemo/new/SeleniumJavaMigration/Test Cases/test.groovy";
        String tcOutputFilePath = "/Users/ghazalashahin/Documents/SetoKSDemo/new/SeleniumJavaMigration/Test Cases/test.tc";

        // Step 1: Convert Selenium test to Katalon .groovy script
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

            // Save the converted Groovy script to the output file
            try (FileWriter writer = new FileWriter(groovyOutputFilePath)) {
                writer.write(katalonScript.toString());
                System.out.println("Katalon test script has been successfully saved to: " + groovyOutputFilePath);
            } catch (IOException e) {
                System.err.println("Error saving the Katalon test script: " + e.getMessage());
            }

        } catch (IOException e) {
            System.err.println("Error reading the Selenium test file: " + e.getMessage());
        }

        // Step 2: Convert the generated Katalon .groovy file to .tc XML file
        convertGroovyToTcXml(groovyOutputFilePath, tcOutputFilePath);
    }

    // Method to convert the .groovy file to .tc XML format
    private static void convertGroovyToTcXml(String groovyFilePath, String tcFilePath) {
        // Generate a unique GUID for the test case
        String testCaseGuid = UUID.randomUUID().toString();  // Now UUID is imported

        try (BufferedReader br = new BufferedReader(new FileReader(groovyFilePath));
             FileWriter writer = new FileWriter(tcFilePath)) {

            // Write XML header for the .tc file
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            writer.write("<TestCaseEntity>\n");
            writer.write("   <description></description>\n");
            writer.write("   <name>test</name>\n");
            writer.write("   <tag></tag>\n");
            writer.write("   <comment></comment>\n");
            writer.write("   <recordOption>OTHER</recordOption>\n");
            writer.write("   <testCaseGuid>" + testCaseGuid + "</testCaseGuid>\n");

            String line;
            int lineNumber = 0;

            // Read the groovy file starting from line 3 (skipping imports)
            while ((line = br.readLine()) != null) {
                lineNumber++;
                if (lineNumber >= 3) { // Skip the first two lines (header lines)
                    // Write each WebUI statement as a <testStep> in XML format
                    writer.write("   <testStep>\n");
                    writer.write("      <keyword>" + line.trim() + "</keyword>\n");
                    writer.write("   </testStep>\n");
                }
            }

            // Close the XML structure
            writer.write("</TestCaseEntity>\n");

            System.out.println("The XML .tc file has been successfully created at: " + tcFilePath);
        } catch (IOException e) {
            System.err.println("Error reading or writing files: " + e.getMessage());
        }
    }

    // Extract locator and data for WebUI.setText
    private static String[] extractLocatorAndData(String line) {
        String locator = line.substring(line.indexOf("dashboardPage.") + 13, line.indexOf(",")).trim();
        String data = line.substring(line.lastIndexOf(",") + 1, line.indexOf(")")).trim();
        return new String[]{locator.replaceFirst("^\\.", ""), data}; // Remove the leading dot
    }

    // Extract locator for WebUI.click
    private static String extractLocator(String line) {
        return line.substring(line.indexOf("dashboardPage.") + 13, line.indexOf(")")).trim().replaceFirst("^\\.", ""); // Remove the leading dot
    }
}
