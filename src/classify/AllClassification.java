/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package classify;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import model.TrainReview;
import preprocess.Preprocessing;
import service.DatabaseBuilder;
import service.QueryManager;

/**
 *
 * @author Wai Nwe Tun
 */
public class AllClassification {
    private StringBuilder result;
    private BernoulliNB nb;
    private int true_pos = 0;
    private int true_neg = 0;
    
    public AllClassification(){
        result = new StringBuilder();
        this.prepareTable();
        nb = new BernoulliNB();       
    }
    
    public String evaluate(){
        int false_pos = 300 - true_pos;
        int false_neg = 300 - true_neg;
        System.out.println(true_pos + "  " + true_neg + "  " + false_pos + " " + false_neg);

        double accruacy = (double)(true_pos + true_neg)/(true_pos + false_pos + true_neg + false_neg);
        double recall_pos = (double) true_pos/ (true_pos + false_neg);
        double recall_neg = (double) true_neg/ (true_neg + false_pos);
        double precision_pos = (double) true_pos/(true_pos + false_pos);
        double precision_neg = (double) true_neg/ (true_neg + false_neg);
        
        result.append("Accuracy : " + accruacy);
        result.append("\nRecall_pos : " + recall_pos);
        result.append("\nRecall_neg : " + recall_neg);
        result.append("\nPrecision_pos : " + precision_pos);
        result.append("\nPrecision_neg : " + precision_neg);
        return result.toString();
    }
    
    public void classify(){
        nb.trainBernoulliNB();
        for(int i=700; i<1000; i++){
            
            StringBuilder review = new StringBuilder();
            try{            
                Scanner scanner = new Scanner(new File("extracted_review/with_nouns/pos/cv" + i + ".txt"));
                while(scanner.hasNext()){
                    review.append(scanner.next() + " ");
                }
            scanner.close();           
            }
            catch(FileNotFoundException ex){
                System.err.println(ex.getMessage());
            }
            //System.out.println(review.toString());
            double[] temp = nb.applyBernoulliNB(review.toString());
            System.out.println(temp[0] + "..");
            this.savingBits("p"+i, nb.getScore()[0], nb.getScore()[1]);
            if(nb.getScore()[0]>nb.getScore()[1]){
                true_pos ++;
                result.append(" This cv" + i +" review (positive) has a positive sentiment.\n");
            }
            else if(nb.getScore()[0]<nb.getScore()[1]){
                result.append(" This cv" + i +" review (positive) has a negative sentiment.\n");
            }
            else{
                result.append(" This cv" + i +" review (positive) is ambiguious.\n");
            }
        }
        for(int i=700; i<1000; i++){
            
            StringBuilder review = new StringBuilder();
            try{            
                Scanner scanner = new Scanner(new File("extracted_review/with_nouns/neg/cv" + i + ".txt"));
                while(scanner.hasNext()){
                    review.append(scanner.next() + " ");
                }
            scanner.close();           
            }
            catch(FileNotFoundException ex){
                System.err.println(ex.getMessage());
            }
            nb.applyBernoulliNB(review.toString());
            this.savingBits("n"+i, nb.getScore()[0], nb.getScore()[1]);
            if(nb.getScore()[0]>nb.getScore()[1]){
                result.append(" This cv" + i +" review (negative) has a positive sentiment.\n");
            }
            else if(nb.getScore()[0]<nb.getScore()[1]){
                true_neg ++;
                result.append(" This cv" + i +" review (negative) has a negative sentiment.\n");
            }
            else{
                result.append(" This cv" + i +" review (negative) is ambiguious.\n");
            }
        }
    }  
    
    public void savingBits(String index, double pos, double neg){
		String  sentiment = "ambiguious";
		if(pos<neg){
                    sentiment = "negative";
		}
		else if(pos>neg){
                    sentiment = "positive";
		}
                TrainReview temp = new TrainReview(index, pos, neg, sentiment);
                //System.out.println(temp.getTotal_pos_bits() +" " +temp.getTotal_neg_bits());
                int success = QueryManager.insertNewTrainReview(temp);
                if(success==0){
                    System.err.println("Cannot insert a review into TrainReview");
                }		

	}
	public static void prepareTable(){
            DatabaseBuilder.createTrainReviewTable();           
        }
    
   public String getResults(){
       return this.result.toString();
   }
}
