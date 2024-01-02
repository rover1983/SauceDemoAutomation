package pages;

import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ProductsPage {
	// Sorting options
	@FindBy(css="option[value='az']")WebElement nameAscendingOption;	
	@FindBy(css="option[value='za']")WebElement nameDescendingOption;
	@FindBy(css="option[value='lohi']")WebElement priceAscendingOption;	
	@FindBy(css="option[value='hilo']")WebElement priceDescendingOption;
	
	@FindBy(css="a[class='shopping_cart_link']")WebElement shoppingCartIcon;	
	@FindBy(css="span[class='shopping_cart_badge']")WebElement shoppingCartIconAddedProductsNumber;	
	@FindBy(css="div[class='inventory_list']")WebElement productsListAsWebElements;		
	@FindBy(xpath="//div[@class='inventory_item']//button")WebElement firstProductItem;
	
	List<WebElement>listOfAllSortedProducts=new ArrayList<WebElement>();
	List<WebElement>listOfAllProducts=new ArrayList<WebElement>();
	List<String>listOfMatchedProductsAddedToCart = new ArrayList<String>();
	
	WebDriver driver;	
	boolean itemMatchFound;
	
	// Constructor
	public ProductsPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}		 
	
	public void sortByNameAsc() {
		nameAscendingOption.click();
	}	
	public void sortByNameDesc() {
		nameDescendingOption.click();
	}	
	public void sortByPriceAsc() {
		priceAscendingOption.click();
	}	
	public void sortByPriceDesc() {
		priceDescendingOption.click();
	}
	
	public WebElement getFirstElement() {
		listOfAllSortedProducts = productsListAsWebElements.findElements(By.className("inventory_item"));		
		return listOfAllSortedProducts.get(0);
	}
	
	public WebElement getLastElement() {
		listOfAllSortedProducts = productsListAsWebElements.findElements(By.className("inventory_item"));		
		return listOfAllSortedProducts.get(listOfAllSortedProducts.size()-1);
	}	
		
	public String getItemName(WebElement element){
		return element.findElement(By.cssSelector("div[class='inventory_item_name ']")).getText();
	}
	
	public double getItemPrice(WebElement element) {
		String priceText = element.findElement(By.cssSelector("div[class='inventory_item_price']")).getText();
		String priceNum = priceText.substring(1, priceText.length()-1);
		return Double.parseDouble(priceNum);		 
	}
	
	public void openShoppingCart() {
		shoppingCartIcon.click();
	}
	
	// get the number of products added to cart which is displayed on the cart icon
	public int getShoppingCartItemsCount() {
		String stringPrice = shoppingCartIconAddedProductsNumber.getText();
		return Integer.parseInt(stringPrice);
	}
	
	public void addFirstProductToCart() {
		firstProductItem.click();
	}
	
	public boolean removeAddedProductFromCart() {
		if(firstProductItem.getText().equals("Remove")) {
			firstProductItem.click();
			return true;
		}
		return false;
	} 
	
	public List<String> getListOfItemsAddedToCart(){
		return listOfMatchedProductsAddedToCart;
	}
	
	public void printListOfAddedProducts() {
		for(String str:listOfMatchedProductsAddedToCart) {
			System.out.println(" * "+str);
		}
	}
		
	public boolean addToCartAnItemByName(String itemName) {
		listOfAllProducts = productsListAsWebElements.findElements(By.className("inventory_item"));
		for(WebElement item: listOfAllProducts) {
			// Comparing string level names of webElements
			if(itemName.equals(getItemName(item))) { 	
				
				listOfMatchedProductsAddedToCart.add(itemName);
				itemMatchFound = true; 							
				item.findElement(By.tagName("button")).click(); 
			}			
		}			
		return itemMatchFound;
	}		
}