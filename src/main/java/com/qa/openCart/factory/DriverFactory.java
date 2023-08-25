package com.qa.openCart.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.aspectj.util.FileUtil;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.io.FileHandler;

public class DriverFactory {

	public WebDriver driver;
	public Properties prop;
	public DriverOptionsManager options;
	
	
	public static String highlight; 
	public static String testDataPath;
	public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();
	
	/**
	 * This method initialize properties data to a prop object
	 * @return : Config.Properties object
	 */
	public Properties initProp() {
		prop = new Properties();
		FileInputStream fis = null;
		String envToExecute = System.getProperty("env");
		System.out.println("Execution is happening in environment :" +envToExecute);
		
		try {
		if (envToExecute.equalsIgnoreCase("qa")) {
			fis = new FileInputStream("./src/test/resources/properties/qa.config.properties");
		} else {
			fis = new FileInputStream("./src/test/resources/properties/config.properties");
		}
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			prop.load(fis);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop;
	}
	
	public WebDriver initDriver(Properties prop) {
		
		highlight = prop.getProperty("highlight");
		testDataPath = prop.getProperty("testdata");
		options = new DriverOptionsManager(prop);
		String browserName = prop.getProperty("browser");
		if (browserName.contentEquals("chrome")) {
			//driver = new ChromeDriver(options.getChromeOptions());
			tlDriver.set(new ChromeDriver(options.getChromeOptions()));
		}
		else if (browserName.equalsIgnoreCase("edge")) {
			//driver = new EdgeDriver(options.getEdgeOptions());
			tlDriver.set(new EdgeDriver(options.getEdgeOptions()));
		}
		else {
			System.out.println("Provide either Chrome or Edge browser");
		
	}
		return getDriver();
	}
	
	public synchronized static WebDriver getDriver() {
		return tlDriver.get();
	}
	
	public static String getScreenshot() {
		File srcFile = ((TakesScreenshot)getDriver()).getScreenshotAs(OutputType.FILE);
		String path = System.getProperty("user.dir")+"/screenshot/"+System.currentTimeMillis()+".png";
		File destination = new File(path);
		try {
			FileUtil.copyFile(srcFile, destination);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return path;
	}
}
