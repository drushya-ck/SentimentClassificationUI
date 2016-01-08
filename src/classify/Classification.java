/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package classify;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * @author Wai Nwe Tun
 */
public class Classification {
    private BernoulliNB nb;
    public Classification(){
        nb = new BernoulliNB();       
    }
    
    public String classify(){
        String result = "";
        StringBuilder review = new StringBuilder();
        try{            
            Scanner scanner = new Scanner(new File("review.txt"));
            while(scanner.hasNext()){
                review.append(scanner.next() + " ");
            }
            scanner.close();           
        }
        catch(FileNotFoundException ex){
            System.err.println(ex.getMessage());
        }
        nb.applyBernoulliNB(review.toString());
        if(nb.getScore()[0]>nb.getScore()[1]){
            result = " This review has a positive sentiment.";
        }
        else if(nb.getScore()[0]<nb.getScore()[1]){
            result = " This review has a negative sentiment.";
        }
        else{
            result = " This review is ambiguous.";
        }
        return result;
    }  
    
    public String train(){
        return nb.trainBernoulliNB();
    }
}
