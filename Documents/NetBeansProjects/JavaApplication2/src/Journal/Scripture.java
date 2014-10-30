package Journal;

/***
 * Scripture class. Primarily used for holding data
 * @author Mark
 */   
public class Scripture {

    String book;
    int chapter;
    int startverse;
    int endverse;
    
    //GETTERS AND SETTERS START
    public String getBook() {
        return book;
    }

    public void setBook(String book) {
        this.book = book;
    }

    public int getChapter() {
        return chapter;
    }

    public void setChapter(int chapter) {
        this.chapter = chapter;
    }

    public int getStartverse() {
        return startverse;
    }

    public void setStartverse(int startverse) {
        this.startverse = startverse;
    }

    public int getEndverse() {
        return endverse;
    }

    public void setEndverse(int endverse) {
        this.endverse = endverse;
    }  
    //GETTERS AND SETTERS END
    
    //CONSTRUCTORS
    public Scripture() {
    }
    
    public Scripture(String book, int chapter, int startverse, int endverse) {
        this.book = book;
        this.chapter = chapter;
        this.startverse = startverse;
        this.endverse = endverse;
    }
    
    public Scripture(String book, int chapter, int startverse) {
        this.book = book;
        this.chapter = chapter;
        this.startverse = startverse;
    }

    public Scripture(String book, int chapter) {
        this.book = book;
        this.chapter = chapter;
    }
    //END CONSTRUCTORS
    
    /***
 * display() used for displaying the scripture in book 1:13-14 format
 * @author Mark
 */   
    public String display() {
        String rString = book + " " + chapter;
        
        if(startverse > 0) {
            rString += ":" + startverse;
            
            if (endverse > 0) {
                rString += "-" + endverse;
            }
        }
        return rString;
    }
}
