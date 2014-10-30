import Journal.Scripture;
import org.testng.Assert;
import static org.testng.Assert.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ScriptureTest {
    
    public ScriptureTest() {
    }
    
    @Test
    public void ScriptureSettersAndGettersTest() {
        Scripture s = new Scripture();
        s.setBook("Moses");
        s.setChapter(3);
        s.setStartverse(19);
        
        Assert.assertEquals(s.getBook(), "Moses");
        Assert.assertEquals(s.getChapter(), 3);
        Assert.assertEquals(s.getStartverse(), 19);     
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
