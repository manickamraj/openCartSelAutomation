package com.qa.openCart.factory;

import java.util.Properties;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;

public class DriverOptionsManager {

	private Properties prop;
	private ChromeOptions co;
	private EdgeOptions eo;
	
	public DriverOptionsManager(Properties prop) {
		this.prop = prop;
	}
	
	public ChromeOptions getChromeOptions() {
		co = new ChromeOptions();
		if(prop.getProperty("headless").equalsIgnoreCase("true")) {
			co.addArguments("--headless");
		}
		if(prop.getProperty("incognito").equalsIgnoreCase("true")) {
			co.addArguments("--incognito");
		}
		return co;
	}
	
	public EdgeOptions getEdgeOptions() {
		eo = new EdgeOptions();
		if(prop.getProperty("headless").equalsIgnoreCase("true")) {
			eo.addArguments("--headless");
		}
		if(prop.getProperty("incognito").equalsIgnoreCase("true")) {
			eo.addArguments("--incognito");
		}
		return eo;
	}
}
