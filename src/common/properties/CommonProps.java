package common.properties;

import java.util.Properties;


public class CommonProps {
	
	private static final String SERVER_HOST = "serverHost";
	private static final String SERVER_PORT = "serverPort";
	
	private static Properties clientProperties;
	
	public static String getServerHost() {
		if (clientProperties == null) {
			clientProperties = PropertiesManager.loadCommonProps();
		}
		return clientProperties.getProperty(SERVER_HOST);
	}
	
	public static int getServerPort() {
		if (clientProperties == null) {
			clientProperties = PropertiesManager.loadCommonProps();
		}
		return Integer.parseInt(clientProperties.getProperty(SERVER_PORT));
	}
	
}
