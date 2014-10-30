package Journal;

import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/***
 * Journal Class. This is where all the information is stored into the sub-classes for journal. Also
 * this is where reading and getting of files happens.
 * 
 * IMPORTANT NOTE: I fully acknowledge that I collaborated with several individuals who are also in 
 * CS246, namely Tyson Graham and other collaborators in the group. I did use many of my own methods and
 * organization of code. Code that was done as a group (and as such our code will be similar or the same):
 * 
 * ScriptureFinder class, 
 * Display class, 
 * JournalProperties class.
 * 
 * Other classes I personally changed much of what the group did. 
 * @author Mark
 */
public class Journal {
    String filename;
    List<String> booksToFind = new ArrayList<>();
    Map<String, List<String>> termsToFind = new HashMap<>();
    Map<String, Entry> entries = new TreeMap<>();
    
    //GETTERS AND SETTERS START HERE
    public String getFilename() {
        return filename;
    }
    
    public void setFilename(String filename) {
        this.filename = filename;
    }
    
    public void setBooksToFind(List<String> booksToFind) {
        this.booksToFind = booksToFind;
    }

    public void setTermsToFind(Map<String, List<String>> termsToFind) {
        this.termsToFind = termsToFind;
    }

    public void setEntries(Map<String, Entry> entries) {
        this.entries = entries;
    }

    public List<String> getBooksToFind() {
        return booksToFind;
    }

    public Map<String, List<String>> getTermsToFind() {
        return termsToFind;
    }
    
    public Map<String, Entry> getEntries() {
        return entries;
    }
    //GETTERS AND SETTERS END
    
 /***
 * Main: was used before the GUI was implemented, still runs and does many of the same functions as
 * the current gui has. 
 * @author Mark
 */
    public static void main(String[] args) throws FileNotFoundException, Exception {
        if (args.length >= 1) { //if there are arguments
            Journal j = new Journal();
            j.getProperties();
            j.readFile(args[0]);
            j.writeToText(args[1]);
            j.writeToXML(args[2]);
            j.display();
        } else {
            System.out.println("Please provide a filename.");
        }
    }
    //enters a new entry into the entries map
    public void addEntry(Entry entry, String date) {
        entries.put(date, entry);
    }
 
