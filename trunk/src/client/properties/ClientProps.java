package client.properties;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import common.properties.PropertiesManager;

public class ClientProps {

    private static final String SERVER_HOST = "serverHost";
    private static final String CLIENT_CAPABILITYS = "capabilitys";
    private static Properties clientProperties;

    public static String getServerHost() {
        if (clientProperties == null) {
            clientProperties = PropertiesManager.loadClientProps();
        }
        return clientProperties.getProperty(SERVER_HOST);
    }

    public static List<String> getCapabilitys() {
        if (clientProperties == null) {
            clientProperties = PropertiesManager.loadClientProps();
        }
        String props = clientProperties.getProperty(CLIENT_CAPABILITYS);
        String[] splitedProps = props.split(";");
        List<String> caps = new ArrayList<String>();
        for (String cap : splitedProps) {
            caps.add(cap);
        }
        return caps;
    }
}
