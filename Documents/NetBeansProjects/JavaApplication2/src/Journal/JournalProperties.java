package Journal;

import java.io.IOException;
import java.util.Properties;

/***
 * JournalProperties Class. Used to load the properties file. Journal.getProperties then
 * uses the file paths in the properties file to extract information from those files.
 * @author Mark
 */   

public class JournalProperties {
    public static String scriptures;
    public static String terms;
    
    //called getPropStupid because it was orginally named getProperties, which
    //apparently cannot be used, as it is already a reserved function.
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
