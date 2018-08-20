package com.ProjecName.base;

import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Select;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.aventstack.extentreports.Status;
import com.google.common.collect.Iterables;


public class GeneralKeywords  extends Base
{

	/* Method Name 		:	appLaunch
	 * Description		:	This method is used to perform all actions of loading all files to memory 
	 */

	public static WebDriver webAppLaunch() throws MalformedURLException
	{
		if(CONFIG.getProperty(GlobalConstants.RUN_INTEGRATION).
				equalsIgnoreCase(GlobalConstants.RUN_MODE_YES))
		{
			if(CONFIG.getProperty(GlobalConstants.BROWSER_TYPE).equalsIgnoreCase(GlobalConstants.BROWSER_FIREFOX))
			{
				System.setProperty("WebDriver.gecko.driver", "D:/jars/geckodriver.exe");
				WebD = new FirefoxDriver();	
			}
			else if(CONFIG.getProperty(GlobalConstants.BROWSER_TYPE).equalsIgnoreCase(GlobalConstants.BROWSER_CHROME))
			{
				System.setProperty(GlobalConstants.CHROMEDRIVER_PROPERTY,GlobalConstants.CHROMEDRIVER_EXE_PATH);
				ChromeOptions options = new ChromeOptions();
				options.addArguments("--test-type");	
				WebD = new ChromeDriver(options);
			}
			WebD.get(CONFIG.getProperty(GlobalConstants.SITE_URL));	
			WebD.manage().timeouts().implicitlyWait(80, TimeUnit.SECONDS);	
			WebD.manage().window().maximize();
		}
		return WebD;
	}

	/* Method Name 		:	appLaunch
	 * Description		:	This method is used to perform all actions of loading all files to memory 
	 */

	public static WebDriver appLaunch() throws MalformedURLException
	{
		if(CONFIG.getProperty(GlobalConstants.CONFIG_APPLICATION_TYPE).
				equalsIgnoreCase(GlobalConstants.CONFIG_APPLICATION_TYPE_MOBILEVALUE))
		{

			//System.out.println("Launching driver");
			url=CONFIG.getProperty(GlobalConstants.MOBILE_URL);
			driver= new AndroidDriver<MobileElement>(new URL(url),
					capabilities);
			driver.manage().timeouts().implicitlyWait(80, TimeUnit.SECONDS);
			//System.out.println("Launching driver is done");
		}
		return driver;
	}

