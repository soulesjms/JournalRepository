package Journal;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Jonny
 */
public class Display {
    Map<String, Entry> entries = new TreeMap<>();
    
    public Display(Map<String, Entry> entries) {
        this.entries = entries;
    }

    public Map<String, Entry> getEntries() {
        return entries;
    }

    public void setEntries(Map<String, Entry> entries) {
        this.entries = entries;
    }
    
    public Display() {
    }
    
    public final void display() {
        System.out.println("Journal Display:");
        List<String> keys = new ArrayList<>(entries.keySet());
        
        for (String key : keys) {
            System.out.println(entries.get(key).display() + "\n");
        }
    }
    
    public final void displayBookReferences() {
        HashMap<String, List<String>> bookMap = new HashMap<>();
        
        System.out.println("Scripture References:");
        
        for (Map.Entry<String, Entry> entry : entries.entrySet()) {
            String date = entry.getKey();
            Entry temp = entry.getValue();
            
            List<String> booksContained = temp.generateScriptureList();
            
            for (String bookName : booksContained) {
                List<String> dates;
                
                if (bookMap.get(bookName) == null) {
                    dates = new ArrayList<>();
                } else {
                    dates = bookMap.get(bookName);
                }
                
                if(!dates.contains(date)) {
                    dates.add(date);
                }
                
                bookMap.put(bookName, dates);
            }
        }
        displayMap(bookMap);
    }
    
    public final void displayTopicReferences() {
        HashMap<String, List<String>> topicMap = new HashMap<>();
        
        System.out.println("Topic References:");
        
        for (Map.Entry<String, Entry> entry : entries.entrySet()) {
            String date = entry.getKey();
            Entry temp = entry.getValue();
            
            List<String> topicsContained = temp.generateTopicList();
            
            for (String topic : topicsContained) {
                List<String> dates;
                
                if (topicMap.get(topic) == null) {
                    dates = new ArrayList<>();
                } else {
                    dates = topicMap.get(topic);
                }
                
                if (!dates.contains(date)) {
                    dates.add(date);
                }
                
                topicMap.put(topic, dates);
            }
        }
        displayMap(topicMap);
    }
    
    private final void displayMap(HashMap<String, List<String>> input) {
        ArrayList<String> keys = new ArrayList<>(input.keySet());
        Collections.sort(keys);
        
        for (String key : keys) {
            System.out.println(key + ":");
            
            List<String> dates = input.get(key);
            Collections.sort(dates);
            
            for (String date : dates) {
                System.out.println("\t" + date);
            }
        }
    }
}