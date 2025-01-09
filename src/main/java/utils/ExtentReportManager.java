package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportManager {
    private static ExtentReports extent;
    private static ExtentTest test;

    // Initialize ExtentReports
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

    // Create a new test in the report
    public static ExtentTest createTest(String testName) {
        test = extent.createTest(testName);
        return test;
    }


    // Finalize and write the report
    public static void flushReport() {
        if (extent != null) {
            extent.flush();
        }
    }

}
