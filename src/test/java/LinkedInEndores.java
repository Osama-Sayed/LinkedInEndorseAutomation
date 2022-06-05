import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarException;

public class LinkedInEndores {
    WebDriver driver;
    WebDriverWait wait;
    @BeforeTest
    public void StartDriver() {
       // System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/drivers/chromedriver.exe");
        WebDriverManager.chromedriver().setup();

        //Create driver object for Chrome
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    @Test(priority = 1)
    public void linkInLogin(){
        driver.navigate().to("https://www.linkedin.com/");
        WebElement email= wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("session_key")));
        WebElement password= wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("session_password")));
        WebElement login= wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("sign-in-form__submit-button")));

        //put your email here
        email.sendKeys("@gmail.com");
        //put your password here
        password.sendKeys("password");
        login.click();
    }
    @Test(retryAnalyzer = testCasesRetry.Retry.class, priority = 2)
    public void linkedInEndores(){
        //Put the target Linked in Profile here
        String URL = "https://www.linkedin.com/UserName";

        //Put the number of skills here
        int skillNumber=50;

        driver.get(URL);
        WebElement showAllSkills= wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Show all "+skillNumber+" skills")));
        showAllSkills.click();
        List<WebElement> endorsBtns = new ArrayList<>();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(@class, 'artdeco-button__text') and text() = 'Endorse']")));
        endorsBtns = driver.findElements(By.xpath("//span[contains(@class, 'artdeco-button__text') and text() = 'Endorse']"));
        for(int i=0;i<endorsBtns.size();i++)
            try {
                endorsBtns.get(i).click();
            }catch (Exception e){
                var jes = ((JavascriptExecutor) driver);
                jes.executeScript("window.scrollTo(0,document.body.scrollHeight)");
                ++i;
                endorsBtns.get(i).click();
            }

    }

    @AfterTest
    public void tearDown(){
        driver.quit();
    }
}
