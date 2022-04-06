package ui.musala;

import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

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
		}else {
			System.out.printf("[info] start testing on %s ... \n", browser);
		}

		driver = new Driver(AvailableBrowsers.valueOf(browser));
		driver.setupPage(cfg);
	}
	
	@AfterTest()
	public void tearDown() {
		if (driver != null) {
			driver.getDriver().quit();
			System.out.println("[info] tear down successfully!");
		}
	}
	
	@Test(description="Test Case 1")
	public void testContactUs() {
		driver.scrollTo(MusalaWebApplication.contactUsButton);
		driver.click(MusalaWebApplication.contactUsButton);
		driver.inputText(MusalaWebApplication.contactUsNameInput, "ss");
		driver.inputText(MusalaWebApplication.contactUsMobileInput, "ss");
		driver.inputText(MusalaWebApplication.contactUsSubjectInput, "ss");
		driver.inputText(MusalaWebApplication.contactUsMessageTextBox, "ss");
		driver.byPassGoogleReCaptcha(cfg);
		driver.clickIfClickable(MusalaWebApplication.contactUsSendButton, cfg);
	}
	
}
