package ui.musala;

import org.openqa.selenium.By;

public abstract class MusalaWebApplication {
	public static final By contactUsButton = By.xpath("//span[@data-alt='Contact us']");
	public static final By contactUsNameInput = By.xpath("//input[@name='your-name']");
	public static final By contactUsEmailInput = By.xpath("//input[@name='your-email']");
	public static final By contactUsMobileInput = By.xpath("//input[@name='mobile-number']");
	public static final By contactUsSubjectInput = By.xpath("//input[@name='your-subject']");
	public static final By contactUsMessageTextBox = By.xpath("//textarea[@name='your-message']");
	public static final By contactUsSendButton = By.xpath("//input[@value='Send']");
	public static final By contactUsEmailInvalidSpan = By.xpath("//span[@class='wpcf7-not-valid-tip']");

	public static final String invalidEmailErrorMessage = "The e-mail address entered is invalid.";
}
