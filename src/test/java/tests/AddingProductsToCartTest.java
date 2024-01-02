package tests;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.google.common.base.Objects;

import io.github.bonigarcia.wdm.WebDriverManager;
import pages.CartPage;
import pages.LoginPage;
import pages.ProductsPage;

public class AddingProductsToCartTest {
	
	WebDriver driver;
	ProductsPage products;
	CartPage cartPage;
	List<String> itemsShouldBeAddedToCart = new ArrayList<String>(); 
	
	@BeforeTest
	public void setup() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();	
		driver.get("https://www.saucedemo.com/");
		driver.manage().window().maximize();				
	}	
	
	@Test
	public void login_test() {
		LoginPage loginPage = new LoginPage(driver);						
		loginPage.login("standard_user", "secret_sauce");
		products = new ProductsPage(driver);
		Assert.assertTrue(driver.getCurrentUrl().contains("inventory"));
	}
	
		
	@DataProvider(name="dataForTests")
	public Object[][] provideDataForTests(){
		Object[][]dataArray =  {{"Sauce Labs Backpack"},{"Sauce Labs Fleece Jacket"},{"Sauce Labs Bolt T-Shirt"}};
		return dataArray;
	}
		
	
	@Test(dataProvider="dataForTests", priority=1)
	public void add_product_to_cart_test(String itemName) {			
		products.addToCartAnItemByName(itemName);
		int numberOfProductsInCart = products.getShoppingCartItemsCount();
		System.out.println("Number of products added to cart: " + numberOfProductsInCart);
		Assert.assertTrue(numberOfProductsInCart>0);		
	}	
	
		
	@Test(dependsOnMethods= {"add_product_to_cart_test"})
	public void open_cart_test() {	
		//products.printListOfAddedProducts();
		itemsShouldBeAddedToCart = products.getListOfItemsAddedToCart(); 	
		products.openShoppingCart();			
		cartPage = new CartPage(driver);
		String cartPageTitle = cartPage.getPageHeaderText();
		Assert.assertEquals("Your Cart", cartPageTitle);
	}
	
	
	@Test(priority=2)
	public void make_sure_needed_items_are_added_to_cart() {		
		List<String> itemsFromCart = cartPage.getCartItemsNames();			
		System.out.println("Should be added: " + itemsShouldBeAddedToCart.size() + "; Added: "+ itemsFromCart.size());
		Assert.assertTrue(itemsShouldBeAddedToCart.containsAll(itemsFromCart));		
	}
	
	
	@BeforeMethod
	public void makePause() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@AfterTest
	public void tearDown() {
		driver.close();
	}
}