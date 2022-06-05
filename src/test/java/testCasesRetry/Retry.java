package testCasesRetry;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import java.util.concurrent.atomic.AtomicInteger;

public class Retry implements IRetryAnalyzer {

    private static final int maxRetryCount=10;
    AtomicInteger count = new AtomicInteger(maxRetryCount);

    public boolean isRetryAvailable() {
        return (count.intValue() > 0);
    }


    @Override
    public boolean retry(ITestResult result) {
        boolean retry = false;
        if(isRetryAvailable())
        {
            System.out.println("Going to retry test case: " + result.getMethod() + ", " + (maxRetryCount - count.intValue() + 1) + " out of " + maxRetryCount);
            retry = true;
            count.decrementAndGet();
        }
        /*System.out.println("Failed!!");
        System.out.println("Taking Screenshot.....");
        ScreenShots.captureScreenshot(driver,result.getName());*/
        return retry;
    }




}
