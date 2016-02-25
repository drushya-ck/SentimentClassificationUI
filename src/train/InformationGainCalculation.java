/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package train;

/**
 *
 * @author Wai Nwe Tun
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
import model.InfoGainFeature;
import preprocess.Preprocessing;
import service.DatabaseBuilder;
import service.QueryManager;

public class InformationGainCalculation {
    private Set<String> allSet;
    private HashMap<String, Integer> posFeatures;
    private HashMap<String, Integer> negFeatures;
    public int isPos ;
   
    
    public InformationGainCalculation(int isPos){
        allSet = new HashSet<>();
        posFeatures = new HashMap<>();
        negFeatures = new HashMap<>();
        this.isPos = isPos;
        this.preprocess();
        this.negFeatureCounts();
        this.posFeatureCounts();
        
        System.out.println("IG: constructor");
    }

    public void preprocess(){
        
        for(int i=0; i<1000; i++){
            Preprocessing preprocessing1 = new Preprocessing();
            Preprocessing preprocessing2 = new Preprocessing();
            preprocessing1.posTagging("input_txt_sentoken/pos/cv"+i+".txt", "extracted_review/with_nouns/pos/cv"+i+".txt");
            preprocessing2.posTagging("input_txt_sentoken/neg/cv"+i+".txt", "extracted_review/with_nouns/neg/cv"+i+".txt");
        }
        System.out.println("Extraction stage is completed!");
    }
    
    public void posFeatureCounts(){       
        for(int i=0; i<700; i++){
            Set<String> posSet = new HashSet<>();
            try{
                Scanner scanner = new Scanner(new File("extracted_review/with_nouns/pos/cv" + i + ".txt"));			
                //HashMap<String, Integer> tempMap = new HashMap<String, Integer>();
                while(scanner.hasNext()){
                    String word = scanner.next();
                    if(word.indexOf("'")<0){ 
                            //tempMap.put(word, 1);
                        posSet.add(word);
                    }							
                }
                scanner.close();
            }
            catch(FileNotFoundException ex){
                System.out.println(ex.getMessage());
            }
            allSet.addAll(posSet);
            //Set<String> set = tempMap.keySet();
            Iterator<String> iterator = posSet.iterator();
            while (iterator.hasNext()) {
                String word = iterator.next();
                if(posFeatures.containsKey(word)){
                    posFeatures.put(word, posFeatures.get(word)+1);					
                }
                else{
                    posFeatures.put(word, 1);
                }
            }
        }
        System.out.print("pos features completed.");
        if(isPos==1){
            System.out.println("the new is pos");
            Set<String> posSet = new HashSet<>();
            try{
                Scanner scanner = new Scanner(new File("review.txt"));
                while(scanner.hasNext()){
                    String word = scanner.next();
                    if(word.indexOf("'")<0){ 
                            //tempMap.put(word, 1);
                        posSet.add(word);
                    }
                }
                scanner.close();
            }
            catch(FileNotFoundException ex){
                System.out.println(ex.getMessage());
            }
            allSet.addAll(posSet);
            //Set<String> set = tempMap.keySet();
            Iterator<String> iterator = posSet.iterator();
            while (iterator.hasNext()) {
                String word = iterator.next();
                if(posFeatures.containsKey(word)){
                    posFeatures.put(word, posFeatures.get(word)+1);					
                }
                else{
                    posFeatures.put(word, 1);
                }
            }
        }
        System.out.println(allSet.size());
    }
    
    public void negFeatureCounts(){  
        System.out.println("neg fea");
        for(int i=0; i<700; i++){
            Set<String> negSet = new HashSet<>();
            try{
                Scanner scanner = new Scanner(new File("extracted_review/with_nouns/neg/cv" + i + ".txt"));                
                //HashMap<String, Integer> tempMap = new HashMap<String, Integer>();
                while(scanner.hasNext()){
                    String word = scanner.next();
                    if(word.indexOf("'")<0){
                            //tempMap.put(word, 1);
                        negSet.add(word);
                    }							
                }
                scanner.close();
            }
            catch(FileNotFoundException ex){
                System.out.println(ex.getMessage());
            }
            allSet.addAll(negSet);
            Iterator<String> iterator = negSet.iterator();
            while (iterator.hasNext()) {
                String word = iterator.next();
                if(negFeatures.containsKey(word)){
                    negFeatures.put(word, negFeatures.get(word)+1);					
                }
                else{
                    negFeatures.put(word, 1);
                }
            }
        }
        if(isPos==-1){
            Set<String> negSet = new HashSet<>();
            try{
                Scanner scanner = new Scanner(new File("review.txt"));
                while(scanner.hasNext()){
                    String word = scanner.next();
                    if(word.indexOf("'")<0){ 
                            //tempMap.put(word, 1);
                        negSet.add(word);
                    }
                }
                scanner.close();
            }
            catch(FileNotFoundException ex){
                System.out.println(ex.getMessage());
            }
            allSet.addAll(negSet);
            //Set<String> set = tempMap.keySet();
            Iterator<String> iterator = negSet.iterator();
            while (iterator.hasNext()) {
                String word = iterator.next();
                if(negFeatures.containsKey(word)){
                    negFeatures.put(word, negFeatures.get(word)+1);					
                }
                else{
                    negFeatures.put(word, 1);
                }
            }
        }
        System.out.println(allSet.size());
    }
	
    public String createTable(){
        System.out.println("creating table");
        System.out.println("pos" + posFeatures.size() + "neg " + negFeatures.size());
        
        DatabaseBuilder.createInfoGainTable();
        for(String feature: allSet){
            int pos = (posFeatures.containsKey(feature))? posFeatures.get(feature) : 0;
            int neg = (negFeatures.containsKey(feature))? negFeatures.get(feature) : 0;
            int revPos = 700 - pos;
            int revNeg = 700 - neg;
            double gain = calculateInformationGain(pos, neg, revPos, revNeg);
            
            QueryManager.insertNewFeaturesInfoGain(new InfoGainFeature(feature, pos, neg, gain));                       
        }        
        return "Features have been successfully calculated using Information Gain.";
    }
	

    public static double calculateInformationGain(float pos, float neg, float revPos, float revNeg){
        double score = 0;
        float total = pos + neg + revNeg + revPos;

        float posProb = (pos+revPos)/total;		
        float negProb = (neg+revNeg)/total;

        float wordProb = (pos+neg)/total;
        float notWordProb = (revPos+revNeg)/total;

        float positiveWordProb = (pos == 0)? 0 : (pos)/(pos+neg);
        float positiveNotWordProb = (revPos == 0)? 0 : (revPos)/(revNeg+revPos);

        float negativeWordProb = (neg == 0) ? 0 : (neg)/(pos+neg);
        float negativeNotWordProb = (revNeg == 0) ? 0 : (revNeg)/(revPos+revNeg);				

        double firstPart = posProb * logBase2(posProb) + negProb * logBase2(negProb);

        double secondPart1 = (positiveWordProb == 0.0)? 0 : positiveWordProb * logBase2(positiveWordProb);
        double secondPart2 = (negativeWordProb == 0.0)? 0 : negativeWordProb * logBase2(negativeWordProb);		
        double secondPart = wordProb * (- secondPart1 - secondPart2);

        double thirdPart1 = (positiveNotWordProb == 0.0)? 0 : positiveNotWordProb * logBase2(positiveNotWordProb);
        double thirdPart2 = (negativeNotWordProb == 0.0)? 0 : negativeNotWordProb * logBase2(negativeNotWordProb);
        double thirdPart = notWordProb * (- thirdPart1 - thirdPart2);

        score = - firstPart - (secondPart + thirdPart);

        return score;
    }	

    public static double logBase2(double number){
        return Math.log(number)/Math.log(2);
    }

}
