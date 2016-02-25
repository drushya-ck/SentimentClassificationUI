/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import classify.AllClassification;
import classify.Classification;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import preprocess.Preprocessing;
import train.InformationGainCalculation;

/**
 *
 * @author wainwetun
 */
public class ClassifyJPanel extends javax.swing.JPanel {
    private String filename = "review.txt";

    /**
     * Creates new form ClassifyJPanel
     */
    public ClassifyJPanel() {
        initComponents();
        jtaResult.setEnabled(false);
       
    }
    
    public void saveReview(String review){
         try{
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(new File(filename))));
            pw.write(review.toString());
            pw.close();
         }
         catch(IOException ex){
             System.out.print(ex.getMessage());
         }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jtaResult = new javax.swing.JTextArea();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtaComment = new javax.swing.JTextArea();
        jBClassify = new javax.swing.JButton();
        jbAnalyze = new javax.swing.JButton();

        jtaResult.setColumns(20);
        jtaResult.setRows(5);
        jScrollPane2.setViewportView(jtaResult);

        jtaComment.setColumns(20);
        jtaComment.setRows(5);
        jScrollPane1.setViewportView(jtaComment);

        jBClassify.setText("Classify!");
        jBClassify.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBClassifyActionPerformed(evt);
            }
        });

        jbAnalyze.setText("Analyze Performance");
        jbAnalyze.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbAnalyzeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 482, Short.MAX_VALUE)
                    .addComponent(jScrollPane2))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jbAnalyze)
                .addGap(18, 18, 18)
                .addComponent(jBClassify)
                .addGap(18, 18, 18))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBClassify)
                    .addComponent(jbAnalyze))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE)
                .addGap(10, 10, 10))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jBClassifyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBClassifyActionPerformed
        // TODO add your handling code here:
        long startTime = new Date().getTime();
        this.saveReview(jtaComment.getText().toLowerCase());
        Preprocessing preprocess = new Preprocessing();
        preprocess.posTagging(filename, filename);

        StringBuilder output = new StringBuilder("POS tagging ..\n");
        jtaResult.setText(output.toString());
        
        output.append("Adjectives.. \n");
        output.append(preprocess.getAdj() + "\n\n");
        jtaResult.setText(output.toString());
        
        output.append("Adverbs.. \n");
        output.append(preprocess.getAdv() + "\n\n");
        jtaResult.setText(output.toString());
        
        output.append("Verbs.. \n");
        output.append(preprocess.getVerb() + "\n");
        jtaResult.setText(output.toString());
        
        Classification classify = new Classification();
        output.append("................................\nSentiment Result: " + classify.classify()+". \n");
        long endTime = new Date().getTime();
        jtaResult.setText(output.toString());
        
        double difference = (endTime - startTime)*0.001;
        output.append("Elapsed time : " + difference + " seconds\n\n");
        jtaResult.setText(output.toString());
        
        
        //OutputJTextArea.setText(text + "\n\n" + "Sentiment Result: " + classify.classify());
    }//GEN-LAST:event_jBClassifyActionPerformed

    private void jbAnalyzeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbAnalyzeActionPerformed
        // TODO add your handling code here:
       
        long startTime = new Date().getTime();
        StringBuilder output = new StringBuilder("Feature Selection by Information Gain .. \n");
        InformationGainCalculation cal = new InformationGainCalculation(0);
        output.append(cal.createTable());
        output.append("\n");
        jtaResult.setText(output.toString());
        
        AllClassification classify = new AllClassification();
        classify.classify();
        output.append(classify.getResults());
        output.append("\n\n");
        jtaResult.setText(output.toString());
        
        long endTime = new Date().getTime();
        long difference = (endTime - startTime)/1000;
        output.append("Elapsed time : " + difference + " seconds\n\n");
        jtaResult.setText(output.toString());
        
        output.append("Evaluation Results ..\n");
        output.append(classify.evaluate());
        jtaResult.setText(output.toString());
        
        
    }//GEN-LAST:event_jbAnalyzeActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBClassify;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton jbAnalyze;
    private javax.swing.JTextArea jtaComment;
    private javax.swing.JTextArea jtaResult;
    // End of variables declaration//GEN-END:variables
}
