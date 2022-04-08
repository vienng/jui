package ui.musala;

import static org.testng.Assert.*;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.opencsv.exceptions.CsvException;

import data.DataDriven;
import data.Form;
import ui.AvailableBrowsers;
import ui.Config;
import ui.Driver;

public class MusalaTest {
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
		String[] titles = { "name", "phone", "subject", "message" };
		String[] regexes = { "[a-z]{10}", "[0-9]{10}", "[a-z]{10}", "[a-z]{200}" };
		DataDriven data = new DataDriven().withGenRegex(titles, regexes);

		String[] invalidEmails = { "test@test", "@", "a@a. b", "/@/./", "...@.." };

		for (int i = 0; i < invalidEmails.length; i++) {
			driver.setup(cfg);
			Form formData = data.randomData();

			driver.scrollToClick(Musala.contactUsButtonLocator);
			driver.inputText(Musala.contactUsNameLocator, formData.getField("name").getValue());
			driver.inputText(Musala.contactUsMobileLocator, formData.getField("phone").getValue());
			driver.inputText(Musala.contactUsSubjectLocator, formData.getField("subject").getValue());
			driver.inputText(Musala.contactUsMessageLocator, formData.getField("message").getValue());
			driver.inputText(Musala.contactUsEmailLocator, invalidEmails[i]);
			driver.submitWithReCaptcha(Musala.contactUsSendButtonLocator,
					ExpectedConditions.visibilityOfElementLocated((Musala.contactUsAlertEmailLocator)));

			assertTrue(
					driver.isTextDisplayed(Musala.contactUsAlertEmailLocator, Musala.contactUsInvalidEmailErrorValue),
					"message not found!");
		}
	}

	@Test(description = "Test Case 2")
	public void testVistingProfile() {
		driver.scrollToClick(Musala.companyHomeTabLocator);
		assertEquals(driver.getCurrentURL(), Musala.companyURLValue);

		driver.scrollToView(Musala.companyLeadershipSessionLocator);
		assertTrue(driver.isElementDisplay(Musala.companyLeadershipSessionLocator), "leadership session not found!");

		driver.scrollToClick(Musala.companyFacebookLinkIconLocator);
		driver.switchTab(1);
		assertEquals(driver.getCurrentURL(), Musala.companyFacebookURLValue);

		driver.waitUntil(ExpectedConditions.visibilityOfElementLocated(Musala.companyFacebookProfilePhotoLocator),
				cfg.getTimeoutSeconds());
		assertTrue(driver.isElementDisplay(Musala.companyFacebookProfilePhotoLocator));
	}

	@Test(description = "Test Case 3")
	public void tc3() throws IOException, CsvException {
		String filePath = this.getClass().getResource("tc3.csv").getPath();
		DataDriven data = new DataDriven().parse(filePath);

		driver.scrollToClick(Musala.careersHomeTabTabLocator);

		driver.scrollToClick(Musala.careersCheckOpenPositionsLocator);
		assertEquals(driver.getCurrentURL(), Musala.careersJoinUsURLValue);

		driver.click(Musala.careersLocationSelectorLocator);
		driver.click(Musala.careersLocationAnywhereLocator);

		driver.scrollToClick(Musala.careersAutomationQAEngineerLocator);

		driver.scrollToView(Musala.careersGeneralDescriptionLocator);
		assertTrue(driver.isElementDisplay(Musala.careersGeneralDescriptionLocator), "general description not found!");

		driver.scrollToView(Musala.careersRequirementsLocator);
		assertTrue(driver.isElementDisplay(Musala.careersRequirementsLocator), "requirements not found!");

		driver.scrollToView(Musala.careersResponsibilitiesLocator);
		assertTrue(driver.isElementDisplay(Musala.careersResponsibilitiesLocator), "responsibilities not found!");

		driver.scrollToView(Musala.careersWhatweofferLocator);
		assertTrue(driver.isElementDisplay(Musala.careersWhatweofferLocator), "'what we offer' not found!");

		driver.scrollToClick(Musala.careersApplyButtonLocator);
	}
}
