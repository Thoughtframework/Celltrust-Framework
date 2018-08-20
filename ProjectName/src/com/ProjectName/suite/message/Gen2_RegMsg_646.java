package com.ProjectName.suite.message;

import java.net.MalformedURLException;
import java.util.Hashtable;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

import com.ProjecName.base.Keywords;

/* File Name    : message_643
 * Test Case #  : test_message_643
 * Author       : Srilekha
 * Date created : 7th Dec 2016
 * Date Modified: 7th DEc 2016
 * Description  : Verify that the most recent message of the conversation is displayed on the message screen
 */
public class Gen2_RegMsg_646 extends Keywords
{
	/* Test Case #  : test_message_643
	 * Author       : Srilekha
	 * Date created : 7th Dec 2016
	 * Date Modified: 7th DEc 2016
	 * Description  : Verify that the most recent message of the conversation is displayed on the message screen
	 */
	@Test(dataProvider="ExcelData",groups={"Major"},priority=1)
	public void test_message_643(Hashtable<String,String> data) throws InterruptedException, MalformedURLException 
	{
		System.out.println(com.ProjecName.base.GlobalConstants.STARTPATTERN1 +this.getClass().getSimpleName()+com.ProjecName.base.GlobalConstants.STARTPATTERN2);
		//Load driver capabilities
		Thread.sleep(80000);
		
		WebD.findElement(By.xpath("//*[@type='email']")).sendKeys("sri88241@gmail.com");
		
		System.out.println(com.ProjecName.base.GlobalConstants.ENDPATTERN1 +this.getClass().getSimpleName()+com.ProjecName.base.GlobalConstants.ENDPATTERN2);
		
		//printing the error msgs
		cverify.checkForVerificationErrors();
	}
}
