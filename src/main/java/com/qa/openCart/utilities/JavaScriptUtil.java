package com.qa.openCart.utilities;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class JavaScriptUtil {
	
	private WebDriver driver; 
	
	public JavaScriptUtil(WebDriver driver) {
		this.driver = driver;
	}
	
	public String getTitleByJS() {
		JavascriptExecutor js = (JavascriptExecutor)driver;
		return js.executeScript("return document.title").toString();
	}
	
	public void goBackByJS() {
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("history.go(-1)");
	}
	
	public void goForwardByJS() {
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("history.go(1)");
	}
 
	public void refreshBrowserByJS() {
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("history.go(0)");
		
	}
		
	public void generateAlertByJS(String message) {
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("alert ('" + message + "')");
	}
	
	public void confirmAlertByJS(String message) {
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("confirm ('" + message + "')");
		//js.executeScript("window.confirm = function() { return true;}");
	}
	
	public String getPageInnerTextByJS() {
		JavascriptExecutor js = (JavascriptExecutor)driver;
		return js.executeScript("return document.documentElement.innerText;").toString();
	}
	
	public void drawBorderOnWebElementByJS(By locator) {
		JavascriptExecutor js = (JavascriptExecutor)driver;
		WebElement element = driver.findElement(locator);
		js.executeScript("arguments[0].style.border='3px solid red'",element);
	}
	
	public void sendKeysUsingIDbyJS(String Id, String value) {
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("document.getElementById('" + Id + "').value='" + value + "'");
	}
	
	public void clickWebElementByJS(By locator) {
		JavascriptExecutor js = (JavascriptExecutor)driver;
		WebElement element = driver.findElement(locator);
		js.executeScript("arguments[0].click();",element);
	}
	
	public void pageScrollIntoViewByJS(By locator) {
		JavascriptExecutor js = (JavascriptExecutor)driver;
		WebElement element = driver.findElement(locator);
		js.executeScript("arguments[0].scrollIntoView(true);",element);
	}
	
	public void scrollDownByJS() {
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("window.scrollTo(0,document.body.scrollHeight)");
	}
	
	public void scrollUpByJS() {
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("window.scrollTo(document.body.scrollHeight,0)");
	}
	
	public void scrollToMiddlePageByJS() {
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("window.scrollTo(0,document.body.scrollHeight/2)");
	}
	
	public void changeColorByJS(String color,WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("arguments[0].style.backgroundColor='" + color + "'",element);
		try {
			Thread.sleep(20);
		}catch (InterruptedException e) {
			
		}
	}
	
	public void flashWebElementByJS(By locator) {
		WebElement element = driver.findElement(locator);
		String bgColor = element.getCssValue("backgroundColor");
		for (int i=0;i<=10;i++) {
			changeColorByJS("rgb(0,200,0)",element);
			changeColorByJS(bgColor ,element);
		}
	}
	
}
