package Journal;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class ScriptureFinder {
    
    private String text;
    private ArrayList<Scripture> scriptures;
    
    public void display() {
        for (Scripture reference : scriptures) {
            reference.display();
            System.out.println("");
        }
    }
    
    public ScriptureFinder() {
        text = "";
        scriptures = null;
    }
    
    public ScriptureFinder(String text){
        this.text = text;
        scriptures = new ArrayList<>();
        findScripture();
    }
     
    private void findScripture() { 
        boolean extracted = true;
        
        extractChapterWord();
        
        while (extracted == true) {
            extracted = bbbcvv();
            
            //This series of if statements check for the most complex to the 
            //least complex of possible scripture statements.  When one is 
            //found, then it should break the nest, and the loop should restart
            if (!extracted) {
                extracted = bbbcv();
                
                if (!extracted) {
                    extracted = bbbc();
                    
                    if (!extracted) {
                        extracted = bbcvv();
                        
                        if (!extracted) {
                            extracted = bbcv();
                            
                            if (!extracted) {
                                extracted = bbc();
                                
                                if (!extracted) {
                                    extracted = bcvv();
                                    
                                    if (!extracted) {
                                        extracted = bcv();
                                        
                                        if (!extracted) {
                                            extracted = bc();
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    
    
    private void extractChapterWord() {
        boolean b = true;
        
        while (b) {
            Pattern p = Pattern.compile(getFormat(1));
            Matcher m = p.matcher(text);
            b = m.find();
            //System.out.println(text);
            
            if (b) {
                String tempOld = m.group(1) + " " + m.group(2) + " " + m.group(3);
                String tempNew = m.group(1) + " " + m.group(3);
                    //TODO: Handle a possible bug where a word will show up 
                    //followed by "chapter" and a digit.  This could be done by 
                    //checking the first word against a book name (or the last 
                    //word of a book title.)
                text = text.replace(tempOld, tempNew);
            }
        }
    }
    
    //Extractors: These methods are named cryptically.  b = book, c = chapter, v = verse
    
    /***************************************************************************
     * Look for scriptures in the format similar to:
     * John 1
     **************************************************************************/
    private boolean bc() {
        
        Pattern p = Pattern.compile(getFormat(0));
        Matcher m = p.matcher(getText());
        boolean b = m.find();
        
        if (b) {
            String bookName = m.group(1);
            
            if (bookName.equals("D&C") || bookName.equals("d&c")) {
                bookName = "Doctrine & Covenants";
            }
            
            String ref = m.group(1) + " " + m.group(2);
            Scripture newRef = new Scripture(bookName, Integer.parseInt(m.group(2)));
            scriptures.add(newRef);
            text = text.replace(ref, "");
            
            return true;
        }
        
        return false;        
    }
    
    /***************************************************************************
     * Look for scriptures in the format similar to:
     * John 1:1-2
     **************************************************************************/
    private boolean bcv() {
        
        Pattern p = Pattern.compile(getFormat(2));
        Matcher m = p.matcher(getText());
        boolean b = m.find();
        
        if (b) {
            String bookName = m.group(1);
            
            if (bookName.equals("D&C") || bookName.equals("d&c")) {
                bookName = "Doctrine & Covenants";
            }
            
            String ref = m.group(1) + " " + m.group(2) + ":" + m.group(3);
            Scripture newRef = new Scripture(bookName, Integer.parseInt(m.group(2)), 
                                             Integer.parseInt(m.group(3)));
            scriptures.add(newRef);            
            text = text.replace(ref, "");
            //System.out.println(text);
            
            return true;
        }
        
        return false;
    }
    
    /***************************************************************************
     * Look for scriptures in the format similar to:
     * John 1:1-2
     **************************************************************************/
    private boolean bcvv() {    
        
        Pattern p = Pattern.compile(getFormat(3));
        Matcher m = p.matcher(getText());
        boolean b = m.find();
        
        if (b) {
            String bookName = m.group(1);
            
            if (bookName.equals("D&C") || bookName.equals("d&c")) {
                bookName = "Doctrine & Covenants";
            }
            
            String ref = m.group(1) + " " + m.group(2) + ":" + m.group(3) + "-" + m.group(4);
            Scripture newRef = new Scripture(bookName, Integer.parseInt(m.group(2)),
                                             Integer.parseInt(m.group(3)), Integer.parseInt(m.group(4)));
            scriptures.add(newRef);
            text = text.replace(ref, "");
            
            return true;
        }
        
        return false;
    }
    
    /***************************************************************************
     * Look for scriptures in the format similar to:
     * 1 Nephi 1
     **************************************************************************/
    private boolean bbc() {        
        
        Pattern p = Pattern.compile(getFormat(4));
        Matcher m = p.matcher(getText());
        boolean b = m.find();
        
        if (b) {
            String bookName = m.group(1) + " " + m.group(2);
            String ref = bookName + " " + m.group(3);
            Scripture newRef = new Scripture(bookName, Integer.parseInt(m.group(3)));
            scriptures.add(newRef);
            text = text.replace(ref, "");
            
            return true;
        }
        
        return false;
    }
    
    /***************************************************************************
     * Look for scriptures in the format similar to:
     * 1 Nephi 1:2
     **************************************************************************/
    private boolean bbcv() {        
        
        Pattern p = Pattern.compile(getFormat(6));
        Matcher m = p.matcher(getText());
        boolean b = m.find();
        
        if (b) {
            String bookName = m.group(1) + " " + m.group(2);
            String ref = bookName + " " + m.group(3) + ":" + m.group(4);
            Scripture newRef = new Scripture(bookName, Integer.parseInt(m.group(3)), Integer.parseInt(m.group(4)));
            scriptures.add(newRef);
            text = text.replace(ref, "");
            
            return true;
        }
        
        return false;
    }
    
    /***************************************************************************
     * Look for scriptures in the format similar to:
     * 1 Nephi 1:2-3
     **************************************************************************/
    private boolean bbcvv() {
        
        Pattern p = Pattern.compile(getFormat(7));
        Matcher m = p.matcher(getText());
        boolean b = m.find();
        
        if (b) {
            String bookName = m.group(1) + " " + m.group(2);
            String ref = bookName + " " + m.group(3) + ":" + m.group(4) + "-" + m.group(5);
            Scripture newRef = new Scripture(bookName, Integer.parseInt(m.group(3)),
                    Integer.parseInt(m.group(4)), Integer.parseInt(m.group(5)));
            scriptures.add(newRef);
            text = text.replace(ref, "");
            
            return true;
        }
        
        return false;
    }
    
    /***************************************************************************
     * Look for scriptures in the format similar to:
     * Doctrine & Covenants 1
     **************************************************************************/
    private boolean bbbc() {
        
        Pattern p = Pattern.compile(getFormat(8));
        Matcher m = p.matcher(getText());
        boolean b = m.find();
        
        if (b) {
            String bookName = m.group(1);
            
            if (m.group(2).equals("and")) {
                bookName += " &";
            } else {
                bookName += " " + m.group(2);
            }
            
            bookName += " " + m.group(3);
            String refRemove = m.group(1) + " " + m.group(2) + " " + m.group(3) + " " + m.group(4);
            
            if (bookName.equals("D & C")) {
                bookName = "Doctrine & Covenants";
            }
            
            Scripture newRef = new Scripture(bookName, Integer.parseInt(m.group(4)));
            
            scriptures.add(newRef);
            text = text.replace(refRemove, "");
            
            return true;
        }
        
        return false;
    }
    
    /***************************************************************************
     * Look for scriptures in the format similar to:
     * Doctrine & Covenants 1:2
     **************************************************************************/
    private boolean bbbcv() {
        
        Pattern p = Pattern.compile(getFormat(10));
        Matcher m = p.matcher(getText());
        boolean b = m.find();
        
        if (b) {
            String bookName = m.group(1);
            
            if (m.group(2).equals("and")) {
                bookName += " &";
            } else {
                bookName += " " + m.group(2);
            }
            
            bookName += " " + m.group(3);
            
            String refRemove = m.group(1) + " " + m.group(2) + " " + m.group(3) + " " + m.group(4) + ":" + m.group(5);
            
            if (bookName.equals("D & C")) {
                bookName = "Doctrine & Covenants";
            }
            
            Scripture newRef = new Scripture(bookName, Integer.parseInt(m.group(4)),
                Integer.parseInt(m.group(5)));
            scriptures.add(newRef);          
            text = text.replace(refRemove, "");
            
            return true;
        }
        
        return false;
    }
    
    /***************************************************************************
     * Look for scriptures in the format similar to:
     * Doctrine & Covenants 1:2-3
     **************************************************************************/
    private boolean bbbcvv() {        
        Pattern p = Pattern.compile(getFormat(11));
        Matcher m = p.matcher(getText());
        boolean b = m.find();
        
        if (b) {
            String bookName = m.group(1);
            
            if (m.group(2).equals("and")) {
                bookName += " &";
            } else {
                bookName += " " + m.group(2);
            }
            
            bookName += " " + m.group(3);
            
            String refRemove = m.group(1) + " " + m.group(2) + " " + m.group(3)
                    + " " + m.group(4) + ":" + m.group(5) + "-" + m.group(6);
            
            if (bookName.equals("D & C")) {
                bookName = "Doctrine & Covenants";
            }
            
            Scripture newRef;
            newRef = new Scripture(bookName, Integer.parseInt(m.group(4)),
                    Integer.parseInt(m.group(5)), Integer.parseInt(m.group(6)));           
            
            scriptures.add(newRef);
            text = text.replace(refRemove, "");
            
            return true;
        }
        
        return false;
    }
    
    /***************************************************************************
     * Returns a regex format to search for scripture formats
     **************************************************************************/
    private String getFormat(int num) {
		String[] condition = new String[12];
		
		condition[0] = "([Dd]&[Cc]||\\w+||[Dd]oc.\\s&\\s[Cc]ov.)\\s(\\d+)"; 		
		condition[1] = "([Dd]&[Cc]||\\w+||[Dd]oc.\\s&\\s[Cc]ov.)\\s([Cc]hapter)\\s(\\d+)";
		condition[2] = "([Dd]&[Cc]||\\w+||[Dd]oc.\\s&\\s[Cc]ov.)\\s(\\d+):(\\d+)"; 						
		condition[3] = "([Dd]&[Cc]||\\w+||[Dd]oc.\\s&\\s[Cc]ov.)\\s(\\d+):(\\d+)-(\\d+)";				
		
		condition[4] = "(\\d)\\s(\\w+)\\s(\\d+)"; 						
		condition[5] = "(\\d)\\s(\\w+)\\s([Cc]hapter)\\s(\\d+)";
		condition[6] = "(\\d)\\s(\\w+)\\s(\\d+):(\\d+)"; 				
		condition[7] = "(\\d)\\s(\\w+)\\s(\\d+):(\\d+)-(\\d+)";
		
		condition[8] = "(\\w+)\\s([^.,&&[\\p{Punct}]]|of|and)\\s(\\w+)\\s(\\d+)";		
		condition[9] = "(\\w+)\\s([^.,&&[\\p{Punct}]]|of|and)\\s(\\w+)\\s([Cc]hapter)\\s(\\d+)";
		condition[10] = "(\\w+)\\s([^.,&&[\\p{Punct}]]|of|and)\\s(\\w+)\\s(\\d+):(\\d+)";
		condition[11] = "(\\w+)\\s([^.,&&[\\p{Punct}]]|of|and)\\s(\\w+)\\s(\\d+):(\\d+)-(\\d+)";
	          
		switch (num) {
			case 0:
				return condition[0];
			case 1:
				return condition[1];
			case 2:
				return condition[2];
			case 3:
				return condition[3];
			case 4:
				return condition[4];
			case 5:
				return condition[5];
			case 6:
				return condition[6];
			case 7:
				return condition[7];	
			case 8:
				return condition[8];
			case 9:
				return condition[9];
			case 10:
				return condition[10];
			case 11:
				return condition[11];
			default:
				return null;
		}
	}

    /**
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * @param text the text to set
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * @return the scriptures
     */
    public List<Scripture> getScriptures() {        
        return scriptures;
    }

    /**
     * @param scriptures the scriptures to set
     */
    public void setScriptures(ArrayList<Scripture> scriptures) {
        this.scriptures = scriptures;
    }
}
