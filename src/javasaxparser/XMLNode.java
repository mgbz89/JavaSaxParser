/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javasaxparser;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author MatthewBarber
 */
public class XMLNode {
    
    String name = "";
    String content = "";
    ArrayList<XMLNode> properties = new ArrayList<>();
    HashMap<String, String> attributes;
}
