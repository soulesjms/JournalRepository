package Journal;

public class Scripture {

    String book;
    int chapter;
    int startverse;
    int endverse;
    
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
}
