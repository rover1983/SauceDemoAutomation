package tests;

import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import io.github.bonigarcia.wdm.WebDriverManager;
import pages.CartPage;
import pages.LoginPage;
import pages.ProductsPage;

public class ProductsPageTest {
	WebDriver driver;	
	ProductsPage productsPage;
	CartPage cart;
	String elementName;
	
	@BeforeTest
	public void setup() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();	
		driver.get("https://www.saucedemo.com/");
		driver.manage().window().maximize();
	}	
		
	@Test(enabled=true)
	public void login_with_valid_creds() {		
		LoginPage loginPage = new LoginPage(driver);						
		loginPage.login("standard_user", "secret_sauce");			
		Assert.assertTrue(driver.getCurrentUrl().contains("inventory")); 	
	}
	
	@Test(priority=1)
	public void addProductToCart_test() {
		productsPage =  new ProductsPage(driver);
		WebElement selectedItem = productsPage.getFirstElement();
		elementName = productsPage.getItemName(selectedItem);
		productsPage.addFirstProductToCart();
		String buttonText = selectedItem.findElement(By.tagName("button")).getText();
		Assert.assertEquals("Remove", buttonText);
	}
	
	@Test(priority=2, enabled=true)
	public void open_cart_page() {
		productsPage.openShoppingCart();
		cart = new CartPage(driver);
		String headerText = cart.getPageHeaderText();
		Assert.assertEquals("Your Cart", headerText);
	}
	
	@Test(priority=3)
	public void check_itemName_from_cart_test() {
		List<String> cartItemNames = new ArrayList<String>();
		cartItemNames = cart.getCartItemsNames();
		Assert.assertEquals(elementName, cartItemNames.get(0));
	}
		
	@AfterMethod
	public void makePause() {
		try {Thread.sleep(2000);
		} catch (InterruptedException e) {e.printStackTrace();}
	}
	
	@AfterTest
	public void tearDown() {
		System.out.println("This class test finished");
		driver.close();
	}
	
}