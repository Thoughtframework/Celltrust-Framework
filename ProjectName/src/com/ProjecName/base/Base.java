package com.ProjecName.base;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.util.Hashtable;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class Base extends GlobalVariables

{		
	/* Method Name 		:	test_beforeSuite
	 * Description		:	This method is used to perform all actions of loading all files to memory and to load the extent reports
	 */
	@BeforeSuite	
	public  void test_beforeSuite()
	{	
		htmlReporter = new ExtentHtmlReporter(new File(System.getProperty("user.dir")+"/AutomationReports.html"));
		//		htmlReporter.loadXMLConfig(new File(System.getProperty("user.dir")+"/extentconfig.xml"));

		reports = new ExtentReports();
		//set report environment like the environment name, build name etc

		reports.setSystemInfo("Domain","App51");
		reports.attachReporter(htmlReporter);

		//Initialize the Object property
		OR = new Properties();		
		WebOR= new Properties();
		//Initialize the Configurations
		CONFIG = new Properties();		
		try 
		{			
			//Create object for file inputstream to read config file
			fIS1 = new FileInputStream(GlobalConstants.CONFIG_FILE_PATH); 	

			//Load the file to memory
			CONFIG.load( fIS1);	

		}
		catch (Throwable e) 	
		{			

			Reporter.log(GlobalConstants.CONFIG_FILE_NOT_FOUND_ERROR+e.getMessage(),true);	
		}	
	}	

	/* Method Name 		:	test_BeforeClass
	 * Description		:	This method is used to perform to all pre conditions before exec
	 */
	@BeforeClass
	public void test_BeforeClass() throws MalformedURLException, InterruptedException
	{
		try 
		{
			/* Description		:	This method is used to run the testcase on both web and android.
			 * 						To run on both change the run_integration as Yes in the Config file
			 */			
			if(CONFIG.getProperty(GlobalConstants.RUN_INTEGRATION).
					equalsIgnoreCase(GlobalConstants.RUN_MODE_YES))

			{
				fIS = new FileInputStream(GlobalConstants.WEB_OR_FILE_PATH); 

				System.out.println(GlobalConstants.WEB_OR_FILE_PATH);
				System.out.println(GlobalConstants.MOBILE_OR_FILE_PATH);
				fIS1 = new FileInputStream(GlobalConstants.MOBILE_OR_FILE_PATH);
				WebOR.load( fIS);
				OR.load( fIS1);	

			}
			/* Description		:	This method is used to run the testcase either only in web or in mobile 
			 * 						To run on both change the run_integration as NO in the Config file
			 */
			else if(CONFIG.getProperty(GlobalConstants.RUN_INTEGRATION).
					equalsIgnoreCase(GlobalConstants.RUN_MODE_NO))
			{
				//Check app type to load respective OR file to run on web
				if(CONFIG.getProperty(GlobalConstants.CONFIG_APPLICATION_TYPE).
						equalsIgnoreCase(GlobalConstants.CONFIG_APPLICATION_TYPE_WEBVALUE))
				{				
					//Fetch the path of web or file
					fIS = new FileInputStream(GlobalConstants.WEB_OR_FILE_PATH); 
				}
				//to run the application the device
				else if(CONFIG.getProperty(GlobalConstants.CONFIG_APPLICATION_TYPE).
						equalsIgnoreCase(GlobalConstants.CONFIG_APPLICATION_TYPE_MOBILEVALUE))
				{
					//Fetch the path of mobile or file
					fIS = new FileInputStream(GlobalConstants.MOBILE_OR_FILE_PATH);
				}		
				OR.load( fIS);	
			}
		}
		catch (Throwable e) 	
		{			
			Reporter.log("file not found here: "+e.getMessage(),true);	
		}			
	}

	/* Method Name 		:	bm
	 * Description		:	This method is used to perform all actions of loading all files to memory 
	 */
	@BeforeMethod
	public void beforeMethod(Method method) throws MalformedURLException
	{
		//method for extent reports
		String TestName = method.getName();
		testinfo=reports.createTest(TestName);

		if(CONFIG.getProperty("run_on_device").equalsIgnoreCase("Yes"))
		{
			Reporter.log("Selected app Type is "+CONFIG.get("appType"),true);
			GeneralKeywords.loadCapabilities();
			GeneralKeywords.appLaunch();
		}
		else if(CONFIG.getProperty("run_on_device").equalsIgnoreCase("No"))
		{
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
			WebD.get(CONFIG.getProperty("siteURL"));	
			WebD.manage().timeouts().implicitlyWait(80, TimeUnit.SECONDS);	
			WebD.manage().window().maximize();
		}
	}

	/* Description		:	This method is used for extent reports and get the status of each of the tests
	 */
	@AfterMethod
	public static void afterMethod(ITestResult result)
	{

		// Here will compare if test is failing then only it will enter into if condition
		if(ITestResult.FAILURE==result.getStatus())
		{
			try 
			{
				// Create refernce of TakesScreenshot
				TakesScreenshot ts=(TakesScreenshot)WebD;

				// Call method to capture screenshot
				File source=ts.getScreenshotAs(OutputType.FILE);

				// Copy files to specific location here it will save all screenshot in our project home directory and
				// result.getName() will return name of test case so that screenshot name will be same
				FileUtils.copyFile(source, new File("./Screenshots/"+result.getName()+".png"));

				System.out.println("Screenshot taken");
			} 
			catch (Exception e)
			{

				System.out.println("Exception while taking screenshot "+e.getMessage());
			} 
		}
		//method for result status for extent reports
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

		driver.quit();
	}

	/* Method Name 		:	getData
	 * Description		:	This method is used to get data from excel file 
	 */
	@DataProvider(name="ExcelData")
	public  Object[][] getData ()
	{

		String suiteName;
		//= "Login";

		suiteName = moduleName(this.getClass().getCanonicalName());
		//	 System.out.println(suiteName);
		//	 suiteName=getActualFileName(suiteName);
		//	 System.out.println(suiteName);
		Xls_Reader sheetPath=new Xls_Reader(".\\excel_suite\\"+suiteName+".xlsx");

		String scriptName=this.getClass().getSimpleName();;
		// if the sheet is not present
		if(!sheetPath.isSheetExist(scriptName))
		{
			// Making xlsx object free
			sheetPath = null;
			// Return 1 row of data without any column data so that testcase can run atleast one time
			return new Object[1][0];
		}

		int rows = sheetPath.getRowCount(scriptName);		// Row count in datasheet
		int cols = sheetPath.getColumnCount(scriptName);	// Column count in datasheet

		Object[][] data = new Object[rows-1][1];
		Hashtable<String,String> table = null;

		// print the test data
		for(int rowNum=2; rowNum<=rows; rowNum++)
		{
			table = new Hashtable<String,String>();
			for(int colNum=0; colNum<cols; colNum++)
			{
				table.put(sheetPath.getCellData(scriptName, colNum, 1),sheetPath.getCellData(scriptName, colNum, rowNum));
				// System.out.print(xls.getCellData("CreateProject", colNum, rowNum)+" - ");
			}
			data[rowNum-2][0] = table;
		}
		return data;
	}

	/* Method Name 		:	moduleName
	 * Description		:	This method is used to fetch module from given string
	 */
	public String moduleName(String mN) 
	{
		String moduleName=mN;
		String[] spiltVal=mN.split("\\.");		  
		moduleName=spiltVal[3];
		//System.out.println("Package name--"+moduleName);		   
		return moduleName.toUpperCase();
	}


	/***************************************************************************************************
	 * MethodName 	: getActualFileName 
	 * Description 	: This method is used get actual file name irrespective of appended characters 
	 * Input 		: currentFileName 
	 * Output 		: Converted File Name
	 * 
	 ****************************************************************************************************/
	public static String getActualFileName(String currentName) 
	{
		String convertedFileName = null;
		convertedFileName = StringFunctions.capitalizeFully(currentName, '_');
		return convertedFileName;
	}

	/* Description		:	This method is used to clear the extent report after every run
	 */
	@AfterTest
	public void cleanup()
	{
		reports.flush();
		reports.close();
	}
}