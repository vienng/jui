package ui;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;

import io.github.bonigarcia.wdm.WebDriverManager;
import ui.musala.GoogleReCaptcha;
import ui.musala.MusalaWebApplication;

public class Driver {
	private WebDriver driver;
	private WebDriverManager driverManager;

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

	public void setupPage(Config cfg) {
		driverManager.timeout(cfg.getTimeoutSeconds());
		driver.get(cfg.getUrl());
	}

	public WebDriver getDriver() {
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

	public void byPassGoogleReCaptcha(Config cfg) {
		if (cfg.isCaptchaDisabled()) {
			System.out.println("[warn] captcha is disabled, applied for internal testing/staging environment only!");
			return;
		} else {
			System.out.println(
					"[warn] captcha is enabled, the purpose is to prevent bots including automation testing. \n"
							+ "Hence, this test execution may requires MANUAL RESOLVING CAPTCHA if captcha challenges displayed, \n"
							+ "or the test will be failed in 300 seconds!");
			// ref: https://www.lambdatest.com/blog/handle-captcha-in-selenium/
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(300));
			wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(GoogleReCaptcha.reCaptchaForm));
			wait.until(ExpectedConditions.elementToBeClickable(GoogleReCaptcha.reCaptchaCheckbox)).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(GoogleReCaptcha.reCaptchaCheckMark));
			System.out.println("[info] passed captcha checking!");
		}
	}
	
	public void clickIfClickable(By by, Config cfg) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(cfg.getTimeoutSeconds()));
		wait.until(ExpectedConditions.elementToBeClickable(GoogleReCaptcha.reCaptchaCheckMark)).click();
	}
}
