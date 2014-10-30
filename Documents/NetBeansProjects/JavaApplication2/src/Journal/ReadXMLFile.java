package Journal;

import java.io.BufferedReader;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.w3c.dom.NamedNodeMap;
 
 /***
 * ReadXMLFile class. Class for parsing an xml file and putting the data into respective variables.
 * Also can read a text file.
 * @author Mark
 */
public class ReadXMLFile {
    private Map<String, List<String>> termsToFind = new HashMap<>();
    Map<String, Entry> map = new TreeMap<>();
    private String filename;
    
    //constructor with a file and map parameter
    public ReadXMLFile(String file, Map<String, List<String>> terms) {
        this.filename = file;
        this.termsToFind = terms;
    }
    //constructor with no parameters
    public ReadXMLFile(){
    }
    
    //GETTERS AND SETTERS START
    public String getFilename() {
        return filename;
    }

    public Map<String, Entry> getMap() {
        return map;
    }

    public Map<String, List<String>> getTermsToFind() {
        return termsToFind;
    }

    public void setTermsToFind(Map<String, List<String>> termsToFind) {
        this.termsToFind = termsToFind;
    }

    public void setMap(Map<String, Entry> map) {
        this.map = map;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
    //GETTERS AND SETTERS END
    
    /***
 * readFile determines whether a txt file is read or an xml file is read and calls their
 * respective functions to read the two different file types.
 * @author Mark
 */
    public void readFile() throws FileNotFoundException, IOException, InterruptedException {
        String[] parts = filename.split("\\.");
        
        if (parts[1].equals("txt")) {
            System.out.println("Got a txt file!");
            readTxt();
        } else if (parts[1].equals("xml")) {
            System.out.println("Got an xml file!");
            readJournal();
        }
        else {
            System.out.println("ERROR: Invalid file extension.");
        }
    }
 /***
 * reads a txt file and fills a map of dates and entries. (which then is later retrieved by
 * the readFile method in Journal's class.
 * @author Mark
 */
    public void readTxt() throws FileNotFoundException, IOException, InterruptedException {
        BufferedReader fin = new BufferedReader(new FileReader(filename));
        System.out.println("Loading File: \"" + filename  + "\"...");
        
        String line;
        String content = "";
        Entry entry = null;
        int i = 0;
        
        while((line = fin.readLine()) != null) {
            if (line.contains("-----")) {
                i++;
                System.out.println("reading in entry number" + i);
                if (!content.isEmpty()) {
                    entry.setContent(content);
                    map.put(entry.getDate(), entry);
                    content = "";
                    Thread.sleep(2000);
                }
                entry = new Entry();
            } else if (line.contains("201")) {
                entry.setDate(line);
            } else {
                content = content + line;
            }
        }
        entry.setContent(content);
        map.put(entry.getDate(), entry);
    }
 /***
 * Reads a Journal in XML format. It calls a series of functions each designed to read
 * the data from its child elements. (e.g. readJournal gets the root, parseJournal() gets the entries,
 * parseEntries() gets the topics, and parseScripture gets the scriptures)
 * @author Mark
 */
    public void readJournal() {
        try {
            System.out.println("Loading File: \"" + filename + "\"...");
            File JournalXmlFile = new File(filename);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(JournalXmlFile);
            doc.getDocumentElement().normalize();

            //System.out.println(doc.getDocumentElement().getNodeName() + ":" ); //prints out journal:
            //NodeList entryNodes = doc.getElementsByTagName("entry"); // entryNodes = all entries labeled <entry>
            System.out.println("----------------------------");

            Element root = doc.getDocumentElement();
            map = parseJournal(root);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 /***
 * Parses the <journal> element for its child elements <entry>
 * @author Mark
 */
    private Map<String, Entry> parseJournal(Element root) throws InterruptedException{
        Map<String, Entry> entryMap = new HashMap<>();
        NodeList journalNodes = root.getChildNodes();
        int j = 1;
        
        for (int i = 0; i < journalNodes.getLength(); i++) {
            Node journalNode = journalNodes.item(i);
            
            if (journalNode.getNodeType() == Node.ELEMENT_NODE) {
                Element journalElement = (Element) journalNode;
                
                if (journalElement.getNodeName().equals("entry")) {
                    Thread.sleep(500);
                    System.out.println("Reading Entry " + j++ + "....");
                    Thread.sleep(500);
                    Entry e = parseEntry(journalElement);
                    entryMap.put(e.getDate(), e);
                }
            }
        }
        return entryMap;
    }
    /***
 * parses the <entry> nodes for its child elements <topic> and its attributes
 * @author Mark
 */
    private Entry parseEntry(Element rootEntryElement) {
        Entry rEntry = new Entry(); //create new entry
        rEntry.setDate(rootEntryElement.getAttribute("date")); //set date for entry
        NodeList entryNodes = rootEntryElement.getChildNodes(); //create a Node list of everything in <entry>
        int j = 0;
        int k = 0;
        for (int i = 0; i < entryNodes.getLength(); i++) {
            Node entryNode = entryNodes.item(i); //set each tag within <entry> to entryNode
            if (entryNode.getNodeType() == Node.ELEMENT_NODE) { //if it's in a tag
                Element entryElement = (Element) entryNode; //cast to an element
                switch (entryElement.getNodeName()) { //name of tag within <entry>
                    case "scripture":
                        j++;
                        rEntry.addScripture(parseScripture(entryElement));
                        break;
                    case "topic":
                        k++;
                        String tempTopic = entryElement.getTextContent();
                        //This is a topic converter
                        for (Map.Entry<String, List<String>> topic : termsToFind.entrySet()) {
                            String masterTopic = topic.getKey();
                            List<String> slaveTopic = topic.getValue();
                            
                            if (slaveTopic.contains(tempTopic.toLowerCase())) {
                                rEntry.addTopic(masterTopic);
                            }
                        }
                        break;
                    case "content":
                        rEntry.setContent(entryElement.getTextContent().trim().replaceAll("\\n\\s+", "\n"));
                        break;
                }
            }
        }
        System.out.println("Scriptures found: " + j);
        System.out.println("Topics found: " + k);
        return rEntry;
    }
    /***
 * parses the <entry> nodes for its child elements <scripture> and its attributes
 * @author Mark
 */
    private Scripture parseScripture(Element rootScriptureElement) {
        Scripture scripture = new Scripture();
        NamedNodeMap attributes = rootScriptureElement.getAttributes();
        for (int i = 0; i < attributes.getLength(); i++) {
            Node attributeAtI = attributes.item(i);
            switch (attributeAtI.getNodeName()) {
                case "book":
                    scripture.setBook(attributeAtI.getNodeValue());
                    break;
                case "chapter":
                    scripture.setChapter(Integer.parseInt(attributeAtI.getNodeValue()));
                    break;
                case "startverse":
                    scripture.setStartverse(Integer.parseInt(attributeAtI.getNodeValue()));
                    break;
                case "endverse":
                    scripture.setEndverse(Integer.parseInt(attributeAtI.getNodeValue()));
                    break;
            }
        }
        return scripture;
    }
}