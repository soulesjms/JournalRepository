
import Journal.Journal;
import Journal.Entry;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class JournalTest {
    
    @Test //Test Journal constructor
    public JournalTest() {
        Journal j = new Journal();
    }
    
    @Test
    public void JournalFileTest() {
        String file = "filePath";
        Journal j = new Journal();
        j.setFilename(file);
        
        Assert.assertEquals(j.getFilename(), "filePath");
    }
    
    @Test //test Journal.getEntries to be null
    public void getEntriesTest() {
        Journal j = new Journal();
        Map<String, Entry> map = null;
        j.setEntries(map);
        
        Assert.assertEquals(j.getEntries(), null);
    }
    
    @Test //test Journal.EntriesByDate(Date)
    public void booksToFindTest() {
        Journal j = new Journal();
        List<String> books = new ArrayList<>();
        books.add("Moses");
        books.add("Abraham");
        books.add("Mormon");
        j.setBooksToFind(books);
        
        Assert.assertEquals(j.getBooksToFind().get(0), "Moses");
        Assert.assertEquals(j.getBooksToFind().get(1), "Abraham");
        Assert.assertEquals(j.getBooksToFind().get(2), "Mormon");
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
