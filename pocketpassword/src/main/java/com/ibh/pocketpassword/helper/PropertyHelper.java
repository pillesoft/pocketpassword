package com.ibh.pocketpassword.helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class PropertyHelper {
	private static final Logger LOG = LoggerFactory.getLogger(PropertyHelper.class);

	private static final Properties PROPERTIES = new Properties();
  private static String propertyFile = null;

  private PropertyHelper() {}
  
	private static void load() {
		try {
			if (StringUtils.isEmpty(propertyFile)) {
				setPropertyFile(PropertyHelper.class.getResource("/application.properties").getPath().toString());
			}
			
			try (InputStream is = new FileInputStream(propertyFile)) {
					PROPERTIES.load(is);
				}
//			} else {
//				
//				try (InputStream is = PropertyHelper.class.getResourceAsStream("/application.properties")) {
//					PROPERTIES.load(is);
//				}
		} catch (IOException ex) {
			LOG.warn("cannot load properties file", ex);
		}
	}

	public static void setPropertyFile(String propertyFile) {
		PropertyHelper.propertyFile = propertyFile;
	}

	public static String getAppDbFolder() {
		if(PROPERTIES.isEmpty()) {
			load();
		}
		return PROPERTIES.getProperty("app.dbfolder");
	}
	
	public static String getAppDbName() {
		if(PROPERTIES.isEmpty()) {
			load();
		}
		return PROPERTIES.getProperty("app.dbname");
	}
	
	public static void setDatabaseName(String dbname) throws IOException {
		
		PROPERTIES.setProperty("app.dbname", dbname);
				
		File propfile = new File(propertyFile);
		try (PrintWriter pw = new PrintWriter(new FileWriter(propfile, false))) {
			PROPERTIES.store(pw, null);
		}
	}
}