	/* Method Name 		:	getObjectValue
	 * Description		:	This method is used to get object value from object repository
	 */
	public static WebElement getObjectValue(String orObject)
	{
		int flag=0;
		WebElement temp=null;
		if(OR.getProperty(orObject) != null )
		{	//if(OR.getProperty(orObject) != null )	
			String s= OR.getProperty(orObject);
			//	System.out.println(s);
			String[] spiltVal1= s.split("#");		
			for (int i = 0; i < spiltVal1.length; i++) 
			{		//for (int i = 0; i < spiltVal1.length; i++) 
				String[] spiltVal= spiltVal1[i].split(",");
				for (int j = 0; j < spiltVal.length; j++) 
				{		// for (int j = 0; j < spiltVal.length; j++) 
					if(j==1)
					{// start 	if(j==1)
						String locator=spiltVal[0].trim();// triming is done only on either sie & not in between
						String locatorValue=spiltVal[1].trim();//triming is done only on either sie & not in between

						if(locator.equals(null) || locatorValue.equals(null))
						{// start for if(locator.equals(null) || locatorValue.equals(null))
							System.out.println("Locator is null or value is null enter correct details");
						}// end for if(locator.equals(null) || locatorValue.equals(null))
						else
						{// start for else
							if(flag==0)
							{// start for if flag==0
								switch(locator)
								{// start of switch case
								case "id": if(locator.equalsIgnoreCase(GlobalConstants.LOCATOR_ID))
								{
									flag=1;
									//System.out.println("Chosen Val is Id");
									temp =driver.findElement(By.id(locatorValue));
									break;						
								}
								case "name": if(locator.equalsIgnoreCase(GlobalConstants.LOCATOR_NAME))
								{
									flag=1;
									//System.out.println("Chosen Val is name");
									temp =driver.findElement(By.name(locatorValue));
									break;
								}
								case "classname": if(locator.equalsIgnoreCase(GlobalConstants.LOCATOR_CLASSNAME))
								{
									flag=1;
									//System.out.println("Chosen Val is classname");
									temp =driver.findElement(By.className(locatorValue));
									break;
								}
								case "linkText": if(locator.equalsIgnoreCase(GlobalConstants.LOCATOR_LINK_TEXT))
								{
									flag=1;
									//System.out.println("Chosen Val is linktext");
									temp =driver.findElement(By.linkText(locatorValue));
									break;
								}

								case "partialLinkText":if(locator.equalsIgnoreCase(GlobalConstants.LOCATOR_PARTIAL_LINK_TEXT))
								{
									flag=1;
									//System.out.println("Chosen Val is partialLinkText");
									temp =driver.findElement(By.partialLinkText(locatorValue));
									break;
								}
								case "tagName":if(locator.equalsIgnoreCase(GlobalConstants.LOCATOR_TAGNAME))
								{
									flag=1;
									//System.out.println("Chosen Val is tagName");
									temp =driver.findElement(By.tagName(locatorValue));
									break;
								}
								case "cssSelector":if(locator.equalsIgnoreCase(GlobalConstants.LOCATOR_CSSSELECTOR))
								{
									flag=1;
									//System.out.println("Chosen Val is cssSelector");
									temp =driver.findElement(By.cssSelector(locatorValue));
									break;
								}
								case "xpath":if(locator.equalsIgnoreCase(GlobalConstants.LOCATOR_XPATH))
								{
									flag=1;
									//System.out.println("Chosen Val is xpath");
									temp =driver.findElement(By.xpath(locatorValue));
									break;
								}
								default: 	flag=1;
								System.out.println("locator s not found"); 
								break;
								}// end of switch case

							}// end for if flag==0

						}// end for else

					}	// end if(j==1)
				}//for (int j = 0; j < spiltVal.length; j++) 

			}//for (int i = 0; i < spiltVal1.length; i++) 

		}//if(OR.getProperty(orObject) != null )
		flag=0;
		return temp;
	}


	protected static boolean isElementPresent(By by){
		try{
			driver.findElement(by);
			return true;
		}
		catch(NoSuchElementException e){
			return false;
		}
	}


