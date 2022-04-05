package ui;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Driver {
	private WebDriver driver;
	private WebDriverManager driverManager;

	public Driver(AvailableDrivers driverName) {
		switch (driverName) {
		case CHROME:
			driverManager = WebDriverManager.chromedriver();
			driverManager.setup();
			driver = new ChromeDriver();
			break;
		case FIREFOX:
			driverManager = WebDriverManager.firefoxdriver();
			driverManager.setup();
			driver = new FirefoxDriver();
			break;
		default:
			System.out.println("unsupported driver");
			return;
		}
	}
	
	public void setupPage(Config cfg) {
		driver.get(cfg.getUrl());
	}
}
