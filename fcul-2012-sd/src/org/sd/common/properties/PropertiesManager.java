package org.sd.common.properties;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesManager {
	
	private static Properties clientConfig;
	private static final String CLIENT_CONFIG_FILENAME = "client_config.properties";
	
	public static Properties getClientProps(boolean loadFromFile) {
		if (clientConfig == null || loadFromFile) {
			clientConfig = loadProps(CLIENT_CONFIG_FILENAME);
		}
		return clientConfig;
	}
	
	private static Properties loadProps(String filename) {
		Properties props = new Properties();
	    InputStream is = null;
	 
	    // First try loading from the current directory
	    try {
	        File f = new File(filename);
	        is = new FileInputStream( f );
	    }
	    catch ( Exception e ) { is = null; }
	    
	    try {
	        if ( is == null ) {
	            // Try loading from classpath
	            is = PropertiesManager.class.getResourceAsStream(filename);
	        }
	 
	        // Try loading properties from the file (if found)
	        props.load( is );
	        is.close();
	    }
	    catch ( Exception e ) { }
	    
	    return props;
	}
}
