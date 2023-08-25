package com.qa.openCart.base;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Epic("EPIC 02 : Account Page Requirements")
@Story("Story 02 : Account Page Verification")
public class AccountPageTest extends baseTest{

	@BeforeClass
	public void acctPageSetUp() {
		accountpage=loginpage.userLoginPage();
	}
	
	@Severity(SeverityLevel.NORMAL)
	@Test(priority=1)
	public void accountPageVerification() {
		String actPageMenu = accountpage.accountPageMenuLink();
		Assert.assertEquals(actPageMenu, "Account");
	}
	
	@DataProvider
	public Object[][] searchProductData() {
		return new Object[][] {
			{"Macbook"},
			{"Apple"},
			{"iMac"},
			{"Samsung"}
		};
		
	}
	
	@Severity(SeverityLevel.NORMAL)
	@Test(priority=2,dataProvider = "searchProductData")
	public void searchProductVerification(String searchKey) {
		searchpage = accountpage.searchProducts(searchKey);
		Assert.assertTrue(searchpage.searchProductResults().size()>0);
	}
	
	@DataProvider
	public Object[][] selectProductData() {
		return new Object[][] {
			{"Macbook","MacBook Pro"},
			{"Apple","Apple Cinema 30\""},
			{"iMac","iMac"},
			{"Samsung","Samsung Galaxy Tab 10.1"}
		};
	}
	
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority=3,dataProvider = "selectProductData")
	public void selectProductVerification(String searchKey, String productName) throws InterruptedException {
		searchpage = accountpage.searchProducts(searchKey);
		if(searchpage.searchProductResults().size()>0) {
			prodinfopage = searchpage.selectSearchProduct(productName);
			Assert.assertEquals(prodinfopage.getProductInfoMenuLink(), productName);
		}
	}
	
	@Severity(SeverityLevel.NORMAL)
	@Test (priority=4)
	public void selectProductDetailsVerification() {
		searchpage = accountpage.searchProducts("Macbook");
		prodinfopage = searchpage.selectSearchProduct("MacBook Pro");
		Map<String,String> map = prodinfopage.getProductDetails();
		System.out.println(map);
		softAssert.assertEquals(map.get("productName"), "MacBook Pro");
		softAssert.assertEquals(map.get("Brand"), "Apple");
		softAssert.assertEquals(map.get("Reward Points"), "800");
		softAssert.assertEquals(map.get("productPrice"), "$2,000.00");
		softAssert.assertAll();
	}
	
	
	@DataProvider
	public Object[][] addProductToCartData() {
		return new Object[][] {
			{"Macbook","MacBook Pro",5},
			{"iMac","iMac",101},
			{"Samsung","Samsung SyncMaster 941BW",7},
			{"Samsung","Samsung Galaxy Tab 10.1",14}
		};
	}
	
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority=5,dataProvider="addProductToCartData")
	public void addProductToCartVerification(String searchKey, String productName, int qty) {
		searchpage = accountpage.searchProducts(searchKey);
		prodinfopage = searchpage.selectSearchProduct(productName);
		String successMsg = prodinfopage.addProductToCart(qty);
		
		//verify success message
		softAssert.assertEquals(successMsg, "Success: You have added "+productName+ " to your shopping cart!");
				
		//verify product quantity added
		String qtyValue = prodinfopage.getCartItemsDetails();
		int indexValue = qtyValue.indexOf(" ");
		softAssert.assertEquals(qtyValue.substring(0, indexValue), String.valueOf(qty));
		
		//verify total amount calculated
		Map<String,String> prodDetails = prodinfopage.getProductDetails();
		String prodQtyPrice = prodDetails.get("productPrice");
		prodQtyPrice = ((prodQtyPrice.replaceAll(",", "")).replaceAll("$", "")).substring(1);
		Float singlePrice = Float.parseFloat(prodQtyPrice);

		String totalQtyPrice = ((qtyValue.substring(qtyValue.indexOf("$"))).replaceAll(",", "")).substring(1);
		Float totalPrice = Float.parseFloat(totalQtyPrice);
	
		Float totalExpValue = singlePrice * qty;
		
		softAssert.assertEquals(totalPrice, totalExpValue);
		
		//verify remove from cart feature
		prodinfopage.removeSingleAddedProductFromCart();
		String msg3 = prodinfopage.getCartItemsDetails();
		softAssert.assertEquals(msg3.substring(0, 1), String.valueOf(0));
		
		softAssert.assertAll();
	}
	
}
