package com.qa.openCart.base;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Epic("EPIC 03 : Registration Page Requirements")
@Story("Story 01 : User Registration successfull")
public class RegisterPageTest extends baseTest {
	
	@BeforeClass
	public void setUpRegisterPage() {
		registerpage = loginpage.navigateToRegisterPage();
	}
	
	@DataProvider
	public Object[][] getUserRegistrationData() {
		Object regData[][] = excelUtil.getTestDataFromSheet("register");
		return regData;
	}
	
	@Severity(SeverityLevel.NORMAL)
	@Test(dataProvider = "getUserRegistrationData")
	public void submitUserRegistration(String firstName, String lastName, String emailID, String phoneNum,
			String password, String subscribe) {
		registerpage.enterFirstName(firstName);
		registerpage.enterLastName(lastName);
		registerpage.enterEmailID(emailID);
		registerpage.enterPhoneNum(phoneNum);
		registerpage.enterPasswordFirst(password);
		registerpage.confirmPassword(password);
		registerpage.selectSubscrice(subscribe);
		registerpage.selectAgreeCheckBox();
		String successMsg = registerpage.submitRegisterButton();
		Assert.assertEquals(successMsg, "Your Account Has Been Created!");
		registerpage.logoutUser();
		registerpage.navigateToRegisterPage();
	}
	

}
