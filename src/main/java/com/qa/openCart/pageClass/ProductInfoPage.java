package com.qa.openCart.pageClass;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.openCart.utilities.ElementUtil;

import io.qameta.allure.Step;

public class ProductInfoPage {

	private WebDriver driver;
	private ElementUtil eleUtil;
	
	public ProductInfoPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}
	
	private Map<String,String> productInfoMap ;
	
	private By productInfoMenuLink = By.xpath("(//ul[@class='breadcrumb']//a)[3]");
	private By productInfoHeader = By.xpath("//*[@id='content']//h1");
	private By productInfoDetails = By.xpath("(//*[@id='content']//h1/parent::div/ul)[position()=1]/li");
	private By productCostDetails = By.xpath("(//*[@id='content']//h1/parent::div/ul)[position()=2]/li");
	private By qtyTextBox = By.xpath("//input[@name='quantity']");
	private By addProductToCart = By.xpath("//button[text()='Add to Cart']");
	//private By addToCartSuccessMsg = By.xpath("(//ul[@class='breadcrumb']/parent::div/div)[1]");
	private By addToCartSuccessMsg = By.cssSelector("div.alert.alert-success");
	private By cartItems = By.xpath("//span[@id='cart-total']");
	private By removeCartItems = By.xpath("//td[@class='text-center']//button[@type='button']");
	
	@Step("getProductInfoMenuLink")
	public String getProductInfoMenuLink() throws InterruptedException {
		Thread.sleep(3000);
		return eleUtil.getTextFindElement(productInfoMenuLink);
	}
	
	@Step("getProductInfoHeader")
	public String getProductInfoHeader() {
		return eleUtil.webDriverWaitFindElementOnPage(productInfoHeader, 2).getText();
	}
	
	@Step("getProductInfoHeader")
	public Map<String, String> getProductDetails() {
		productInfoMap = new LinkedHashMap<String,String>();
		//header
		productInfoMap.put("productName", getProductInfoHeader());
		getProductInfoDetails();
		getProductPriceDetails();
		return productInfoMap;
	}
	
	@Step("getProductPriceDetails")
	private  void getProductPriceDetails() {
		List<WebElement> elements = eleUtil.webDriverWaitFindElementsOnPage(productCostDetails, 2);
			String price = elements.get(0).getText();
			String xprice = elements.get(1).getText();
			String exTaxPrice = xprice.split(":")[1].trim();
		productInfoMap.put("productPrice", price);
		productInfoMap.put("productExTaxPrice", exTaxPrice);
	}
	
	@Step("getProductInfoDetails")
	private void getProductInfoDetails() {
		List<WebElement> elements = eleUtil.webDriverWaitFindElementsOnPage(productInfoDetails, 2);
		for(WebElement e : elements) {
			String metaInfo = e.getText();
			String metaDetails[] = metaInfo.split(":");
			String key = metaDetails[0].trim();
			String value = metaDetails[1].trim();
			productInfoMap.put(key, value);
			
		}
	}
	
	@Step("addProductToCart")
	public String addProductToCart(int qty) {
		eleUtil.clearTextField(qtyTextBox);
		eleUtil.doSendKeys(qtyTextBox, String.valueOf(qty));
		eleUtil.clickButton(addProductToCart);
		String SuccessMsg = eleUtil.webDriverWaitFindElementOnPage(addToCartSuccessMsg, 5).getText();
		return (SuccessMsg.substring(0, SuccessMsg.length()-1).replace("\n", ""));
	}
	
	@Step("get current CartItemsDetails")
	public String getCartItemsDetails() {
		eleUtil.webDriverWaitFindElementOnPage(cartItems, 6);
		eleUtil.doActionMoveToElement(cartItems);
		return eleUtil.getTextFindElement(cartItems);
	}
	
	@Step("remove added item from Cart")
	public void removeSingleAddedProductFromCart() {
		eleUtil.doActionClick(cartItems);
		eleUtil.clickButton(removeCartItems);
	}
	
}
