package com.ProjecName.base;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

import java.io.FileInputStream;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class GlobalVariables extends GlobalConstants
{

	public static Properties OR; 
	public static Properties WebOR; 
	public static Properties CONFIG; 
	public static FileInputStream fIS;
	public static FileInputStream fIS1;
	public static AppiumDriver <MobileElement> driver ;
	public static WebDriver WebD;
	public static String url=null;
	public static String url2=null;
	public static String runOn=null;
	public static DesiredCapabilities capabilities;
	public static CustomVerification cverify = new CustomVerification();
	public static ExtentReports reports;
	public static ExtentTest testinfo;
	public static ExtentHtmlReporter htmlReporter;
	
	public static String statusVal;
	public static String temp;
	public static Logger APPICATION_LOGS = Logger.getLogger(Base.class.getName());
	public static String errormsgVal;
}
