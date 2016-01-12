/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sentimentclassificationui;

import gui.ClassifyJPanel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JFrame;

/**
 *
 * @author wainwetun
 */
public class TestClass {
    
    public static void main(String[] args){
        JFrame frame = new JFrame();
        frame.setLayout(new BorderLayout());
        frame.add(new ClassifyJPanel(), BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
        //frame.se
        
    }
    
    
}
