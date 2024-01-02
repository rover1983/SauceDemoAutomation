package pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CartPage {
	// Page header
	@FindBy(xpath="//div[@class='header_secondary_container']/span")WebElement cartPageHeader;	
	// List of products elements
	@FindBy(className="cart_list")WebElement cartItems;
	
	@FindBy(id="continue-shopping")WebElement continueShoppingButton;
	
	@FindBy(xpath="//div[@class='item_pricebar']//button")WebElement removeItemFromCart;
	
	@FindBy(css="span[class='shopping_cart_badge']")WebElement cartIconProductCount;
	
	WebDriver driver;
	List<WebElement>cartProductsElements = new ArrayList<WebElement>();	
	List<String>cartProductsNames = new ArrayList<String>();
	
	// Constructor
	public CartPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}		
	
	private void setCartItemsElements() {
		cartProductsElements = cartItems.findElements(By.className("cart_item"));
	}
	
	public String getPageHeaderText() {
		return cartPageHeader.getText();
	}
	
	public void clickContinueShopping() {
		continueShoppingButton.click();
	}
	
	public void clickRemoveProduct() {
		removeItemFromCart.click();
	}
	
	public int getCartProductsCount() {
		return Integer.parseInt(cartIconProductCount.getText());
	}
	
	public boolean checkCartContainsProducts() {
		return cartIconProductCount != null;
	}
	
	public List<String> getCartItemsNames(){
		setCartItemsElements();
		System.out.println("cartProductsElements list size: "+cartProductsElements.size());
		for(WebElement cartItemElement:cartProductsElements) {
			
			System.out.println("Show it if added: "+cartProductsNames.add(cartItemElement.findElement(By.className("inventory_item_name")).getText()));
		}
		return cartProductsNames;
	}
}