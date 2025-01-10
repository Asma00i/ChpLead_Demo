# **Chplead_Demo Project**

**_Project Overview_**

This project is designed to automate API functional and performance tests using RestAssured, JMeter, and Gatling. It incorporates advanced error handling, retry mechanisms, and integration with reporting tools such as Allure and Extent Reports.

**_Features_**

**_API Functional Testing_**

Automated functional tests using RestAssured and TestNG.

Custom error handling and retry logic.

**_Performance Testing_**

Performance tests using JMeter

Simulates high-concurrency scenarios to validate API scalability and response times.

**_Mocked API Testing_**

Mock external services using WireMock.

**_Error Handling_**

Custom error handlers for different failure modes.

Retry logic for transient network issues and server errors.

**_Reporting_**

Integrated with Allure and Extent Reports for generating detailed test execution reports.

**_Known Issues:_**
1. Extent report doesn't include all tests
2. JMeter File is not included however env setup done
3. Email part in pipeline has an issue as GitHub Secrets and Variables has issues with Gmail 2 Step Auth


# Setup Instructions

**_Prerequisites_**

Java 17

Maven 3.8+

JMeter 5.5

# Environment Variables

1. Set JAVA_HOME to the path of your Java installation.
2. Set JMETER_HOME to the path of your JMeter installation.
3. Add %JMETER_HOME%\bin to your system PATH.