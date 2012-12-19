package org.sd.common.properties;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class PropertiesManager {
	
	private static final String CLIENT_CONFIG_FILENAME = "client_config.properties";
	private static final String SERVER_CONFIG_FILENAME = "server_config.properties";
	
	public static Properties getClientProps() {
		return loadProps(CLIENT_CONFIG_FILENAME);
	}
	
	public static boolean saveClientProps(Properties props) {
		return saveProps(props, CLIENT_CONFIG_FILENAME);
	}
	
	public static Properties getServerProps() {
		return loadProps(SERVER_CONFIG_FILENAME);
	}
	
	public static boolean saveServerProps(Properties props) {
		return saveProps(props, SERVER_CONFIG_FILENAME);
	}
	
	@SuppressWarnings("resource")
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
	
	private static boolean saveProps(Properties props, String filename) {
		try {
	        File f = new File(filename);
	        OutputStream out = new FileOutputStream( f );
	        props.store(out, null);
	        out.close();
	        return true;
	    }
	    catch (Exception e ) {
	        return false;
	    }
	}
}
