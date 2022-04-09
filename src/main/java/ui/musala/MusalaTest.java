package ui.musala;

import static org.testng.Assert.*;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.By.ByClassName;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ByChained;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
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
	@BeforeMethod()
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

	@AfterMethod()
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
					ExpectedConditions.visibilityOfElementLocated((Musala.contactUsAlertLocator)));

			assertTrue(
					driver.isTextDisplayed(Musala.contactUsAlertLocator, Musala.contactUsInvalidEmailErrorValue),
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

		driver.waitUntil(ExpectedConditions.presenceOfElementLocated(Musala.companyFacebookProfilePhotoLocator), cfg.getTimeoutSeconds());
		assertTrue(driver.isElementDisplay(Musala.companyFacebookProfilePhotoLocator));
	}

	@Test(description = "Test Case 3")
	public void testApplyAPosition() throws IOException, CsvException {
		String filePath = MusalaTest.class.getClassLoader().getResource("tc3.csv").getPath();
		DataDriven data = new DataDriven().parse(filePath);

		List<Form> dataTests = data.getAll();
		assertFalse(dataTests.isEmpty(), "no data test!");

		dataTests.forEach((formData) -> {
			driver.setup(cfg);

			driver.scrollToClick(Musala.careersHomeTabTabLocator);

			driver.scrollToClick(Musala.careersCheckOpenPositionsLocator);
			assertEquals(driver.getCurrentURL(), Musala.careersJoinUsURLValue);

			driver.selectOption(Musala.careersLocationSelectorLocator, "Anywhere");

			driver.scrollToClick(Musala.careersAutomationQAEngineerLocator);

			driver.scrollToView(Musala.careersGeneralDescriptionLocator);
			assertTrue(driver.isElementDisplay(Musala.careersGeneralDescriptionLocator),
					"general description not found!");

			driver.scrollToView(Musala.careersRequirementsLocator);
			assertTrue(driver.isElementDisplay(Musala.careersRequirementsLocator), "requirements not found!");

			driver.scrollToView(Musala.careersResponsibilitiesLocator);
			assertTrue(driver.isElementDisplay(Musala.careersResponsibilitiesLocator), "responsibilities not found!");

			driver.scrollToView(Musala.careersWhatweofferLocator);
			assertTrue(driver.isElementDisplay(Musala.careersWhatweofferLocator), "'what we offer' not found!");

			driver.scrollToClick(Musala.careersApplyButtonLocator);

			driver.inputText(Musala.careersApplyNameLocator, formData.getField("name").getValue());
			driver.inputText(Musala.careersApplyEmailLocator, formData.getField("email").getValue());
			driver.inputText(Musala.careersApplyMobileLocator, formData.getField("mobile").getValue());
			driver.inputText(Musala.careersApplyLinkedInLocator, formData.getField("linkedin").getValue());
			driver.inputText(Musala.careersApplyMessageLocator, formData.getField("message").getValue());
			driver.upload(Musala.careersApplyCVLocator, "path/my_cv.pdf");
			driver.click(Musala.careersApplyAggreementLocator);

			driver.submitWithReCaptcha(Musala.careersApplySendButtonLocator,
					ExpectedConditions.presenceOfElementLocated(Musala.careerApplyErrorPopupLocator));
			driver.waitUntil(ExpectedConditions.presenceOfElementLocated(Musala.careerApplyErrorPopupCloseLocator),
					cfg.getTimeoutSeconds());
			
			assertTrue(driver.isTextDisplayed(Musala.careerApplyErrorAlertLocator,
					formData.getField("expected_error").getValue()));
		});
	}

	@Test(description = "Test Case 4")
	public void testListingOpenPositions() {
		driver.scrollToClick(Musala.careersHomeTabTabLocator);
		
		driver.scrollToClick(Musala.careersCheckOpenPositionsLocator);
		
		String[] cities = {"Sofia", "Skopje"};
		
		for (int i = 0; i < cities.length; i++) {
			driver.selectOption(Musala.careersLocationSelectorLocator, cities[i]);
			
			List<String> titles = driver.getAtributesByClassName(Musala.careersJobTitleClassName, "data-alt");
			List<String> links = driver.getAtributesByClassName(Musala.careersJobLinkClassName, "href");
			
			assertFalse(titles.isEmpty(), "empty title list!");
			assertEquals(titles.size(), links.size(), "size of links and titles are not equal!");
			
			System.out.println("---------" + cities[i] + "---------");
			for (int j = 0; j < titles.size(); j++) {
				System.out.println("Position: " + titles.get(j));
				System.out.println("More info: " + links.get(j));
				System.out.println("---");
			}
		}		
	}
}