	public static void setCapability(String appType,String deviceName) throws MalformedURLException
	{
		if(appType.
				equalsIgnoreCase(GlobalConstants.CONFIG_APPLICATION_TYPE_WEBVALUE))
		{

			Reporter.log("Selected app Type is "+CONFIG.get("appType"),true);
		}
		else if(CONFIG.getProperty(GlobalConstants.CONFIG_APPLICATION_TYPE).
				equalsIgnoreCase(GlobalConstants.CONFIG_APPLICATION_TYPE_MOBILEVALUE))
		{
			url=CONFIG.get(GlobalConstants.MOBILE_URL).toString();
			//File app=new File(CONFIG.get(GlobalConstants.ANDROID_APK_FILE_PATH).toString());
			capabilities=new DesiredCapabilities();
			//CONFIG.getProperty(GlobalConstants.DEVICE_NAME)
			capabilities.setCapability(GlobalConstants.DEVICE, CONFIG.getProperty(GlobalConstants.DEVICE_TYPE));
			capabilities.setCapability(GlobalConstants.DEVICE_NAME, deviceName);
			capabilities.setCapability(GlobalConstants.PLATFORM_VERSION, CONFIG.getProperty(GlobalConstants.PLATFORM_VERSION));
			capabilities.setCapability(GlobalConstants.PLATFORM_NAME, CONFIG.getProperty(GlobalConstants.PLATFORM_NAME));
			capabilities.setCapability(GlobalConstants.AUTOMATION_NAME,CONFIG.getProperty(GlobalConstants.AUTOMATION_NAME));
			capabilities.setCapability(GlobalConstants.AUTO_ACCEPT_ALERTS, CONFIG.getProperty(GlobalConstants.AUTO_ACCEPT_ALERTS));
			//capabilities.setCapability(GlobalConstants.APP_PATH,app.getAbsolutePath());
			capabilities.setCapability(GlobalConstants.COMMAND_TIMEOUT,CONFIG.getProperty(GlobalConstants.COMMAND_TIMEOUT));	

			if(CONFIG.getProperty(GlobalConstants.MOBILE_APP_VERSION).
					equalsIgnoreCase(GlobalConstants.MOBILE_APP_VERSIONGSVALUE))
			{
				System.out.println("Running on mobile");			
				capabilities.setCapability(GlobalConstants.APP_PACKAGE,CONFIG.getProperty(GlobalConstants.GS_APP_PACKAGE_VALUE));			 			
				capabilities.setCapability(GlobalConstants.ANDROID_WAIT_ACTIVITY,
						CONFIG.getProperty(GlobalConstants.GS_ANDROID_WAIT_ACTIVITY));

			}
			else if(CONFIG.getProperty(GlobalConstants.MOBILE_APP_VERSION).
					equalsIgnoreCase(GlobalConstants.MOBILE_APP_VERSIONENTRPRISEVALUE))
			{
				System.out.println("Running on E version");	

				//capabilities.setCapability("appActivity",CONFIG.getProperty(GlobalConstants.ENTERPRISE_ANDROID_WAIT_ACTIVITY));
				capabilities.setCapability(GlobalConstants.APP_PACKAGE,
						CONFIG.getProperty(GlobalConstants.ENTERPRISE_APP_PACKAGE_VALUE));					
				capabilities.setCapability(GlobalConstants.ANDROID_WAIT_ACTIVITY,
						CONFIG.getProperty(GlobalConstants.ENTERPRISE_ANDROID_WAIT_ACTIVITY));			

			}

		}
		//Launch app on phone
		GeneralKeywords.appLaunch();
	}

	public static WebDriver webLaunch() throws MalformedURLException
	{
		if(CONFIG.getProperty(GlobalConstants.RUN_INTEGRATION).
				equalsIgnoreCase(GlobalConstants.RUN_MODE_YES))
		{
			Reporter.log("Selected app Type is "+CONFIG.get("appType"));

			if(CONFIG.getProperty(GlobalConstants.BROWSER_TYPE).equalsIgnoreCase(GlobalConstants.BROWSER_FIREFOX))
			{

				WebD = new FirefoxDriver();	
			}
			else if(CONFIG.getProperty(GlobalConstants.BROWSER_TYPE).equalsIgnoreCase(GlobalConstants.BROWSER_CHROME))
			{

				System.setProperty(GlobalConstants.CHROMEDRIVER_PROPERTY,GlobalConstants.CHROMEDRIVER_EXE_PATH);
				ChromeOptions options = new ChromeOptions();
				options.addArguments("--test-type");	

				WebD = new ChromeDriver(options);
			}

			WebD.get(CONFIG.getProperty(GlobalConstants.SITE_URL));	
			WebD.manage().timeouts().implicitlyWait(80, TimeUnit.SECONDS);	
			WebD.manage().window().maximize();
		}

		if(CONFIG.getProperty(GlobalConstants.RUN_INTEGRATION).
				equalsIgnoreCase(GlobalConstants.RUN_MODE_NO))
		{
			System.out.println("Since integration is not set to Yes.Can not proceed further");
		}
		return WebD;

	}


