package com.ui.framework.utility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import org.apache.log4j.Logger;

import com.ui.framework.enums.DriverType;
import com.ui.framework.enums.EnvironmentType;


public class ConfigFileReader {
	public static final Logger log = Logger.getLogger(ConfigFileReader.class.getName());
	private Properties properties;
	String path = "src/test/resources";
	File file = new File(path);
	String absolutePath = file.getAbsolutePath();
	private final String propertyFilePath = absolutePath+"/config.properties";

	public ConfigFileReader() {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(propertyFilePath));
			properties = new Properties();
			try {
				properties.load(reader);
			} catch (IOException e) {
				log.error("Exception message : " + e.getMessage());
			}
		} catch (FileNotFoundException e) {
			log.error("Properties file not found at path : " + propertyFilePath+ e.getMessage());
		} finally {
			try {
				if (reader != null)
					reader.close();
			} catch (IOException ignore) {
				log.error("Exception message : " + ignore.getMessage());
			}
		}
	}

	public long getImplicitlyWait() {
		String implicitlyWait = properties.getProperty("implicitlyWait");
		if (implicitlyWait != null) {
			try {
				return Long.parseLong(implicitlyWait);
			} catch (NumberFormatException e) {
				log.error("Not able to parse value : " + implicitlyWait + " in to Long");
			}
		}
		return 30;
	}

	public String getApplicationUrl() {
		String url = properties.getProperty("url");
		if (url != null)
			return url;
		else
			throw new RuntimeException(
					"Application Url not specified in the Configuration.properties file for the Key:url");
	}
	
	public String getOSName() {
		String os = properties.getProperty("os");
		if (os != null)
			return os;
		else
			throw new RuntimeException(
					"OS not specified in the Configuration.properties file for the Key:os");
	}
	
	public String getApplicationUserName() {
		String userName = properties.getProperty("userName");
		if (userName != null)
			return userName;
		else
			throw new RuntimeException(
					"Application Username not specified in the Configuration.properties file for the Key:userName");
	}
	
	public String getApplicationPassword() {
		String password = properties.getProperty("password");
		if (password != null)
			return password;
		else
			throw new RuntimeException(
					"Application password not specified in the Configuration.properties file for the Key:password");
	}

	public DriverType getBrowser() {
		String browserName = properties.getProperty("browser");
		if (browserName == null || browserName.equals("chrome"))
			return DriverType.CHROME;
		else if (browserName.equalsIgnoreCase("firefox"))
			return DriverType.FIREFOX;
		else
			throw new RuntimeException(
					"Browser Name Key value in Configuration.properties is not matched : " + browserName);
	}
	
	public int getRetryCount() {
		String retryCount = properties.getProperty("retrycount");
		return Integer.valueOf(retryCount);
	}

	public EnvironmentType getEnvironment() {
		String environmentName = properties.getProperty("environment");
		if (environmentName == null || environmentName.equalsIgnoreCase("local"))
			return EnvironmentType.LOCAL;
		else if (environmentName.equals("remote"))
			return EnvironmentType.REMOTE;
		else
			throw new RuntimeException(
					"Environment Type Key value in Configuration.properties is not matched : " + environmentName);
	}

}

