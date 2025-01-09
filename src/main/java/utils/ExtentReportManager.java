package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;


import java.io.IOException;

public class ExtentReportManager {
    private static ExtentReports extent;


    public static void initializeReport() {

        ExtentSparkReporter sparkReporter = new ExtentSparkReporter("ExtentReport.html");
        sparkReporter.config().setDocumentTitle("API Test Report");
        sparkReporter.config().setReportName("Reqres API Test Results");
        sparkReporter.config().setTheme(Theme.DARK);


        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);

        extent.setSystemInfo("Tester", "Your Name");
        extent.setSystemInfo("Environment", "QA");
    }

    public static ExtentTest createTest(String testName) {
        ExtentTest test = extent.createTest(testName);
        return test;
    }


    public static void flushReport() {
        if (extent != null) {
            extent.flush();
        }
    }

}