	public static WebElement getWebObjectValue(String orObject)
	{
		int flag=0;
		WebElement temp=null;
		if(WebOR.getProperty(orObject) != null )
		{	//if(OR.getProperty(orObject) != null )	
			String s= WebOR.getProperty(orObject);
			//	System.out.println(s);
			String[] spiltVal1= s.split(";");		
			for (int i = 0; i < spiltVal1.length; i++) 
			{		//for (int i = 0; i < spiltVal1.length; i++) 
				String[] spiltVal= spiltVal1[i].split(":");
				for (int j = 0; j < spiltVal.length; j++) 
				{		// for (int j = 0; j < spiltVal.length; j++) 
					if(j==1)
					{// start 	if(j==1)
						String locator=spiltVal[0].trim();// triming is done only on either sie & not in between
						String locatorValue=spiltVal[1].trim();//triming is done only on either sie & not in between

						if(locator.equals(null) || locatorValue.equals(null))
						{// start for if(locator.equals(null) || locatorValue.equals(null))
							System.out.println("Locator is null or value is null enter correct details");
						}// end for if(locator.equals(null) || locatorValue.equals(null))
						else
						{// start for else
							if(flag==0)
							{// start for if flag==0
								switch(locator)
								{// start of switch case
								case "id": if(locator.equalsIgnoreCase(GlobalConstants.LOCATOR_ID))
								{
									flag=1;
									//System.out.println("Chosen Val is Id"+locatorValue);
									temp =WebD.findElement(By.id(locatorValue));
									break;						
								}
								case "name": if(locator.equalsIgnoreCase(GlobalConstants.LOCATOR_NAME))
								{
									flag=1;
									//System.out.println("Chosen Val is name");
									temp =WebD.findElement(By.name(locatorValue));
									break;
								}
								case "classname": if(locator.equalsIgnoreCase(GlobalConstants.LOCATOR_CLASSNAME))
								{
									flag=1;
									//System.out.println("Chosen Val is classname");
									temp =WebD.findElement(By.className(locatorValue));
									break;
								}
								case "linkText": if(locator.equalsIgnoreCase(GlobalConstants.LOCATOR_LINK_TEXT))
								{
									flag=1;
									//System.out.println("Chosen Val is linktext");
									temp =WebD.findElement(By.linkText(locatorValue));
									break;
								}

								case "partialLinkText":if(locator.equalsIgnoreCase(GlobalConstants.LOCATOR_PARTIAL_LINK_TEXT))
								{
									flag=1;
									//System.out.println("Chosen Val is partialLinkText");
									temp =WebD.findElement(By.partialLinkText(locatorValue));
									break;
								}
								case "tagName":if(locator.equalsIgnoreCase(GlobalConstants.LOCATOR_TAGNAME))
								{
									flag=1;
									//System.out.println("Chosen Val is tagName");
									temp =WebD.findElement(By.tagName(locatorValue));
									break;
								}
								case "cssSelector":if(locator.equalsIgnoreCase(GlobalConstants.LOCATOR_CSSSELECTOR))
								{
									flag=1;
									//System.out.println("Chosen Val is cssSelector");
									temp =WebD.findElement(By.cssSelector(locatorValue));
									break;
								}
								case "xpath":if(locator.equalsIgnoreCase(GlobalConstants.LOCATOR_XPATH))
								{
									flag=1;
									//System.out.println("Chosen Val is xpath");
									temp =WebD.findElement(By.xpath(locatorValue));
									break;
								}
								default: 	flag=1;
								System.out.println("locator s not found"); 
								break;
								}// end of switch case

							}// end for if flag==0

						}// end for else

					}	// end if(j==1)
				}//for (int j = 0; j < spiltVal.length; j++) 

			}//for (int i = 0; i < spiltVal1.length; i++) 

		}//if(OR.getProperty(orObject) != null )
		flag=0;
		return temp;
	}

