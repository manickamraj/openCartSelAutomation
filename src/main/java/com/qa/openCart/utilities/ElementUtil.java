package com.qa.openCart.utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Month;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qa.openCart.factory.DriverFactory;

public class ElementUtil {
	
	private WebDriver driver;
	private JavaScriptUtil jsutil;
	
	public ElementUtil(WebDriver driver) {
		this.driver = driver;
		jsutil = new JavaScriptUtil(driver);
	}
	
	public By dynamicLocator(String locator, String oldValue, String newValue ) {
		String newLocator = locator.replace(oldValue, newValue);
		return By.xpath(newLocator);
		}
	
	public WebElement findElement(By locator) {
		WebElement element = driver.findElement(locator);
		if(Boolean.parseBoolean(DriverFactory.highlight)) {
			jsutil.flashWebElementByJS(locator);
		}
		return element;
	}
	
	public List<WebElement> findElementsList(By locator) {
		return driver.findElements(locator);
	}
	
	public void doSendKeys(By locator,String str) {
		findElement(locator).sendKeys(str);
	}
	
	public void doActionSendKeys(By locator,String str) {
		Actions act = new Actions(driver);
		act.sendKeys(findElement(locator), str).build().perform();
	}
	
	public void doActionMoveToElement(By locator) {
		Actions act = new Actions(driver);
		act.moveToElement(findElement(locator)).build().perform();
	}
	
	public void clearTextField(By locator) {
		findElement(locator).clear();
	}
	
	public String getTextFindElement(By locator) {
		return findElement(locator).getText();
	}
	
	public String getAttribute(By locator, String attribute) {
		return findElement(locator).getAttribute(attribute);
	}
	
	public void clickButton(By locator) {
		findElement(locator).click();
	}
	
	public void clickImage(By locator) {
		findElement(locator).click();
	}
	
	public void clickLink(By locator) {
		findElement(locator).click();
	}
	
	public void doActionClick(By locator) {
		Actions act = new Actions(driver);
		act.click(findElement(locator)).build().perform();
	}
	
	public void checkBox(By locator) {
		findElement(locator).click();
	}
	
	public List<String> doSearchGetTextList(By locator) {
		return getTextfindElementsList(locator);
	}
	
	public void doSearchAndClickSugg(By locator,String SuggText) {
		List<WebElement> elements = findElementsList(locator);
		for(WebElement e : elements) {
			String text = e.getText();
			if(text.contains(SuggText)) {
				e.click();
				break;
			}
		}

	}
	
	public List<String> getTextfindElementsList(By locator) {
		List<WebElement> elements = findElementsList(locator);
		List<String> attributeList = new ArrayList<String>();
		for(WebElement e : elements) {
			attributeList.add(e.getText());
		}
		return attributeList;
	}
	
    public void selectDropDownOptionByIndex(By locator, int index) {
    	Select dropDown = new Select(findElement(locator));
    	dropDown.selectByIndex(index);
    }
	
    public void selectDropDownOptionByValue(By locator, String value) {
    	Select dropDown = new Select(findElement(locator));
    	dropDown.selectByValue(value);
    }
    
    public void selectDropDownOptionByText(By locator, String text) {
    	Select dropDown = new Select(findElement(locator));
    	dropDown.selectByVisibleText(text);
    }
    
    public ArrayList<String> getDropDownOptionsList(By locator) {
    	List<WebElement> elements = findElementsList(locator);
    	ArrayList<String> dropDownList = new ArrayList<String>();
    	for(WebElement e : elements) {
    		dropDownList.add(e.getText());
    	}
    	return dropDownList;
    }
    
    public WebElement getDropDownCurrentList(By locator) {
    	Select dropDown = new Select(findElement(locator));
    	return dropDown.getFirstSelectedOption();
    }
    
    public WebDriver switchToFrameByNameOrId(String value) {
    	return driver.switchTo().frame(value);
    }
    
    public WebDriver switchToFrameByWebElement(By locator) {
    	return driver.switchTo().frame(driver.findElement(locator));
    }
    
    public WebDriver switchToParentFrame() {
    	return driver.switchTo().parentFrame();
    }
    
    public WebDriver switchBackFromFrameToDefaultContent() {
    	return driver.switchTo().defaultContent();
    }

    public void acceptAlert() {
    	driver.switchTo().alert().accept();
    }
    
    public void cancelAlert() {
    	driver.switchTo().alert().dismiss();
    }
    
    public String getTextFromAlert() {
    	return driver.switchTo().alert().getText();
    }
    
    public String retrieveWindowHandle() {
    	return driver.getWindowHandle() ;
    }
    
    public Set<String> retrieveWindowHandles() {
    	return driver.getWindowHandles();
    }
    
    public WebDriver twoWindowNavigateToChildWindow(String parentWindow) throws InterruptedException {
    	Set<String> windowsList = retrieveWindowHandles();
		Iterator<String> it = windowsList.iterator();
		while(it.hasNext()) {
			String windID = it.next();
			driver.switchTo().window(windID);
			String str = retrieveWindowHandle();
			if(!str.equals(parentWindow)) {
				Thread.sleep(2000);
				return driver;
			}
		}
		return driver;
    }
    
