package com.ProjecName.base;

import org.testng.Assert;
import org.testng.Reporter;



/********************************************************************************************
 *		ClassName					:	CustomVerification 
 *		Description					:	This class contains methods for handling verification errors
 *
 *********************************************************************************************/
public class CustomVerification extends GeneralKeywords
{
	public  static StringBuffer verificationErrors;
	public static String errormsg=null;
	public static int iterationVal;


	public CustomVerification()
	{
		verificationErrors = new StringBuffer();
	}

	public  void clearVerificationErrors() 
	{
		verificationErrors = new StringBuffer();

	}

	/*********************************************************************************
	 * 	MethodName				: 	assertEqualsTest
	 * 	Description				:	method performs validation by comparing expected with actual values
	 * 	Input					:	keywordName,Expected value & Actual value
	 * 
	 **********************************************************************************/	    
	public static String  assertEqualsTest(Object expected,Object actual)
	{
		String status = null;
		try
		{
			Assert.assertEquals(((String) expected).trim(),((String) actual).trim());

			status="Pass";
		}
		catch (Error e)
		{
			verificationErrors.append(e);				   
			Reporter.log("Fail----"+e.getMessage()+" ", true);
			status="Fail--"+e.getMessage();
		}
		return status;
	}

	/*********************************************************************************
	 * 	MethodName				: 	verifyContent
	 * 	Description				:	This method is used to capture failures during execution
	 * 	Input					:	Boolean values(true/false),msg to be thrown with error
	 * 
	 **********************************************************************************/	   
	public static void verifyContent(Boolean b, String msg) 
	{
		try 
		{				
			Assert.fail(msg);
		} 
		catch (Error e) 
		{			
			errormsg=msg;
			verificationErrors.append(e);
			Reporter.log(msg +"");
		} 
	}

	/**************************************************************************************
	 * 	MethodName			: 	checkForVerificationErrors
	 * 	Description			:	This method is checks for any verification failures & tracks it at the end
	 * 				  	 
	 **************************************************************************************/	   
	public void checkForVerificationErrors() 
	{
		String verificationErrorString = verificationErrors.toString();
		// Clear Verification Errors so that it is ready to test new verifications
		clearVerificationErrors();
		if (!"".equals(verificationErrorString))
			fail(verificationErrorString);
	}

	public static void fail(String message) 
	{
		Assert.fail(message);
	}

	public static String  assertEqualsTestString(Object expected,Object actual,String SuccessMsg)
	{
		String status = null;
		try
		{
			Assert.assertEquals((String) expected,(String) actual);
			status="Pass";
			System.out.println(SuccessMsg);
		}
		catch (Error e)
		{
			verificationErrors.append(e);       
			Reporter.log("Fail----"+e.getMessage()+" ", true);
			status="Fail--"+e.getMessage();
		}
		return status;
	}

	public static String  assertEqualsTestint(Object expected,Object actual,String SuccessMsg)
	{
		String status = null;
		try
		{
			Assert.assertEquals((int) expected,(int) actual);
			status="Pass";
			System.out.println(SuccessMsg);
		}
		catch (Error e)
		{
			verificationErrors.append(e);       
			Reporter.log("Fail----"+e.getMessage()+" ", true);
			status="Fail--"+e.getMessage();
		}
		return status;
	}	 
}