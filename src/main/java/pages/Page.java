package pages;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.support.ui.Select;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import utilities.ExcelUtility;
import utilities.ExtentManager;

public class Page {
	public static WebDriver driver;
	protected static Properties config;
	protected static Properties OR;
	protected static FileInputStream fis;
	static String path = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test"
			+ File.separator + "resources" + File.separator + "excel" + File.separator + "testdata.xlsx";
	protected static ExcelUtility eu = new ExcelUtility(path);
	protected ChromeOptions options;
	protected static Logger log = LogManager.getLogger(Page.class);
	protected static ExtentReports rep = ExtentManager.createExtentInstance();
	protected static ThreadLocal<ExtentTest> testThread = new ThreadLocal<ExtentTest>();
	protected static TopMenu tm;

	protected Page() {
		String configPath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main"
				+ File.separator + "resources" + File.separator + "Properties" + File.separator + "Config.properties";

		try {
			fis = new FileInputStream(configPath);
			config = new Properties();
			config.load(fis);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (driver == null) {

			if (config.getProperty("browser").equalsIgnoreCase("chrome")) {
				path = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main"
						+ File.separator + "resources" + File.separator + "executables" + File.separator
						+ "chromedriver.exe";
				System.setProperty("webdriver.chrome.driver", path);
				options = new ChromeOptions();
				options.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);
				driver = new ChromeDriver(options);

			} else if (config.getProperty("browser").equalsIgnoreCase("ie")) {
				path = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main"
						+ File.separator + "resources" + File.separator + "executables" + File.separator
						+ "IEdriverServer.exe";
				System.setProperty("webdriver.ie.driver", path);
				driver = new InternetExplorerDriver();

			}

			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
			driver.get(config.getProperty("LoginUrl"));

		}

		String ORPath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main"
				+ File.separator + "resources" + File.separator + "Properties" + File.separator + "OR.properties";

		try {
			OR = new Properties();
			fis = new FileInputStream(ORPath);
			OR.load(fis);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tm = new TopMenu(driver);

	}

	public static WebElement getElement(String loc) {
		try {
			if (loc.endsWith("_CSS")) {
				System.out.println("hello2");
				System.out.println(driver.findElement(By.cssSelector(OR.getProperty(loc))));
				return driver.findElement(By.cssSelector(OR.getProperty(loc)));
			} else if (loc.endsWith("_XPATH")) {
				System.out.println("hello1");
				System.out.println(driver.findElement(By.xpath(OR.getProperty(loc))));
				return driver.findElement(By.xpath(OR.getProperty(loc))); // if cannot find will throw exception
			} else if (loc.endsWith("_ID")) {
				System.out.println("hello3");
				System.out.println(driver.findElement(By.id(OR.getProperty(loc))));
				return driver.findElement(By.id(OR.getProperty(loc)));
			}
			log.info("Type of locator not handled :" + loc + "Please provide CSS,XPATH OR ID");
			return null;
		} catch (NoSuchElementException e) {
			e.printStackTrace();
			testThread.get().info(loc + " not found");
			log.info(loc + " not found");
			return null;

		}
	}

	public static boolean isElementPresent(String loc) {

		if (getElement(loc) != null) {
			testThread.get().info(loc + " is present ");
			log.info(loc + " is present ");
			return true;
		} else {
			return false;
		}
	}

	public static boolean click(String loc) {

		if (getElement(loc) != null) {
			getElement(loc).click();
			testThread.get().info(loc + " is clicked successully ");
			log.info(loc + " is clicked successully ");
			return true;
		} else
			return false;
	}

	public static boolean sendKeys(String loc, String keys) {
		if (getElement(loc) != null) {
			getElement(loc).sendKeys(keys);
			testThread.get().info("Successfully entered " + keys + " in  " + loc + " ");
			log.info("Successfully entered " + keys + " in  " + loc + " ");
			return true;
		} else
			return false;
	}

	public static boolean selectDropdown(String elementLocator, String dropdownValue) {
		if (getElement(elementLocator) != null) {
			WebElement dropdown = getElement(elementLocator);
			Select selectElement = new Select(dropdown);
			selectElement.selectByVisibleText(dropdownValue);
			testThread.get()
					.info("Successfully selected value " + dropdownValue + " in the dropdown " + elementLocator);
			log.info("Successfully selected value " + dropdownValue + " in the dropdown " + elementLocator);
			return true;
		} else {
			return false;
		}

	}

	public static void quit() {
		if (driver != null) {
			driver.quit();
		}
	}

}
