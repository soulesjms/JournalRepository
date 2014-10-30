import Journal.Display;
import Journal.Entry;
import java.util.Map;
import java.util.TreeMap;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class DisplayTest {

    public DisplayTest() {
    }
    
    @Test
    public void DisplayTest() {
        Display d = new Display();
        Map<String, Entry> entries = new TreeMap<>();
        String content = "Hello World";
        String date = "2013-12-10";
        Entry entry = new Entry(content, date);
        entries.put(content, entry);
        d.setEntries(entries);
        
        Assert.assertEquals(d.getEntries().keySet(), entries.keySet());
        Assert.assertEquals(d.getEntries().values(), entries.values());
    }
    
    

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }
}
