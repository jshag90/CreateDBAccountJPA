package com.ji.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class DataSourceUtil {
	
	public static Properties getDataSourcePropByConfigFile(String dbInfoPath) {
		
		Properties prop = new Properties();
		Properties result = new Properties();
		
		try {
			prop.load(new FileInputStream(dbInfoPath));
			
			result.setProperty("javax.persistence.jdbc.url", prop.getProperty("spring.datasource.url"));
			result.setProperty("javax.persistence.jdbc.driver", prop.getProperty("spring.datasource.driver-class-name"));
			result.setProperty("javax.persistence.jdbc.user", prop.getProperty("spring.datasource.username"));
			result.setProperty("javax.persistence.jdbc.password", prop.getProperty("spring.datasource.password"));
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

}
