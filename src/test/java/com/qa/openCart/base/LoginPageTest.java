package com.qa.openCart.base;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.openCart.constants.AppConst;

import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Epic("EPIC 01 : Login Page Requirements")
@Story("Story 01 : User Login successfull")
public class LoginPageTest extends baseTest{

	@Severity(SeverityLevel.BLOCKER)
	@Test
	public void loginPageRightSideMenuLinksTest() {
	 int menuLinks = loginpage.getRightSideMenuLinks().size();
	 Assert.assertEquals(menuLinks, AppConst.LOGIN_PAGE_RMENU_LINKS_COUNT);
	}
	
} 
