package com.cbt.tests;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.cbt.utilities.ConfigurationReader;
import com.cbt.utilities.Driver;

public class TestBase {
	protected WebDriver driver;
	protected Actions actions;

	protected ExtentReports report;
	protected ExtentHtmlReporter htmlReporter;
	protected ExtentTest extentLogger;

	@BeforeTest
	public void setUpTest() {
		// actual reporter
		report = new ExtentReports();
		// get the path to curent project
		// test-output -->folder in the curent project, will be created by testng if it
		// does not alrady exist.
		String filePath = System.getProperty("./Reports/report.html");
		htmlReporter = new ExtentHtmlReporter(filePath);
		report.attachReporter(htmlReporter);
 
		report.setSystemInfo("ENV", "staging");
		report.setSystemInfo("browser", ConfigurationReader.getProperty("browser"));
		htmlReporter.config().setReportName("Web Orders Automated Test Reports");

	}

	@BeforeMethod
	public void setUp() {
		driver = Driver.getDriver();
		actions = new Actions(driver);
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.manage().window().fullscreen();
		driver.get("http://secure.smartbearsoftware.com/samples/TestComplete12/WebOrders/Login.aspx");

	}

	@AfterMethod
	public void tearDown() {
		Driver.closeDriver();
	}
	
	@AfterMethod
public void tearDown(ITestResult result) throws IOException {
	// checikng if the test method failed
		if (result.getStatus()==ITestResult.FAILURE) {
			//Capture the name of the test method
			extentLogger.fail(result.getName());
			//Capture the exception thrown
			extentLogger.fail(result.getThrowable());
	
		}else if(result.getStatus()==ITestResult.SKIP) {
			extentLogger.skip("Test Case Skipped is "+ result.getName());
		}
		
		Driver.closeDriver();
	
	}
	@AfterTest
	public void tearDownTest() {

		report.flush();
	 	
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
