package utils;

import helpers.UIHelper;
import org.openqa.selenium.NoSuchSessionException;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.TestListenerAdapter;

public class TestListener extends TestListenerAdapter {

    UIHelper uiHelper = new UIHelper();

    @Override
    public void onTestFailure(ITestResult tr) {
        Reporter.log("The test " + tr.getName() + " is Failed " + tr.getStatus());
        String methodName = tr.getName();
        try {
            uiHelper.takeScreenShot(methodName);
        } catch (NoSuchSessionException e) {
            System.out.println("No session, no UI for screenshot");
        }
    }

    @Override
    public void onTestSkipped(ITestResult tr) {
    }

    @Override
    public void onTestSuccess(ITestResult tr) {
        Reporter.log("The test " + tr.getName() + " is succeeded " + tr.getStatus());
        String methodName = tr.getName();

        try {
            uiHelper.takeScreenShot(methodName);
        } catch (NoSuchSessionException e) {
            System.out.println("No session, no UI for screenshot");
        }

    }

}

