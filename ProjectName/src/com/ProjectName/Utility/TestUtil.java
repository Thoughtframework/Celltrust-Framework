package com.ProjectName.Utility;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.Reporter;

import com.ProjecName.base.Base;
import com.ProjecName.base.CustomVerification;
import com.ProjecName.base.GlobalConstants;
import com.ProjecName.base.GlobalVariables;

/********************************************************************************************
 *		ClassName				:	TestUtil
 *		Description				:	This class contains all Utility functions used across framework
 *********************************************************************************************/
public class TestUtil extends Base
{	

	/*********************************************************************************************
	 *		MethodName			:	now
	 *		Description			:	Method which returns current  date and time
	 *		Input				:	Date in specific format
	 *		Output				: 	Current time in given format
	 **********************************************************************************************/
	public static String now(String dateFormat) 
	{
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		// fetch the value based on format specified
		return sdf.format(cal.getTime());
	}

	/***************************************************************************************************
	 * MethodName 		: errormsgReporter 
	 * Description 		: This method is track custom error & exception error & print either of them 
	 * Input 			: Custom Error Msg , Exception Error msg
	 * Output 			: Prints the error in report, console & log files
	 ****************************************************************************************************/
	public static void errormsgReporter(String custErrMsg, String exceptionErrmsg) 
	{
		if (custErrMsg != null) 
		{
			TestUtil.custReporter(custErrMsg);
			GlobalVariables.APPICATION_LOGS.error(custErrMsg);
			GlobalVariables.errormsgVal = custErrMsg;
			// throwAssertionFailure(custErrMsg);
			CustomVerification.verifyContent(false, custErrMsg);
		} 
		else 
		{
			TestUtil.custReporter(exceptionErrmsg);
			GlobalVariables.APPICATION_LOGS.error(exceptionErrmsg);
			GlobalVariables.errormsgVal = exceptionErrmsg;
			// throwAssertionFailure(exceptionErrmsg);
			CustomVerification.verifyContent(false, custErrMsg);
		}
	}

	/*********************************************************************************************
	 *		MethodName		:	takeScreenShot
	 *		Description		:	Method which takes screen shot & stores in path specified
	 *		Input			:   File name for screen shot 
	 *		OutPut			: 	screen shot with given file name in given path
	 **********************************************************************************************/	
	public static void takeScreenShot(String filePath) 
	{
		// Take screen shot using driver
		File scrFile = ((TakesScreenshot)GlobalVariables.driver).getScreenshotAs(OutputType.FILE);
		try
		{
			// copy file path 
			FileUtils.copyFile(scrFile, new File(filePath));
		}
		catch (IOException e)
		{
			errormsgReporter("Could not take screen shot as path is wrong",e.getMessage());	    
		}	  
	}  

