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
import pages.LoginPage;
import pages.ProductsPage;

public class ProductsPageSortingTest {
	
	WebDriver driver;
	ProductsPage products;
	
	//ProductsPage products;

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
	public void sortNameDesc_test() {		
		products = new ProductsPage(driver);
		products.sortByNameDesc();
		WebElement firstElement = products.getFirstElement();
		WebElement lastElement = products.getLastElement();
		String firstElementName = products.getItemName(firstElement);
		String lastElementName = products.getItemName(lastElement);		
		Assert.assertTrue(firstElementName.compareTo(lastElementName) > 0);
	}
	
	@Test(priority=2)
	public void sortNameAsc_test() {		
		products = new ProductsPage(driver);
		products.sortByNameAsc();
		WebElement firstElement = products.getFirstElement();
		WebElement lastElement = products.getLastElement();
		String firstElementName = products.getItemName(firstElement);
		String lastElementName = products.getItemName(lastElement);
		Assert.assertTrue(firstElementName.compareTo(lastElementName) < 0);
	}
	
	@Test(priority=3)
	public void sortPriceAsc_test() {		
		products = new ProductsPage(driver);
		products.sortByPriceAsc();		
		WebElement firstElement = products.getFirstElement();
		WebElement lastElement = products.getLastElement();		
		Assert.assertTrue(products.getItemPrice(firstElement) < products.getItemPrice(lastElement)); 	
	}
	
	@Test(priority=4)
	public void sortPriceDesc_test() {		
		products = new ProductsPage(driver);
		products.sortByPriceDesc();		
		WebElement firstElement = products.getFirstElement();
		WebElement lastElement = products.getLastElement();		
		Assert.assertTrue(products.getItemPrice(firstElement) > products.getItemPrice(lastElement)); 
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