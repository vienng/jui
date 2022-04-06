package ui;

import java.time.Duration;
import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Driver {
	private WebDriver driver;
	private WebDriverManager driverManager;
	private Config config;

	public Driver(AvailableBrowsers driverName) {
		switch (driverName) {
		case chrome:
			driverManager = WebDriverManager.chromedriver();
			driverManager.setup();
			driver = new ChromeDriver();
			break;
		case firefox:
			driverManager = WebDriverManager.firefoxdriver();
			driverManager.setup();
			driver = new FirefoxDriver();
			break;
		default:
			System.out.println("[error] unsupported driver");
			return;
		}
	}

	public void setup(Config cfg) {
		config = cfg;
		driverManager.clearDriverCache();
		driverManager.timeout(cfg.getTimeoutSeconds());
		driver.get(cfg.getUrl());
	}

	public WebDriver getWebDriver() {
		return driver;
	}

	public void scrollTo(By by) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		WebElement elem = driver.findElement(by);
		js.executeScript("arguments[0].scrollIntoView();", elem);
	}

	public void click(By by) {
		driver.findElement(by).click();
	}

	public void inputText(By by, String text) {
		driver.findElement(by).sendKeys(text);
	}

	public void submitWithGoogleReCaptcha(By submitLocator, ExpectedCondition<WebElement> reachedExpectation) {
		if (config.isCaptchaDisabled()) {
			System.out.println("[warn] captcha is disabled, applied for internal testing/staging environment only!");
			driver.findElement(submitLocator).click();

			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(config.getTimeoutSeconds()));
			wait.until(reachedExpectation);
		} else {
			System.out.println(
					"[warn] captcha is enabled, the purpose is to prevent bots including automation testing. \n"
							+ "Hence, this test execution may requires MANUAL RESOLVING CAPTCHA AND SUBMIT \n"
							+ "or the test will be failed in 300 seconds!"); // ref:
																				// https://www.lambdatest.com/blog/handle-captcha-in-selenium/

			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(300));
			wait.until(reachedExpectation);

//				wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(GoogleReCaptcha.reCaptchaForm));
//				wait.until(ExpectedConditions.elementToBeClickable(GoogleReCaptcha.reCaptchaCheckbox)).click();
//				wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(GoogleReCaptcha.reCaptchaCheckMark));
		}
	}

	public boolean isTextDisplayed(By by, String text) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(config.getTimeoutSeconds()));
		return wait.until(ExpectedConditions.textToBe(by, text));
	}

	public void quit() {
		if (driver != null) {
			driver.quit();
		}
	}

	public String getCurrentURL() {
		return driver.getCurrentUrl();
	}

	public boolean isDisplay(By by) {
		return driver.findElement(by).isDisplayed();
	}
}
