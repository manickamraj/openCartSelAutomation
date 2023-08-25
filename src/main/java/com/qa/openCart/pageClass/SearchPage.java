package com.qa.openCart.pageClass;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.openCart.constants.AppConst;
import com.qa.openCart.utilities.ElementUtil;

import io.qameta.allure.Step;

public class SearchPage {
	
	private WebDriver driver;
	private ElementUtil eleUtil;
	
	By searchProductList = By.xpath("//div[@class='product-thumb']");
	
	public SearchPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}
	
	@Step("get searchProductResults")
	public List<String> searchProductResults() {
		return eleUtil.getTextfindElementsList(searchProductList);
	}
	
	@Step("Navigating to Product Info page")
	public ProductInfoPage selectSearchProduct(String productName) {
		By product = By.linkText(productName);
		eleUtil.webDriverWaitFindElementOnPage(product, AppConst.LONG_TIME_OUT).click();
		return new ProductInfoPage(driver);
	}

}