	/*********************************************************************************************
	 *		MethodName			:	zip
	 *		Description			:	Method which zip's results/reports obtained & stores in path specified
	 **********************************************************************************************/	
	public static void zip(String filepath)
	{
		try
		{
			File inFolder=new File(filepath);
			File outFolder=new File("Reports.zip");
			ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(outFolder)));
			BufferedInputStream in = null;
			byte[] data  = new byte[1000];
			String files[] = inFolder.list();
			for (int i=0; i<files.length; i++)
			{
				in = new BufferedInputStream(new FileInputStream
						(inFolder.getPath() + "/" + files[i]), 1000);  
				out.putNextEntry(new ZipEntry(files[i])); 
				int count;
				while((count = in.read(data,0,1000)) != -1)
				{
					out.write(data, 0, count);
				}
				out.closeEntry();
			}
			out.flush();
			out.close();
		}
		catch(Exception e)
		{			
			errormsgReporter("Could not Zip results",e.getMessage());	    
		} 
	}	


	/***********************************************************************************************
	 *		MethodName			:	scriptExecutionTime
	 *		Description			:	This method returns script execution in terms of hrs:min:sec
	 *		Input				:	startTime & endTime of execution		 
	 *		outPut				:  	Execution time
	 **********************************************************************************************/			
	public static String  scriptExecutionTime(String startTime,String endTime)
	{
		String executedTime=null;
		Date d1 = null;
		Date d2 = null;
		String dateStart = startTime;
		String dateStop =endTime ;

		//HH converts hour in 24 hours format (0-23), day calculation
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		try 
		{
			d1 = format.parse(dateStart);		
			d2 = format.parse(dateStop);					
			long diff = d2.getTime() - d1.getTime();	
			long diffSeconds = diff / 1000 % 60;
			long diffMinutes = diff / (60 * 1000) % 60;
			long diffHours = diff / (60 * 60 * 1000) % 24;
			//long diffDays = diff / (24 * 60 * 60 * 1000);			
			//	System.out.print(diffHours + " hrs:"+diffMinutes + " min:"+diffSeconds + " sec");
			executedTime = diffHours + " hrs:"+diffMinutes + " min:"+diffSeconds + " sec";
		}
		catch (ParseException e) 
		{
			errormsgReporter("Error while calculating time",e.getMessage());	    				
		}
		return executedTime ;
	}

	/*********************************************************************************************
	 *		MethodName			:	runStatusAdd
	 *		Description			:	Method which returns current  date and time
	 *		Input				: status of execution
	 *		Output				: Appended Status
	 **********************************************************************************************/
	public static String runStatusAdd(String status)
	{
		// Append test status
		GlobalVariables.statusVal+=status+",";		  
		return GlobalVariables.statusVal;
	}

	/*********************************************************************************************
	 *		MethodName			:	appenedFileNames
	 *		Description			:	Method which returns current  date and time
	 *		Input				: 	File names for screen shots
	 *		Output				: 	Appended file names
	 **********************************************************************************************/
	public static String appenedFileNames(String fileNames)
	{
		GlobalVariables.temp+=fileNames+"#";
		//			System.out.println(temp );
		return GlobalVariables.temp;
	}

	/*********************************************************************************************
	 *		MethodName			:	getDirectoryPath
	 *		Description			:	This method is used to actual path of file specified
	 *		Input				:   File name 
	 *		Output				:	Actual path of file
	 **********************************************************************************************/
	public static String getDirectoryPath (String path)
	{
		String classpaths=path;
		if (StringUtils.countMatches(classpaths, ":")==1)
		{
			return classpaths.split("com")[0].split(":")[1];
		}
		else
		{
			//System.out.println ("split content -----------------------"+classpaths.split("com")[0].split(":")[1]);
			String sDrive =classpaths.split("com")[0].split(":")[1];
			sDrive = sDrive.replace("//","");
			//System.out.println("Sdrive content is -----------------"+sDrive+":/"+classpaths.split("com")[0].split(":")[2]);
			String SetDrive=sDrive+":/"+classpaths.split("com")[0].split(":")[2];
			String DrivePath=SetDrive.replace("/", "\\");
			return DrivePath;
		}
	}

	/*********************************************************************************************
	 *		MethodName			:	getDirectoryPath
	 *		Description			:	This method is used to actual path of Base class file 
	 *		Output				:	Actual path of file (F or  F:/.../.../full path)
	 * 		Updated 			: Replace added  at same line (7/23/2018).
	 **********************************************************************************************/
	public static String getDirectoryPath ()
	{
		if (StringUtils.countMatches(Base.class.getResource("Base.class").toString(), ":")==1)
		{
			return Base.class.getResource("Base.class").toString().split("com")[0].split(":")[1];
		}
		else
		{
			//System.out.println (Base.class.getResource("Base.class").toString().split("com")[0].split(":")[1]);
			String sDrive = Base.class.getResource("Base.class").toString().split("com")[0].split(":")[1].replace("/","").concat(":/"); // Output : F:/
			//sDrive = sDrive.replace("/","")
			//System.out.println(sDrive+":\\"+Base.class.getResource("Base.class").toString().split("com")[0].split(":")[2]);
			return sDrive + Base.class.getResource("Base.class").toString().split("com")[0].split(":")[2]; // Output : F:/..../..../...
		}
	}
	
	/*********************************************************************************************
	 *		MethodName			:	cleanDBError
	 *		Description			:	This method is used to clean contents of error with space which wil be inserted into database
	 *		Input				:   String which contains error message in case of test failure 
	 *		Output				:   Cleaned string wit no special characters
	 **********************************************************************************************/
	public static String cleanDBError(String stringToClean)
	{
		boolean  errorcheck=false;
		System.out.println("Error to clean is --->"+stringToClean);
		errorcheck=StringUtils.isBlank(stringToClean);
		if( errorcheck==true) 
		{GlobalVariables.APPICATION_LOGS.error("No error to clean");}
		else 
		{
			if(stringToClean.contains("\"")) stringToClean = stringToClean.replace("\"", "");
			if(stringToClean.contains("'")) stringToClean = stringToClean.replace("'", "");
			if(stringToClean.contains("/")) 	stringToClean = stringToClean.replace("/", "");
			if(stringToClean.contains("\\")) stringToClean = stringToClean.replace("\\", "");
			if(stringToClean.contains("{")) stringToClean = stringToClean.replace("{", "");
			if(stringToClean.contains("}")) stringToClean = stringToClean.replace("}", "");	  
			if(stringToClean.contains(",")) stringToClean = stringToClean.replace(",", "");	  
			if(stringToClean.contains("")) stringToClean = stringToClean.replace("", "");	
			if(stringToClean.contains(":")) stringToClean = stringToClean.replace(":", "");	
			if(stringToClean.contains("*")) stringToClean = stringToClean.replace("*", "");	
			if(stringToClean.contains("[")) stringToClean = stringToClean.replace("[", "");	
			if(stringToClean.contains("]")) stringToClean = stringToClean.replace("]", "");	
			if(stringToClean.contains("=")) stringToClean = stringToClean.replace("=", "");	
			if(stringToClean.contains("@")) stringToClean = stringToClean.replace("@", "");	
			System.out.println("Cleaned eror is=="+stringToClean);
		}
		return stringToClean;     
	}


	/*****************************************************************************************************
	 *		MethodName			:	custReporter
	 *		Description			:	This method prints the log to testNG report & console based on settings in config file
	 *		Input				:	Config file setting values Y/N in  reporterLog & consolePrint option
	 *		Output				:	 Print s the contents on console or report based on selections
	 *******************************************************************************************************/
	public static void custReporter(String msg)
	{		 
		String status=GlobalVariables.CONFIG.getProperty(GlobalConstants.REPORTER_LOG);
		//System.out.println("Value in config is --->"+status);
		String msg1=msg;
		String cStatus =GlobalVariables.CONFIG.getProperty(GlobalConstants.CONSOLE_PRINT);
		boolean t=true;
		if(cStatus.equalsIgnoreCase(GlobalConstants.RUNMODE_YES))
		{
			// if console print is set to Y then print on console as well	
			// if set to N , then only print in testNG report
			t=true; 
		}
		else t=false;
		if(status.equalsIgnoreCase(GlobalConstants.RUNMODE_YES) )
		{
			Reporter.log(msg1,t);
		}			
	}

	/******************************************************************************************
	 * 	MethodName			: 	runInEditor
	 * 	Description			:	It fetches value to decide to run scripts in Editor or cmd
	 * 	Input				:   Variable containing value to run in eclipse or cmd
	 * 	Output				:   Based on value , it loads  file to editor bin path or cmd bin path
	 ********************************************************************************************/	   
	public static String runInEditor(String runFromVal)
	{
		String pathValue=null;
		if(runFromVal.equalsIgnoreCase(GlobalConstants.RUN_FROM_ECLIPSE))
		{
			// Run in eclipse class file path
			pathValue=".";
		}
		else if(runFromVal.equalsIgnoreCase(GlobalConstants.RUN_FROM_ANT))
		{
			// run in given classpath
			pathValue=getDirectoryPath();
		}
		return pathValue;
	}

	/*********************************************************************************
	 * 	MethodName			: 	moduleName
	 * 	Description			:	Fetches the module name at run time
	 * 	Input				:	Fully qualified package name
	 * 	Output				:   Module name in uppercase
	 **********************************************************************************/	  
	public String moduleName(String mN) 
	{
		String moduleName=mN;
		String[] spiltVal=mN.split("\\.");		  
		moduleName=spiltVal[3];
		//System.out.println("Package name--"+moduleName);		   
		return moduleName.toUpperCase();
	}
}
