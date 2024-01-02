package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import io.github.bonigarcia.wdm.WebDriverManager;
import pages.LoginPage;

public class LoginPageTest {
	WebDriver driver;
	LoginPage loginPage;
	
	@BeforeTest
	public void setup() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		
	}
	@BeforeMethod
	public void setUpForEachTest() {
		loginPage = new LoginPage(driver);
		driver.manage().window().maximize();
		driver.get("https://www.saucedemo.com/");
	}
	
	@DataProvider(name="valid_creds_provider")
	public String[][] provideCreds() {
		return new String[][] {{"standard_user","secret_sauce"}};
	}
	
	@DataProvider(name="invalid_creds_provider")
	public String[][] provideWrongCreds() {
		return new String[][] {{"standard_user","secret_mess"}};
	}
	
	
	@Test(dataProvider="valid_creds_provider", groups="positive", enabled=true)
	public void login_with_valid_creds(String username, String password) {		
		loginPage.login(username, password);		
		Assert.assertTrue(driver.getCurrentUrl().contains("inventory")); 		
	}
	
	
	@Test(dataProvider="invalid_creds_provider", groups="negative", enabled=true)
	public void login_with_Wrong_creds(String username, String password) {				
		loginPage.enterUsername(username);
		loginPage.enterPassword(password);
		loginPage.clickLoginButton();		
		String errMess = loginPage.getErrorTextForWrongCreds();
		Assert.assertTrue(errMess.contains("sadface"));
	}	
	
	
	@AfterTest
	public void tearDown() {
		driver.close();
	}
}