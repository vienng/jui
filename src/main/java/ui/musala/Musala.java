package ui.musala;

import org.openqa.selenium.By;

import data.DataDriven;

public abstract class Musala {
	public static final By contactUsButtonLocator = By.xpath("//span[@data-alt='Contact us']");
	public static final By contactUsNameLocator = By.xpath("//input[@name='your-name']");
	public static final By contactUsEmailLocator = By.xpath("//input[@name='your-email']");
	public static final By contactUsMobileLocator = By.xpath("//input[@name='mobile-number']");
	public static final By contactUsSubjectLocator = By.xpath("//input[@name='your-subject']");
	public static final By contactUsMessageLocator = By.xpath("//textarea[@name='your-message']");
	public static final By contactUsSendButtonLocator = By.xpath("//input[@value='Send']");
	public static final By contactUsAlertLocator = By.xpath("//span[@class='wpcf7-not-valid-tip']");
	public static final String contactUsInvalidEmailErrorValue = "The e-mail address entered is invalid.";

	public static final By companyHomeTabLocator = By.xpath("//a[@class='main-link' and text()='Company']");
	public static final By companyLeadershipSessionLocator = By.xpath("//h2[text()='Leadership']");
	public static final By companyFacebookLinkIconLocator = By.xpath("//span[@class='musala musala-icon-facebook']");
	public static final By companyFacebookProfilePhotoLocator = By.xpath("//a[@href='https://www.facebook.com/MusalaSoft/photos/a.152166551470703/3926723730681614/?__tn__=%3C']");
	public static final String companyURLValue = "https://www.musala.com/company/";
	public static final String companyFacebookURLValue = "https://www.facebook.com/MusalaSoft?fref=ts";
	
	public static final By careersHomeTabTabLocator = By.xpath("//a[@href='https://www.musala.com/careers/']");
	public static final By careersCheckOpenPositionsLocator = By.xpath("//span[@data-alt='Check our open positions']");
	public static final By careersLocationSelectorLocator =  By.xpath("//select[@id='get_location']");
	public static final By careersLocationAnywhereLocator = By.xpath("//select[@id='get_location']/option[@value='Anywhere']");
	public static final By careersLocationSofiaLocator = By.xpath("//select[@id='get_location']/option[@value='Sofia'");
	public static final By careersAutomationQAEngineerLocator = By.xpath("//h2[@data-alt='Automation QA Engineer']");
	public static final By careersGeneralDescriptionLocator = By.xpath("//h2[text()='General description']");
	public static final By careersRequirementsLocator = By.xpath("//h2[text()='Requirements']");
	public static final By careersResponsibilitiesLocator = By.xpath("//h2[text()='Responsibilities']");
	public static final By careersWhatweofferLocator = By.xpath("//h2[text()='What we offer']");
	public static final By careersApplyButtonLocator = By.xpath("//input[@value='Apply']");
	public static final By careersApplyNameLocator = By.xpath("//input[@name='your-name']");
	public static final By careersApplyEmailLocator = By.xpath("//input[@name='your-email']");
	public static final By careersApplyMobileLocator = By.xpath("//input[@name='mobile-number']");
	public static final By careersApplyCVLocator = By.xpath("//input[@name='uploadtextfield']");
	public static final By careersApplyLinkedInLocator = By.xpath("//input[@name='linkedin']");
	public static final By careersApplyMessageLocator = By.xpath("//textarea[@name='your-message']");
	public static final By careersApplyAggreementLocator = By.xpath("//input[@name='adConsentChx']");
	public static final By careersApplySendButtonLocator = By.xpath("//input[@value='Send']");
	public static final By careersApplyCloseLocator = By.xpath("//a[@id='fancybox-close']");
	public static final By careersFilteredJobsLocator = By.xpath("//div[@class='inner-wraper']/article");
	public static final String careersJoinUsURLValue = "https://www.musala.com/careers/join-us/";
	public static final By careerApplyErrorPopupLocator = By.xpath("//span[@class='wpcf7-not-valid-tip']");
	public static final By careerApplyErrorPopupCloseLocator = By.xpath("//button[@class='close-form']");
	public static final By careerApplyErrorAlertLocator = By.xpath("//span[@class='wpcf7-not-valid-tip']");
	public static final String careersJobLinkClassName = "card-jobsHot__link";
	public static final String careersJobTitleClassName = "card-jobsHot__title";
}
