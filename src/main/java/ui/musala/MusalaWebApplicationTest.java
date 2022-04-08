package ui.musala;

import static org.testng.Assert.*;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import data.DataDriven;
import data.Form;
import ui.AvailableBrowsers;
import ui.Config;
import ui.Driver;

public class MusalaWebApplicationTest {
	Config cfg;
	Driver driver;

	@Parameters("browser")
	@BeforeTest()
	public void setUp(String browser) {
		cfg = new Config();
		cfg.loadConfig();
		if (!cfg.isSupportedBrowser(browser)) {
			System.out.printf("[warn] skip testing on browser %s \n", browser);
			throw new SkipException("");
		} else {
			System.out.printf("[info] start testing on %s ... \n", browser);
		}

		driver = new Driver(AvailableBrowsers.valueOf(browser));
		driver.setup(cfg);
	}

	@AfterTest()
	public void tearDown() {
		driver.quit();
		System.out.println("[info] tear down successfully!");
	}

	@Test(description = "Test Case 1")
	public void testContactUs() {
		String[] titles = {"name", "phone", "subject", "message"};
		String[] regexes = {"[a-z]{10}","[0-9]{10}", "[a-z]{10}", "[a-z]{200}"};
		DataDriven data = new DataDriven().withGenRegex(titles, regexes);
		
		testContactInvalidEmail(data.randomData(), "test@test", driver);
		testContactInvalidEmail(data.randomData(), "@.", driver);
		testContactInvalidEmail(data.randomData(), "a@a. b", driver);
		testContactInvalidEmail(data.randomData(), "/@/./", driver);
		testContactInvalidEmail(data.randomData(), "...@..", driver);
	}
		
	void testContactInvalidEmail(Form form, String email, Driver drive) {
		driver.setup(cfg);

		driver.scrollTo(MusalaWebApplication.contactUsButtonLocator);
		driver.click(MusalaWebApplication.contactUsButtonLocator);

		driver.inputText(MusalaWebApplication.contactUsNameLocator, form.getField("name").getValue());
		driver.inputText(MusalaWebApplication.contactUsMobileLocator, form.getField("phone").getValue());
		driver.inputText(MusalaWebApplication.contactUsSubjectLocator, form.getField("subject").getValue());
		driver.inputText(MusalaWebApplication.contactUsMessageLocator, form.getField("message").getValue());
		driver.inputText(MusalaWebApplication.contactUsEmailLocator, email);
		driver.submitWithGoogleReCaptcha(MusalaWebApplication.contactUsSendButtonLocator,
				ExpectedConditions.visibilityOfElementLocated((MusalaWebApplication.contactUsAlertEmailLocator)));

		assertTrue(driver.isTextDisplayed(MusalaWebApplication.contactUsAlertEmailLocator,
				MusalaWebApplication.contactUsInvalidEmailErrorValue), "error message not found!");
	}

}