	public static void loadCapabilities() throws MalformedURLException
	{
		url=CONFIG.get(GlobalConstants.MOBILE_URL).toString();
		//File app=new File(CONFIG.get(GlobalConstants.ANDROID_APK_FILE_PATH).toString());
		capabilities=new DesiredCapabilities();

		capabilities.setCapability(GlobalConstants.DEVICE, CONFIG.getProperty(GlobalConstants.DEVICE_TYPE));
		capabilities.setCapability(GlobalConstants.DEVICE_NAME, CONFIG.getProperty(GlobalConstants.DEVICE_NAME));
		capabilities.setCapability(GlobalConstants.PLATFORM_VERSION, CONFIG.getProperty(GlobalConstants.PLATFORM_VERSION));
		capabilities.setCapability(GlobalConstants.PLATFORM_NAME, CONFIG.getProperty(GlobalConstants.PLATFORM_NAME));
		capabilities.setCapability(GlobalConstants.AUTOMATION_NAME,CONFIG.getProperty(GlobalConstants.AUTOMATION_NAME));
		capabilities.setCapability(GlobalConstants.AUTO_ACCEPT_ALERTS, CONFIG.getProperty(GlobalConstants.AUTO_ACCEPT_ALERTS));
		capabilities.setCapability(GlobalConstants.App_WaitPackage,CONFIG.getProperty(GlobalConstants.App_WaitPackage));
		capabilities.setCapability(GlobalConstants.App_Activity,CONFIG.getProperty(GlobalConstants.App_Activity));
	}

	/*
	 * Method		: to dispaly the status message
	 * Description  : function to print the msgs on the report and the console
	 */
	public static void printMsg(String msg)
	{
		if(CONFIG.getProperty(GlobalConstants.PRINT_IN_REPORT).equalsIgnoreCase(GlobalConstants.PRINTYES) &&
				CONFIG.getProperty(GlobalConstants.PRINT_IN_CONSOLE).equalsIgnoreCase(GlobalConstants.PRINTYES))
		{
			Reporter.log(msg,true);
			testinfo.log(Status.INFO, msg);
		}
		else if(CONFIG.getProperty(GlobalConstants.PRINT_IN_REPORT).equalsIgnoreCase(GlobalConstants.PRINTYES) &&
				CONFIG.getProperty(GlobalConstants.PRINT_IN_CONSOLE).equalsIgnoreCase(GlobalConstants.PRINTNO))
		{
			Reporter.log(msg,false);
			testinfo.log(Status.INFO, msg);
		}

		else if(CONFIG.getProperty(GlobalConstants.PRINT_IN_REPORT).equalsIgnoreCase(GlobalConstants.PRINTNO) &&
				CONFIG.getProperty(GlobalConstants.PRINT_IN_CONSOLE).equalsIgnoreCase(GlobalConstants.PRINTNO))
		{
			System.out.println(msg);
			testinfo.log(Status.INFO, msg);
		}
	}

	//method to configure the status of the build
	public static void captureStatus(ITestResult result)
	{
		if(result.getStatus()==ITestResult.SUCCESS)
		{
			testinfo.log(Status.PASS, "The test method named as : "+result.getName()+" is passed");
		}
		else if (result.getStatus()==ITestResult.FAILURE) 
		{
			testinfo.log(Status.PASS, "The test method named as : "+result.getName()+" is failed");
			testinfo.log(Status.FAIL, "Test Failure :"+result.getThrowable());
		}
		else if (result.getStatus()==ITestResult.SKIP ) 
		{
			testinfo.log(Status.PASS, "The test method named as : "+result.getName()+" is skipped");
		}
	}
	/*
	 * Description  : function to add a timestamp to a group name
	 */
	public static String timestamp()
	{
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("ddmmhmmss");
		String formattedDate = sdf.format(date);
		return formattedDate;
	}

