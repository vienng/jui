package ui.musala;

import static org.testng.Assert.*;

import java.util.ArrayList;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.support.ui.ExpectedConditions;
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

	class contactUsFormData {
		String name;
		String mobile;
		String subject;
		String email;
		String message;

		public contactUsFormData(String email) {
			name = RandomStringUtils.randomAlphabetic(10);
			mobile = RandomStringUtils.randomNumeric(10);
			subject = RandomStringUtils.randomAlphabetic(10);
			message = RandomStringUtils.randomAlphanumeric(100);
			this.email = email;
		}

		public void test(Driver driver) {
			driver.setup(cfg);

			driver.scrollTo(MusalaWebApplication.contactUsButton);
			driver.click(MusalaWebApplication.contactUsButton);

			driver.inputText(MusalaWebApplication.contactUsNameInput, name);
			driver.inputText(MusalaWebApplication.contactUsMobileInput, mobile);
			driver.inputText(MusalaWebApplication.contactUsSubjectInput, subject);
			driver.inputText(MusalaWebApplication.contactUsMessageTextBox, message);
			driver.inputText(MusalaWebApplication.contactUsEmailInput, email);
			driver.submitWithGoogleReCaptcha(MusalaWebApplication.contactUsSendButton,
					ExpectedConditions.visibilityOfElementLocated((MusalaWebApplication.contactUsEmailInvalidSpan)));

			assertTrue(driver.isTextDisplayed(MusalaWebApplication.contactUsEmailInvalidSpan,
					MusalaWebApplication.invalidEmailErrorMessage), "error message not found!");
		}
	}

	@Test(description = "Test Case 1")
	public void testContactUs() {
		contactUsFormData testData1 = new contactUsFormData("test@test");
		contactUsFormData testData2 = new contactUsFormData("@.");
		contactUsFormData testData3 = new contactUsFormData("a@a. b");
		contactUsFormData testData4 = new contactUsFormData("/@/./");
		contactUsFormData testData5 = new contactUsFormData("...@..");

		// sequentially
		testData1.test(driver);
		testData2.test(driver);
		testData3.test(driver);
		testData4.test(driver);
		testData5.test(driver);
	}
}
