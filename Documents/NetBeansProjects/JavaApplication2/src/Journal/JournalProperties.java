package Journal;

import java.io.IOException;
import java.util.Properties;

public class JournalProperties {
    public static String scriptures;
    public static String terms;
    
    public void getPropStupid() throws IOException {
        Properties properties = new Properties();
        String propFile = "Properties.properties";
        try {
            //gets filepath from properties file for scripture and topic
            properties.load(getClass().getResourceAsStream(propFile)); 
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        scriptures = properties.getProperty("scripture");
        terms = properties.getProperty("terms"); 
    }
}
