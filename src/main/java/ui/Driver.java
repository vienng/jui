package ui;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By.ByClassName;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.pagefactory.ByChained;
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
			driver = new FirefoxDriver(new FirefoxOptions().configureFromEnv());
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

	public void scrollToClick(By by) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		WebElement elem = driver.findElement(by);
		js.executeScript("arguments[0].scrollIntoView();", elem);
		js.executeScript("arguments[0].click();", elem);
	}
	
	public void scrollToView(By by) {
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

	public void submitWithReCaptcha(By submitLocator, ExpectedCondition<WebElement> reachedExpectation) {
		if (config.isCaptchaDisabled()) {
			System.out.println("[warn] captcha is disabled, applied for internal testing/staging environment only!");
			
			// assume button is clickable on testing environment
			driver.findElement(submitLocator).click();

			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(config.getTimeoutSeconds()));
			wait.until(reachedExpectation);
		} else {
			System.out.println("[warn] captcha is enabled, requires MANUAL RESOLVING CAPTCHA AND SUBMIT \n"
							+ "or the test will be failed in 300 seconds!");

			// manually resolving captcha challenges and continue
			
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(300));
			wait.until(reachedExpectation);
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

	public boolean isElementDisplay(By by) {
		return driver.findElement(by).isDisplayed();
	}
	
	public void waitUntil(ExpectedCondition<WebElement> condition, int timeoutSeconds) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
		wait.until(condition);
	}
	
	public void switchTab(int toTabIdx) {
		ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
	    driver.switchTo().window(tabs.get(toTabIdx));
	}
	
	public void upload(By by, String filePath) {
	}
	
	public void selectOption(By by, String text) {
		Select opt = new Select(driver.findElement(by));
		opt.selectByVisibleText(text);
	}
	
	public List<String> getAtributesByClassName(String className, String atributeName) {
	    By.ByClassName byClassNames = new ByClassName(className);
	    List<WebElement> list = driver.findElements(new ByChained(byClassNames));
	    List<String> values = new ArrayList<String>(list.size());
	    
	    list.forEach((element) -> {
	    	values.add(element.getAttribute(atributeName));
	    });
	    
	    return values;
	}
}
