package com.qa.openCart.pageClass;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.openCart.constants.AppConst;
import com.qa.openCart.utilities.ElementUtil;

import io.qameta.allure.Step;

public class LoginPage {
	
	private WebDriver driver;
	private ElementUtil eleUtil;
	
	protected AccountPage accountpage;

	//LoginPage constructor created
	public LoginPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}

	//Private By locators created
	private By email = By.id("input-email");
	private By password = By.id("input-password");
	private By loginButton = By.xpath("//input[@value='Login']");
	private By rightSideMenuLinks = By.xpath("//div[@class='list-group']//a");
	private By registerLink = By.xpath("//aside[@id='column-right']//a[text()='Register']");
	
	@Step("User Login Successfull")
	public AccountPage userLoginPage() {
		eleUtil.webDriverWaitFindElementOnPage(email, AppConst.DEFAULT_TIME_OUT).sendKeys("manickamraj@gmail.com");
		eleUtil.doSendKeys(password, "Monika");
		eleUtil.clickButton(loginButton);
		accountpage = new AccountPage(driver);
		return accountpage;
	}
	
	@Step("getRightSide All Menu Links")
	public List<String> getRightSideMenuLinks() {
		return eleUtil.getTextfindElementsList(rightSideMenuLinks);
	}
	
	@Step("Navigating to register page")
	public RegisterPage navigateToRegisterPage() {
		eleUtil.clickLink(registerLink);
		return new RegisterPage(driver);
	}
}

