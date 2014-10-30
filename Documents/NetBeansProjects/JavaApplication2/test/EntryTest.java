
import Journal.Entry;
import Journal.Scripture;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class EntryTest {
    
    public EntryTest() {
    }

    @Test
    public void basicContentTest() {
        Entry e = new Entry();
        
        String content = "this is content :D";
        
        e.setContent(content);
        content = e.getContent();
        
        Assert.assertEquals(e.getContent(), "this is content :D");
    }
    
    @Test
    public void testEntryConstructor() {
        String content = "Hello World";
        String date = "2016-12-12";
        List<String> topics = new ArrayList<>();
        List<Scripture> scriptures = new ArrayList<>();
        Entry e = new Entry(content, date, topics, scriptures);
        
        Assert.assertEquals(e.getContent(), content);
        Assert.assertEquals(e.getDate(), date);
        Assert.assertEquals(e.getTopics(), topics);
        Assert.assertEquals(e.getScriptures(), scriptures); 
    }
    
    @Test
    public void getEmptyScripturesTest() {
        String date = "2014-15-10";
        Entry e = new Entry("I like food", date);
        
        List<Scripture> list = e.getScriptures();
        
       Assert.assertEquals(list.isEmpty(), true);
    }
    
    @Test
    public void getScripturesTest() {
        String date = "2014-13-10";
        Entry e = new Entry("I like moses 1:39", date);
        Scripture s = new Scripture();
        s.setBook("moses");
        s.setChapter(12);
        s.setStartverse(15);
        List<Scripture> list = new ArrayList<>();
        list.add(s);
        e.setScripture(list);
        
        List<Scripture> newList = e.getScriptures();
        
        Assert.assertEquals(newList.get(0).getBook(), "moses");
        Assert.assertEquals(newList.get(0).getChapter(), 12);
        Assert.assertEquals(newList.get(0).getStartverse(), 15);
    }
    
    @Test
    public void constructorTest() {
        String date = "2014-12-10";
        Entry e = new Entry("this is the content", date);
        
        Assert.assertEquals(e.getContent(), "this is the content");
        Assert.assertEquals(e.getDate(), "2014-12-10");
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
