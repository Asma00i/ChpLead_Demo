@echo off

:: Step 1: Run Tests
mvn clean test

:: Step 2: Open Extent Report in Chrome
start chrome ExtentReport.html
