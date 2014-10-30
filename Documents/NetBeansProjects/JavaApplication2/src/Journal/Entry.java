package Journal;

import java.util.ArrayList;
import java.util.List;
 /***
 * Entry class: primarily used as the data holder of all the functions of other classes. 
 * @author Mark
 */   
public class Entry {
    private String content;
    private String date;
    private List<String> topics = new ArrayList<>();
    private List<Scripture> scriptures = new ArrayList<>();

    //DEFAULT CONSTRUCTORS
    public Entry() {
    }

    public Entry(String content, String date, List<String> topics, List<Scripture> scripture) {
        this.content = content;
        this.date = date;
        this.topics = topics;
        this.scriptures = scripture;
    }
    
    public Entry(String content, String date) {
        this.content = content;
        this.date = date;
    }

    //GETTERS AND SETTERS START
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<String> getTopics() {
        return topics;
    }

    public void setTopics(List<String> topics) {
        this.topics = topics;
    }

    public List<Scripture> getScriptures() {
        return scriptures;
    }

    public void setScripture(List<Scripture> scripture) {
        this.scriptures = scripture;
    }
    //GETTERS AND SETTERS END
    
    //ADD TOPIC TO TOPICS LIST
    public void addTopic(String topic) {
        this.topics.add(topic);
    }
    //ADD SCRIPTURE TO SCRIPTURES LIST
    public void addScripture(Scripture scr) {
        this.scriptures.add(scr);
    }
 /***
 * display(): displays entry. (used in Journal's main, not in gui)
 * @author Mark
 */   
    public String display() {
        String rString = "Entry Display:\n" +"Date : " + date + "\n";
        for (Scripture scripture : scriptures) {
            rString = rString + scripture.display();
        }
        for (String topic : topics) {
            rString = rString + topic + " ";
        }
        rString = rString + "\n" + content + "\n";
        return rString;
    }
 /***
 * populates the topics list. It fills the list with terms, and their synonyms.
 * @author Mark
 */   
    public List<String> generateTopicList() {
        List<String> list = new ArrayList<>();
        
        for (String temp : topics) {
            if (!list.contains(temp)) {
                list.add(temp);
            }
        }
        return list;
    }
/***
 * populates the scriptures list. It fills the list with just the book names, not verses.
 * @author Mark
 */   
    public List<String> generateScriptureList() {
        List<String> list = new ArrayList<>();
        
        for (Scripture temp : scriptures) {
            String bookName = temp.getBook();
            
            if (!list.contains(bookName)) {
                list.add(bookName);
            }
        }
        return list;
    }
}