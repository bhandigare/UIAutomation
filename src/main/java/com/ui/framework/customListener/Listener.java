package com.ui.framework.customListener;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.ui.framework.baseTest.BaseTest;

public class Listener extends BaseTest implements ITestListener {
	
	public void onTestStart(ITestResult result) {
		Reporter.log("Test Started: "+result.getMethod().getMethodName());
	}

	public void onTestSuccess(ITestResult result) {
		Reporter.log("Test Success: "+result.getMethod().getMethodName());
	}

	public void onTestFailure(ITestResult result) {
		Reporter.log("Test Failed: "+result.getMethod().getMethodName());
	}

	public void onTestSkipped(ITestResult result) {
		Reporter.log("Test Skipped: "+result.getMethod().getMethodName());
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		Reporter.log("Test FailedButWithinSuccessPercentage: "+result.getMethod().getMethodName());
	}

	public void onStart(ITestContext context) {
	}

	public void onFinish(ITestContext context) {
	}

}
