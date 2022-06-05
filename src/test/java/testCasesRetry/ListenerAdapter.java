package testCasesRetry;

import org.testng.*;

import java.util.Iterator;

public class ListenerAdapter extends TestListenerAdapter {
    @Override
    public void onTestFailure(ITestResult result) {
        if (result.getMethod().getRetryAnalyzer(result) != null) {
            Retry retryAnalyzer = (Retry)result.getMethod().getRetryAnalyzer(result);

            if(retryAnalyzer.isRetryAvailable()) {
                result.setStatus(ITestResult.SKIP);
            } else {
                result.setStatus(ITestResult.FAILURE);
            }
            Reporter.setCurrentTestResult(result);
        }
    }

    @Override
    public void onFinish(ITestContext context) {
        super.onFinish(context);
        Iterator<ITestResult> failedTestCases =context.getFailedTests().getAllResults().iterator();
        while (failedTestCases.hasNext()) {
            System.out.println("failedTestCases");
            ITestResult failedTestCase = failedTestCases.next();
            ITestNGMethod method = failedTestCase.getMethod();
            if (context.getFailedTests().getResults(method).size() > 1) {
                System.out.println("failed test case remove as dup:" + failedTestCase.getTestClass().toString());
                failedTestCases.remove();
            } else {

                if (context.getPassedTests().getResults(method).size() > 0) {
                    System.out.println("failed test case remove as pass retry:" + failedTestCase.getTestClass().toString());
                    failedTestCases.remove();
                }
            }
        }
/*
        Iterator<ITestResult> skippedTestCases =context.getSkippedTests().getAllResults().iterator();
        while (skippedTestCases.hasNext()) {
            System.out.println("skippedTestCases");
            ITestResult skippedTestCase = skippedTestCases.next();
            ITestNGMethod method = skippedTestCase.getMethod();
            if (context.getSkippedTests().getResults(method).size() > 1) {
                System.out.println("skipped test case remove as dup:" + skippedTestCase.getTestClass().toString());
                skippedTestCases.remove();
            } else {

                if (context.getPassedTests().getResults(method).size() > 0) {
                    System.out.println("skipped test case remove as pass retry:" + skippedTestCase.getTestClass().toString());
                    skippedTestCases.remove();
                }
            }
        }*/
    }

}
