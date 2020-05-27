package tests;

import application.ApplicationManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import java.util.Arrays;

public class TestListener implements ITestListener {
    Logger logger = LoggerFactory.getLogger(BaseTest.class);

    @Override
    public void onTestStart(ITestResult result) {
        logger.info("Start test " + result.getName() + " with parameters " + Arrays.asList(result.getParameters()));
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        logger.info("Test passed " + result.getName() + " with parameters " + Arrays.asList(result.getParameters()));
    }

    @Override
    public void onTestFailure(ITestResult result) {
        logger.info("Test failed " + result.getName() + " with parameters " + Arrays.asList(result.getParameters()));
        ApplicationManager app = (ApplicationManager) result.getTestContext().getAttribute("app");
        app.takeScreenshot(result.getName());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        logger.info("Test skipped " + result.getName() + " with parameters " + Arrays.asList(result.getParameters()));
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {

    }

    @Override
    public void onStart(ITestContext iTestContext) {

    }

    @Override
    public void onFinish(ITestContext iTestContext) {

    }
}
