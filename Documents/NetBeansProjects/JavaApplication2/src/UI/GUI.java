package UI;

import Journal.*;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
/***
 * Gui class. This is where the program starts. 
 * @author Mark
 */
public class GUI extends Application {
    private Journal journal;
    Label scriptureLabel = new Label("Scriptures:");
    Label entryDateLabel = new Label("Entry Date:");
    Label editEntryLabel = new Label("Edit Entry:");
    Label topicLabel = new Label("Topics:");
    
    GridPane grid = new GridPane();
    
    ListView<String> dateList = new ListView<>();
    ListView<String> topicList = new ListView<>();
    ListView<String> scriptureList = new ListView<>();
    
    TextArea editEntryArea = new TextArea();
    
    TextField progressBar = new TextField();
    
    Button browseButton = new Button("Browse");
    Button writeFileButton = new Button("Write");
    Button newEntryButton = new Button("New Entry");
    Button updateEntryButton = new Button("Update Entry");
    
    @Override
    public void start(Stage primaryStage) {
        journal = new Journal();
        
        primaryStage.setTitle("Scriptures Application");
        grid.setAlignment(Pos.TOP_CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        
        //LIST OF ENTRY DATES
        grid.add(entryDateLabel, 0, 0, 2, 1);
        grid.add(dateList, 0, 1, 2, 1);
        dateList.setPrefHeight(200);
        
        //AREA TO MAKE/EDIT AN ENTRY
        grid.add(editEntryLabel, 2, 0, 2, 1);
        grid.add(editEntryArea, 2, 1, 2, 1);
        editEntryArea.setPrefHeight(200);
        
        //BUTTON TO LOAD JOURNAL
        grid.add(browseButton, 0, 3);
        //WRITE BUTTON
        grid.add(writeFileButton, 1, 3);
        //NEW ENTRY BUTTON
        grid.add(newEntryButton, 0, 4);
        //UPDATE ENTRY BUTTON
        grid.add(updateEntryButton, 1, 4);
        
        //LIST OF SCRIPTURES
        grid.add(scriptureLabel, 2, 2, 1, 1);
        grid.add(scriptureList, 2, 3, 1, 3);
        
        //LIST OF TOPICS
        grid.add(topicLabel, 3, 2, 1, 1);
        grid.add(topicList, 3, 3, 1, 3);

        grid.add(progressBar, 0, 5, 2, 1);
        
        //WHAT THE BROWSE BUTTON DOES
        browseButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                FileChooser chooser = new FileChooser();
                chooser.getExtensionFilters().addAll(
                        new ExtensionFilter("XML File", "*.xml"),
                        new ExtensionFilter("Text Files", "*.txt"), 
                        new ExtensionFilter("AllFiles", "*.*"));
                File file = chooser.showOpenDialog(primaryStage);
                
                String fileName = file.getPath();
                
                File existDirectory = file.getParentFile();
                chooser.setInitialDirectory(existDirectory);
                progressBar.clear();
                progressBar.appendText("Adding Entries...");
                try {
                    Thread t1 = new Thread(new Runnable(){

                        @Override
                        public void run() {   
                            journal.getProperties();
                            try {
                                journal.readFile(fileName);
                            } catch (Exception ex) {
                                Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            fillDateList();
                        }
                    });
                    t1.start();
                } catch (Exception ex) {
                    Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        //WHAT THE WRITE BUTTON DOES
        writeFileButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                FileChooser chooser = new FileChooser();
                chooser.getExtensionFilters().addAll(
                        new ExtensionFilter("Text Files", "*.txt"), 
                        new ExtensionFilter("XML File", "*.xml"), 
                        new ExtensionFilter("AllFiles", "*.*"));
                
                File file = chooser.showSaveDialog(primaryStage);
                
                if(file != null){
                    File existDirectory = file.getParentFile();
                    chooser.setInitialDirectory(existDirectory);
                }
                
                String fileName = file.getPath();
                
                try {
                    journal.writeFile(fileName);
                } catch (Exception ex) {
                    Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        //WHAT THE NEW ENTRY BUTTON DOES
        newEntryButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {             
                try {
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");//2014-09-18
                    Date date = new Date();
                    String sDate = dateFormat.format(date);
                    String entryContent = "";
                    Entry entry = new Entry(entryContent, sDate);
                    journal.addEntry(entry, sDate);
                    fillDateList();
                } catch (Exception ex) {
                    Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });   
        
        //WHAT THE UPDATE BUTTON DOES
        updateEntryButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {             
                try {
                    String date = dateList.getSelectionModel().getSelectedItem();
                    String content = editEntryArea.getText();
                    Entry entry = new Entry(content, date);
                    journal.addEntry(entry, date);
                } catch (Exception ex) {
                    Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });   
        
        Scene scene = new Scene(grid, 800, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    //POPULATES THE dateList LISTVIEW WITH DATES
    private void fillDateList() {
        ObservableList<String> oList = FXCollections.observableArrayList();
        Map<String, Entry> entries = journal.getEntries();
        
        //GET ALL DATES
        for (Map.Entry<String, Entry> entry : entries.entrySet()) {
            String key = entry.getKey();
            oList.add(key);
        }
        //ADD THEM TO DATELIST
        dateList.setItems(oList);
        clickDate();
    }
    
    public void clickDate() {
        //WHEN YOU CLICK ON A DATE DO THIS
        dateList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                //ACTION HERE
                Map<String, Entry> entries = journal.getEntries();
                Entry entry = entries.get(newValue);
                String content = entry.getContent();
                //EDITING TEXT AREA TO DISPLAY ENTRY CONTENT
                editEntryArea.clear();
                editEntryArea.appendText(content);//this will be entry's content
                editEntryArea.setWrapText(true);
                editEntryArea.getOnInputMethodTextChanged();
                fillTopicList(content);
                fillScriptureList(content);
            }
        });
    }
    
    //FILLS THE TOPIC LISTVIEW
    public void fillTopicList(String text) {
        ObservableList<String> oList = FXCollections.observableArrayList();
        text = text.replaceAll("[^a-zA-Z\\s]", "").replaceAll("\\s+", " ");
        text = text.toLowerCase();
        
        List<String> topics = new ArrayList<>();
        //   Term  List<synonyms>
        Map<String, List<String>> topicsMap = journal.getTermsToFind();
        for (Map.Entry<String, List<String>> entry : topicsMap.entrySet()) {
            //key = terms (aka faith, repentance etc.)
            String key = entry.getKey();
            //value = synonyms (aka believe, forsake, remission, etc.)
            List<String> value = entry.getValue();
            
            for (String topic : value) { //String topic is all values: which is all synonyms
                if (text.contains(topic)) { //if the topic is in the text
                    if (!topics.contains(key)) { //and if the main topic hasn't been added yet
                        topics.add(key); //add it
                        oList.add(key);
                    }
                }
            }
        }
        //System.out.println(topics);
        topicList.setItems(oList);
    }
    
    //FILLS THE SCRIPTURE LIST VIEW
    public void fillScriptureList(String text) {
        text = text.replaceAll("\\s+", " ");
        //text = text.replaceAll("[^a-zA-Z0-9\\s]", "");
        
        ObservableList<String> oList = FXCollections.observableArrayList();
        ScriptureFinder SF = new ScriptureFinder(text);
        
        List<Scripture> scripturesList = SF.getScriptures();

        for (Scripture scripture : scripturesList) {
                String temp = scripture.display();
                oList.add(temp);
            }
        //System.out.println(scripturesList);
        scriptureList.setItems(oList);
    }
    
    //MAIN OF THE GUI
    public static void main(String[] args) {
        launch(args);
    }
    
    //WHEN THE PROGRAM STOPS DO THIS
    public void stop() {
        System.out.println("Application Terminated.");
        System.exit(0);
    }
}