package tests;

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

public class CartPageTest {
	
	WebDriver driver;
	ProductsPage products;
	CartPage cartPage;
	

	@BeforeTest
	public void setup() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();	
		driver.get("https://www.saucedemo.com/");
		driver.manage().window().maximize();
	}	
	
	@Test
	public void login_with_valid_creds() {		
		LoginPage loginPage = new LoginPage(driver);						
		loginPage.login("standard_user", "secret_sauce");			
		Assert.assertTrue(driver.getCurrentUrl().contains("inventory")); 	
	}
	
	@Test(priority=1)
	public void add_item_to_cart_test() {
		products = new ProductsPage(driver);
		products.addFirstProductToCart();
		Assert.assertEquals(1, products.getShoppingCartItemsCount());
		
	}
	
	@Test(priority=2)
	public void cart_test() {	
		products.openShoppingCart();
		cartPage = new CartPage(driver);
		String header = cartPage.getPageHeaderText();
		Assert.assertTrue(header.contains("Your Cart"));
	}
	
	@Test(priority=3)
	public void delete_product_test() {
		int numberOfProductsBeforeRemoving = cartPage.getCartProductsCount();
		System.out.println("numberOfProductsBeforeRemoving: "+numberOfProductsBeforeRemoving);
		cartPage.clickRemoveProduct();
		Assert.assertTrue(!cartPage.checkCartContainsProducts() || 1==numberOfProductsBeforeRemoving);
	}
	
	@Test(priority=4)
	public void go_from_cart_to_products_page() {
		cartPage.clickContinueShopping();
		String header = driver.getCurrentUrl();
		Assert.assertTrue(header.contains("inventory"));
	}
	
	
	@AfterMethod
	public void makePause() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@AfterTest
	public void tearDown() {
		driver.close();
	}
}