	/*
	 * Description  : function to verify if the element is present or not
	 */
	public static void verifyPresenceOfElement(List<MobileElement> element, String successMsg, String failMsg)
	{
		if(element.size()!=0)
		{
			System.out.println(successMsg);
		}
		else
		{
			System.out.println(failMsg);
			CustomVerification.verifyContent(false,failMsg );
		}
	}

	public static void verifyAbsenceOfElement(List<MobileElement> element, String SuccessMsg, String FailMsg)
	{
		if(element.size()==0)
		{
			printMsg(SuccessMsg);
		}
		else
		{
			printMsg(FailMsg);
			CustomVerification.verifyContent(false,FailMsg );
		}
	}

	public static void verifyAbsenceOfElementWeb(List<WebElement> element, String SuccessMsg, String FailMsg)
	{
		if(element.size()==0)
		{
			printMsg(SuccessMsg);
		}
		else
		{
			printMsg(FailMsg);
			CustomVerification.verifyContent(false,FailMsg );
		}
	}

	/*
	 * Author       : Srilekha
	 * Date created : 2nd December 2016
	 * Date Modified: 2nd December 2016
	 * Description  : Function to validate the assertions in the test case with expected and actual result
	 */
	public static void validateContent(String actual,String expected,String successmsg,String failmsg)
	{
		//checking if the secure message is displayed
		String val=	CustomVerification.assertEqualsTest(actual,expected);

		if(val.equalsIgnoreCase("Pass"))
		{
			printMsg(successmsg);
		}

		else
		{
			printMsg(failmsg);
		}
	}


	public static void alphabetical(int l, String n)
	{
		char b[] = new char[l];//getting the size of the word
		//storing every char in a string
		for(int i=0;i<l;i++)
			b[i] = n.charAt(i);
		//temp to sort
		char t;
		for(int j=0;j<l-1;j++)//size of the word
		{
			for(int k=0;k<l-1-j;k++)
			{
				if(b[k]>b[k+1])
				{
					t=b[k];
					b[k]=b[k+1];
					b[k+1]=t;
				}
			}
		}
		for(int m=0;m<l;m++)
			System.out.print(b[m]);
	}

	public static void swipeHorizontal (double start, double end, double anchorPercentage, int duration)
	{

		Dimension size = driver.manage().window().getSize();
		System.out.println(size);

		int anchor = (int) (size.height * anchorPercentage);
		int startPoint = (int) (size.width *start);
		int endPoint = (int) (size.width * end);
		new TouchAction(driver).press(startPoint, anchor).waitAction(duration).moveTo(endPoint, anchor).release().perform();

	}

	public static  void scrollDown() 
	{
		Dimension size = driver.manage().window().getSize();
		System.out.println(size);
		int x = size.getWidth() / 2;
		int starty = (int) (size.getHeight() * 0.90);
		//0.60
		int endy = (int) (size.getHeight() * 0.10);
		//0.10
		driver.swipe(x, starty, x, endy, 2000);
	}

	public static void RunMode(String path,String SheetName)
	{
		Xls_Reader xl=new Xls_Reader(path);

		Boolean sheetExist=xl.isSheetExist(SheetName);

		if(sheetExist.TRUE);
		{
			System.out.println("Sheet Exists");

		}

		int Rowcount=xl.getRowCount(SheetName);
		System.out.println("Number of rows:"+" " +Rowcount);

		if(Rowcount!=0);
		{
			System.out.println("Data is present");
		}

		int ColumnCount=xl.getColumnCount(SheetName);
		System.out.println("Number of Columns:" + " " +ColumnCount);

		for(int i=2;i<=Rowcount;i++)
		{

			String value = xl.getCellData(SheetName,"Runmode",i);
			System.out.println(value);

			String  TestSuiteName= xl.getCellData(SheetName,"TestSuiteName",i);
			System.out.println(TestSuiteName);

			if(value.equals("Y"))
			{
				System.out.println("RunMode is Yes");
			}

			else
			{
				System.out.println("RunMode is not yes so skip the execution of the module");
			}
		}
	}
}