 /***
 * Determines whether an xml file or txt file is to be written, then calls respective functions.
 * @author Mark
 */   
    public void writeFile(String input) {
        try {
            String[] parts = input.split("\\.");
            if(parts[1].equals("xml")) {
                writeToXML(input);    
            } else if (parts[1].equals("txt")) {
                writeToText(input);
            }
                
        } catch (Exception e) {
            System.err.println("unable to write file");
        }
    }
 /***
 * gets the properties file and calls readTerms and readScriptures functions
 * and passes those functions the file paths.
 * @author Mark
 */   
    public void getProperties() {
        try {
        JournalProperties jProp = new JournalProperties();
        
        jProp.getPropStupid();
        
        readTermsFile(jProp.terms);
        readScriptureFile(jProp.scriptures);

        } catch (IOException ex) {
            Logger.getLogger(Journal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
 /***
 * creates a ReadXMLFile instance and calls its readFile function.
 * @author Mark
 */   
    public void readFile(String fileName) throws Exception {
        System.out.println("in readFile");
        ReadXMLFile readIn = new ReadXMLFile(fileName, termsToFind);
        readIn.readFile();
        entries = readIn.getMap();
    }
    
 /***
 * Displays all entries and separates them by a line. Not used in the gui.
 * @author Mark
 */
    public void display() {
        Display disp = new Display(entries);
        disp.displayBookReferences();
        System.out.println("-------------------------");
        disp.displayTopicReferences();
    }
    
 /***
 * Reads the terms file from the properties file, and puts the terms and synonyms 
 * into a map of terms and synonyms.
 * @author Mark
 */   

    private void readTermsFile(String termsFile) throws IOException {
        FileReader FR = new FileReader(termsFile);
        BufferedReader BR = new BufferedReader(FR);

        String line = BR.readLine();
        List<String> termLines = new ArrayList<>();
        List<String> synonyms = new ArrayList<>();
        
        while (line != null) {
            termLines.add(line);
            line = BR.readLine();
        }
        for (String temp : termLines) {
            String[] termParts = temp.split(":");
            String termKey = termParts[0];
            String[] temps = termParts[1].split(",");
            
            for (String temp2 : temps) {
                synonyms.add(temp2);
            }
            termsToFind.put(termKey, synonyms);
            synonyms = new ArrayList<>();
        }
        BR.close();
    }
 /***
 * Does the same as readTopics file, only with scriptures file instead.
 * @author Mark
 */
    public void readScriptureFile(String scriptureFile) throws IOException {
        FileReader FR = new FileReader(scriptureFile);
        BufferedReader BR = new BufferedReader(FR);
        
        String line = BR.readLine();
        
        while (line != null) {
            String book = line.split(":")[0].trim();
            booksToFind.add(book);
            line = BR.readLine();
        }
        BR.close();
    }
 /***
 * Writes the journal to a txt file. 
 * @author Mark
 */
    public void writeToText(String fileName) throws IOException {
        BufferedWriter BW;
        File fout = new File(fileName);
        BW = new BufferedWriter(new FileWriter(fout));
        
        System.out.println("Writing Journal to text file: " + fileName);
        
        int counter = 0;
        
        for (Map.Entry<String, Entry> entry : entries.entrySet()) {
            String key = entry.getKey();
            Entry value = entry.getValue();
            
            System.out.println(key + ": " + value.getContent());
            
            if (counter == 0) {
                BW.write("-----\n");
                counter++;
            } else {
                BW.write("\n-----\n");
            }
            BW.write(key + "\n\n");
            BW.write(value.getContent() + "\n");
        }
        System.out.println("File written");
        BW.close();
    }
 /***
 * writes the journal to XML file, separating all of the entries and their elements and attributes.
 * @author Mark
 */
    private void writeToXML(String filename) throws Exception{
        System.out.println("Building document");
        
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        
        Document doc = builder.newDocument();
        
        // Root element
        Element rootElement = doc.createElement("journal");
        doc.appendChild(rootElement);
        
        List<String> entryKeys = new ArrayList<>(getEntries().keySet());
        for (String entryKey : entryKeys) {
            // The entry we are working with
            Entry currentEntry = getEntries().get(entryKey);
            
            // Entry element
            Element entryEle = doc.createElement("entry");
            rootElement.appendChild(entryEle);
            
            // Add Entry Date
            Attr attr = doc.createAttribute("date");
            attr.setValue(entryKey);
            entryEle.setAttributeNode(attr);
            
            // Scripture Time
            List<Scripture> scriptures = currentEntry.getScriptures();
            for (Scripture scripture : scriptures) {
                // Scripture element
                Element scriptureEle = doc.createElement("scripture");
                entryEle.appendChild(scriptureEle);
                
                // Add Scripture's Book
                Attr book = doc.createAttribute("book");
                book.setValue(scripture.getBook());
                scriptureEle.setAttributeNode(book);
                // Add Scripture's Chapter
                Attr chapter = doc.createAttribute("chapter");
                chapter.setValue(Integer.toString(scripture.getChapter()));
                scriptureEle.setAttributeNode(chapter);
                // Add Scripture's Book
                Attr startverse = doc.createAttribute("startverse");
                startverse.setValue(Integer.toString(scripture.getStartverse()));
                scriptureEle.setAttributeNode(startverse);
                // Add Scripture's Book
                Attr endverse = doc.createAttribute("endverse");
                endverse.setValue(Integer.toString(scripture.getEndverse()));
                scriptureEle.setAttributeNode(endverse);
            }
            // Topic time
            List<String> topics = currentEntry.getTopics();
            for (String topic : topics) {
                // Topic element
                Element topicEle = doc.createElement("topic");
		topicEle.appendChild(doc.createTextNode(topic));
                entryEle.appendChild(topicEle);
            }
            
            // Content
            Element contentEle = doc.createElement("content");
            contentEle.appendChild(doc.createTextNode(currentEntry.getContent()));
            entryEle.appendChild(contentEle);
        }
        // write the content into xml file
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(filename));
        
        // Output to console for testing
        // StreamResult result = new StreamResult(System.out);
        
        transformer.transform(source, result);
        
        System.out.println("File saved!");
    }
}