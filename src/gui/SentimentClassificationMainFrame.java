/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;


/**
 *
 * @author wainwetun
 */
public class SentimentClassificationMainFrame extends JFrame{
    private SelectionJPanel selectionPanel;
    private JPanel trainingPanel;
    private JPanel classifyPanel;
    private JFrame mainFrame;
    
    public SentimentClassificationMainFrame(){
        mainFrame = new JFrame("Sentiment Classification");
        selectionPanel = new SelectionJPanel(this);
        trainingPanel = new TrainingJPanel();
        classifyPanel = new ClassifyJPanel();
        
        mainFrame.setLayout(new BorderLayout());
        mainFrame.add(selectionPanel, BorderLayout.NORTH);
        getContent();
        mainFrame.pack();
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLocationRelativeTo(null);
    }
    
    public void show(){
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SentimentClassificationMainFrame().mainFrame.setVisible(true);
            }
        });
    }
    
    public void updateContent(){
        getContent();
        mainFrame.pack();
        mainFrame.validate();
    }
    
    private void getContent(){
        boolean isTraining = selectionPanel.isTraining();
        if(isTraining){
            mainFrame.remove(classifyPanel);
            mainFrame.add(trainingPanel, BorderLayout.CENTER);
        }
        else{
            mainFrame.remove(trainingPanel);
            mainFrame.add(classifyPanel, BorderLayout.CENTER);
        }
    }
    
}
