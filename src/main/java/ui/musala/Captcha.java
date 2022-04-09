package ui.musala;

import org.openqa.selenium.By;

public abstract class Captcha {
	public static final By reCaptchaForm = By.xpath("//iframe[starts-with(@name, 'a-') and starts-with(@src, 'https://www.google.com/recaptcha')]");
	public static final By reCaptchaCheckbox = By.xpath("//div[@class='recaptcha-checkbox-border']");
	public static final By reCaptchaCheckMark = By.xpath("//div[@class='recaptcha-checkbox-checkmark']");
}