    public void printWebTableValues(By locatorTH, By locatorTR,String IrowsLocator,int startRow,int startCol) {
    	String row = String.valueOf(startRow);
		String col = String.valueOf(startCol);
		int totaltableheader = driver.findElements(locatorTH).size();
		System.out.println("Total Columns :" + totaltableheader);
		int totalrows = driver.findElements(locatorTR).size();
		System.out.println("Total Rows :" + totalrows);
		List<String> headerData = getTextfindElementsList(locatorTH);
		for(String e : headerData) {
			System.out.print(e + " : ");
		}
		System.out.println();
		System.out.println("-------------------------------------------------------------------");
		for(int i=startRow; i<=totalrows;i++) {
			String ReplacingRows = IrowsLocator.replace(row, String.valueOf(i));
			for(int j=startCol;j<=totaltableheader;j++) {
				String ReplacingCols = ReplacingRows.replace(col, String.valueOf(j));
				By indValue = By.xpath(ReplacingCols);
				String value = driver.findElement(indValue).getText();
				System.out.print(value + " : ");
			}
			System.out.println();
		}
    }
    
    /**
     * Find WebElement on DOM with a timOut
     * @param locator
     * @param timeOut
     * @return
     */
    public WebElement webDriverWaitFindWebElementOnDom(By locator, int timeOut) {
    	WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timeOut));
    	return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }
    
    /**
     * Find WebElement on the web page with a timOut
     * @param locator
     * @param timeOut
     * @return
     */
    public WebElement webDriverWaitFindElementOnPage(By locator, int timeOut) {
    	WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timeOut));
    	WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    	if(Boolean.parseBoolean(DriverFactory.highlight)) {
			jsutil.flashWebElementByJS(locator);
		}
    	return element;
    }
    
    public List<WebElement> webDriverWaitFindElementsOnPage(By locator, int timeOut) {
    	WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timeOut));
    	 List<WebElement> element = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    	 if(Boolean.parseBoolean(DriverFactory.highlight)) {
 			jsutil.flashWebElementByJS(locator);
 		}
    	 return element; 
    }
    
    public WebDriver waitForFrameAndSwitchUsingIDOrName(String IdOrName,int timeOut ) {
    	WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timeOut));
    	return wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(IdOrName));
    }
    
    public WebDriver waitForFrameAndSwitchUsingLocator(By locator,int timeOut ) {
    	WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timeOut));
    	return wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(locator));
    }
    
    /**
     * Sample FluentWait method for reference 
     * @param totalTimeOut
     * @param pollingTime
     * @param locator
     * @return
     */
    public WebElement fuentWait(int totalTimeOut, int pollingTime, By locator) {
    	FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver);
    			wait.withTimeout(Duration.ofSeconds(totalTimeOut))
    				.ignoring(NoSuchElementException.class)
    				.ignoring(StaleElementReferenceException.class)
    				.pollingEvery(Duration.ofSeconds(pollingTime))
    				.withMessage("Element not visible");
    			return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
    
    public void selectDateInCalendar(String day, String month, String year,String CalendarDayxPath,By yearLocator,By monthLocator, By NextLink, By PrevLink ) throws ParseException {
    	
    	if(Integer.parseInt(year)%4 !=0 && month.contains("Feb") && Integer.parseInt(day)>28) {
    		System.out.println("February date is incorrect - give the correct date");
    		return;
    	}
    	
    	if(Integer.parseInt(year)%4 ==0 && month.contains("Feb") && Integer.parseInt(day)>29) {
    		System.out.println("February date is incorrect - give the correct date");
    		return;
    	}
    	
    	if(month.contains("Apr") || month.contains("Jun") || month.contains("Sep") || month.contains("Nov"))
    	{
    		if(Integer.parseInt(day)>30) {
    			System.out.println("Date is incorrect for the give month : " +month);
        		return;	
    		}
    	} else
    	{
    		if(Integer.parseInt(day)>31) {
    			System.out.println("Date is incorrect for the give month : " +month);
        		return;	
    		}
    	}
    	
    	By dayLocator = dynamicLocator(CalendarDayxPath, "DAY", day);
    	
    	Date Date = new Date();
		SimpleDateFormat FormatDate = new SimpleDateFormat("yyyy-MMM-dd");
		Date dateToSelect = FormatDate.parse(year+"-"+month+"-"+day);
		
		String currDate = FormatDate.format(Date);
		Date todaysDate = FormatDate.parse(currDate);
	
		if(dateToSelect.before(todaysDate)) {
		while(!(getTextFindElement(yearLocator).equals(year) && getTextFindElement(monthLocator).contains(month))) {
				clickLink(PrevLink);
			}
		clickLink(dayLocator);
		}
		
		if(dateToSelect.after(todaysDate)) {
			while(!(getTextFindElement(yearLocator).equals(year) && getTextFindElement(monthLocator).contains(month))) {
				clickLink(NextLink);
			}
		clickLink(dayLocator);
		}
		
		if(dateToSelect.equals(todaysDate)) {
			clickLink(dayLocator);
		}

    }
}