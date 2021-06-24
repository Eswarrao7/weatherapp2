package com.TestNG;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class WeatherAppTestCases {
	
	WebDriver driver;
	
	@BeforeMethod
	public void setUp()
	{
	ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox"); // Bypass OS security model, MUST BE THE VERY FIRST OPTION
        options.addArguments("--headless");
       // options.setExperimentalOption("useAutomationExtension", false);
       // options.addArguments("start-maximized"); // open Browser in maximized mode
        //options.addArguments("disable-infobars"); // disabling infobars
       // options.addArguments("--disable-extensions"); // disabling extensions
       // options.addArguments("--disable-gpu"); // applicable to windows os only
        //options.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
		System.setProperty("webdriver.chrome.driver", "/home/eswar/Capstone_Project/chromedriver_linux64/chromedriver");
		//driver = new ChromeDriver();
		/*System.setProperty("webdriver.gecko.driver", "C:\\Users\\JABRU\\Desktop\\geckodriver.exe");
		driver = new FirefoxDriver();*/
        driver = new ChromeDriver(options);
        //options.merge(capabilities);
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(100, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.get("http://localhost:5050/weatherapp/");
	}
	@Test(priority=1)
	public void verifyPageTitleTest()
	{
		String title = driver.getTitle();
		System.out.println("********** Title : "+title+" **********");
		Assert.assertEquals(title, "Weather App");
	}
	
	@Test(priority=2)
	public void verifyWeatherAppContent()
	{
		boolean flag = driver.findElement(By.xpath("/html/body/div")).isDisplayed();
		if(flag)
			System.out.println("********** Content Found **********");
		else
			System.out.println("********** Content Not Found **********");
		Assert.assertTrue(flag);
	}
	
	@Test(priority=3)
	public void verifyWeatherAppPlace()
	{
		String place="London";
		WebElement city=driver.findElement(By.className("search-bar"));
		city.sendKeys(place);
		driver.findElement(By.tagName("button")).click();
		WebElement content = driver.findElement(By.xpath("/html/body/div/div[2]/h2"));
		String val = content.getText();
		String[] a=val.split(" ");
		String actualval = a[0]+" "+a[1]+" "+place;
		Assert.assertEquals(actualval,"Weather in "+place);
		System.out.println("*********** Weather : "+actualval+" **********");
	}
	
	@Test(priority=4)
	public void verifyWeatherAppTemperature()
	{
		WebElement content = driver.findElement(By.xpath("/html/body/div/div[2]/h1"));
		System.out.println("********** Temperature : "+content.getText()+" **********");
		boolean flag = driver.findElement(By.xpath("/html/body/div/div[2]/h1")).isDisplayed();
		Assert.assertTrue(flag);
	}
	
	@Test(priority=5)
	public void verifyWeatherDetails()
	{
		boolean flag=false;
		boolean flag1 = driver.findElement(By.xpath("/html/body/div/div[2]/div[1]")).isDisplayed();
		boolean flag2 = driver.findElement(By.xpath("/html/body/div/div[2]/div[2]")).isDisplayed();
		boolean flag3 = driver.findElement(By.xpath("/html/body/div/div[2]/div[3]")).isDisplayed();
		boolean flag4 = driver.findElement(By.xpath("/html/body/div/div[2]/div[4]")).isDisplayed();
		boolean flag5 = driver.findElement(By.xpath("/html/body/div/div[2]/div[5]")).isDisplayed();
		if(flag1==flag2==flag3==flag4==flag5==true)
			flag=true;
		if(flag)
			System.out.println("********** Details Found **********");
		else
			System.out.println("********** Details Not Found **********");
		Assert.assertTrue(flag);
	}
	
	@Test(priority=6)
	public void verifyWeatherAppAlert()
	{
		String place="xyz";
		WebElement city=driver.findElement(By.className("search-bar"));
		city.sendKeys(place);
		driver.findElement(By.tagName("button")).click();
		WebDriverWait wait = new WebDriverWait(driver, 300 /*timeout in seconds*/);
		boolean flag = true;
		if(wait.until(ExpectedConditions.alertIsPresent())==null)
		{
		    System.out.println("********** Alert Found **********");
		    flag = false;
		}
		else
		{
		    System.out.println("********** Alert Not Found **********");
		    flag = true;
		}
		
		Assert.assertEquals(flag, true);
		
	}
	
	@AfterMethod
	public void tearDown()
	{
		driver.quit();
	}
	
	

}
