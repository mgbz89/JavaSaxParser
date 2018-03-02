/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javasaxparser;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author MatthewBarber
 */
public class ReadXMLFile {
    
    private static XMLNode root;
    private static Stack<XMLNode> stack;
    private static XMLNode currentNode;
    
    public static XMLNode loadParser (File xmlFile) throws Exception {
        
        try {
            SAXParserFactory parserFactory = SAXParserFactory.newInstance();
            SAXParser parser = parserFactory.newSAXParser();
            
            DefaultHandler handler = new DefaultHandler() {
                @Override
                public void startDocument() throws SAXException{
                    root = null;
                    stack = new Stack<>();
                }
                
                @Override
                public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException{
                    XMLNode node = new XMLNode();
                    node.name = qName;
                    
                    node.attributes = new HashMap();
                    int size = attributes.getLength();
                    
                    for(int i = 0; i < size; i++){
                        node.attributes.put(attributes.getQName(i), attributes.getValue(i));
                    }
                    stack.push(node);
                    
                    if(currentNode != null){
                        if(currentNode.properties == null){
                            currentNode.properties = new ArrayList();
                        }
                        currentNode.properties.add(node);
                    }
                    
                    currentNode = node;
                }

                @Override
                public void endElement(String uri, String localName,String qName) throws SAXException {
                    XMLNode popped = stack.pop();
                    if(popped != null){
                        if(stack.empty()){
                            root = popped;
                            currentNode = null;
                        } else {
                            currentNode = stack.peek();
                        }
                    }
                }

                @Override
                public void characters(char ch[], int start, int length) throws SAXException {
                    currentNode.content = new String(ch, start, length);
                }
                
            };
            
            parser.parse(xmlFile.getAbsolutePath(), handler);
            
        }
        catch (Exception e) {
            throw e;
        }
        
        return root;
    }
}
