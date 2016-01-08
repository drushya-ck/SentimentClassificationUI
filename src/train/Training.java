/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package train;

import javax.swing.JOptionPane;

/**
 *
 * @author Wai Nwe Tun
 */
public class Training {
    
    
    public Training(){
        
    }
    
    public String checkPosNeg(){
        boolean flag = true;
        String output = "";
        while(flag){
            String sentiment = JOptionPane.showInputDialog( "This given review is pos or neg? If it's positive, type 1. Otherwise type -1.");
            if(sentiment.equals("1")){
               flag = false;
               InformationGainCalculation cal = new InformationGainCalculation(true);
               output = cal.createTable();
            }
            else if(sentiment.equals("-1")){
                flag = false;
                InformationGainCalculation cal = new InformationGainCalculation(false);
                output = cal.createTable();
            }
            else{
                flag = false;
                
            }
        }        
        return output;
        
    }
   
    
}
