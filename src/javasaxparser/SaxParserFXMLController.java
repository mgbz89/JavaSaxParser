/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javasaxparser;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author MatthewBarber
 */
public class SaxParserFXMLController implements Initializable {
    
    private Stage stage;
    @FXML
    private TextArea textArea = new TextArea();
    public void ready(Stage stage){
        this.stage = stage;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    @FXML
    public void handleOpenFile(ActionEvent event){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open XML File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML Files (*.xml)", "*.xml"));
        File file = fileChooser.showOpenDialog(stage);
        
        try {
            XMLNode root = ReadXMLFile.loadParser(file);
            textArea.appendText(xmlTreetoString(root));
            
        } catch (Exception ex) {
            Logger.getLogger(SaxParserFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private String xmlTreetoString(XMLNode root){
        
        if(root == null)
            return null;
        String string = printXMLNode(root) + "\n";
        
        for(XMLNode property : root.properties){
            string += xmlTreetoString(property);
        }
        
        return string;       
    } 
    
    private String printXMLNode(XMLNode node){
        
        String string = node.name + ": ";
        if(node.content != null || node.content != "\n"){
            string += node.content;
        }
        else{
            string += " null";
        }
        if(!node.attributes.isEmpty()){
            string +=  "Attributes(";
            for(String key : node.attributes.keySet()){
                string += key + " : " + node.attributes.get(key) + ", ";
            }
            string += ")";
        }
        return string;
    }
}
