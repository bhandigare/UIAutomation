package com.ui.framework.manager;

import com.ui.framework.utility.ConfigFileReader;

public class FileReaderManager {
	
	 private static FileReaderManager fileReaderManager = new FileReaderManager();
	 private static ConfigFileReader configFileReader;
	
	 private FileReaderManager() {}
	
	 public static FileReaderManager getInstance( ) {
	      return fileReaderManager;
	 }
	 
	 public ConfigFileReader getConfigReader() {
		 return (configFileReader == null) ? configFileReader = new ConfigFileReader() : configFileReader;
	 }
	 
}
