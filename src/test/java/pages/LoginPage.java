package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {
	WebDriver driver;
	// WebElements of LOGIN page
	By usernameField = By.id("user-name");
	By passwordField = By.id("password");
	By loginButton = By.id("login-button");	
	By errorText = By.cssSelector("h3[data-test='error']");	
	
	public LoginPage(WebDriver driver) {
		this.driver = driver;
	}
	
	// Actions on WebElements of LOGIN page
	public void enterUsername(String username) {
		driver.findElement(usernameField).sendKeys(username);
	}
	
	public void enterPassword(String password) {
		driver.findElement(passwordField).sendKeys(password);
	}
	
	public void clickLoginButton() {
		driver.findElement(loginButton).click();
	}
	
	public void login(String login, String password) {
		enterUsername(login);
		enterPassword(password);
		clickLoginButton();
	}
	
	public String getErrorTextForWrongCreds() {
		return driver.findElement(errorText).getText();
	}
}