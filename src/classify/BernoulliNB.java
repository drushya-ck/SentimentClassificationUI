/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package classify;


import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import model.InfoGainFeature;
import model.NaiveBayesFeature;
import service.DatabaseBuilder;
import service.QueryManager;


/**
 *
 * @author Wai Nwe Tun
 */


public class BernoulliNB {
    private static HashSet<String> features;
    private static double[] prior;
    private static HashMap<String,Double> conprob_pos;
    private static HashMap<String, Double> conprob_neg;
    private static HashMap<String, Integer> pos_count;
    private static HashMap<String, Integer> neg_count;
    private double[] score;
    private static final double GAIN = 0.0007;


    public BernoulliNB(){
        features = new HashSet<String>();
        prior = new double[2];
        conprob_pos = new HashMap<String, Double>();
        conprob_neg = new HashMap<String, Double>();
        pos_count = new HashMap<>();
        neg_count = new HashMap<>();
        score = new double[2];
        
    }
    
    
    public double[] applyBernoulliNB(String review){
        getPriorProb();
        int total = 1400;
        for(int i=0; i<2; i++){
            prior[i] = 700.0/total;
        }
        for(int i=0; i<2; i++){
            score[i] = Math.log(prior[i]);
            //System.out.println(score[i] + " in applyBernoulliNB ..");
            for(String feature: features){
                if(review.contains(feature)){
                    if(i==0){
                        score[i] = score[i] + Math.log(conprob_pos.get(feature));
                        System.out.println(conprob_pos.get(feature) +" apply Bernoulli Naive Bayes");
                    }
                    else{
                        score[i] = score[i] + Math.log(conprob_neg.get(feature));
                        System.out.println(conprob_neg.get(feature) +" apply Bernoulli Naive Bayes");
                    }
            }
                
            else{
                    if(i==0){
                        score[i] = score[i] + Math.log(1 - conprob_pos.get(feature));
                    }
                    else{
                        score[i] = score[i] + Math.log(1 - conprob_neg.get(feature));
                    }
                }
            }
                        
        }
        
        return score;
    }

    
    public void getPriorProb(){  
        features.clear();      
        
        List<NaiveBayesFeature> featuresSet = QueryManager.getNaiveBayesFeature();
        
        for(int i=0; i<featuresSet.size(); i++){
            NaiveBayesFeature temp = featuresSet.get(i);
            String feature = temp.getFeature();
            double pos_bits = temp.getPos_bits();
            double neg_bits = temp.getNeg_bits();
            
            System.out.println(pos_bits + " " + neg_bits + " in get prior prob");
            
            conprob_pos.put(feature, pos_bits);
            conprob_neg.put(feature, neg_bits);
            features.add(feature);
        }
        //System.out.println("Get Prior Prob " + features.size());
                
    }
    public void getFeaturesCounts(){     
        System.out.println("feature counts");
        features.clear();
        List<InfoGainFeature> featuresSet = QueryManager.getInfoGainFeature(GAIN);
        for(int i=0;i<featuresSet.size();i++){
            InfoGainFeature temp = featuresSet.get(i);
            String feature = temp.getFeature();
            int pos = temp.getPos();
            int neg = temp.getNeg();
            features.add(feature);
            pos_count.put(feature, pos);
            neg_count.put(feature, neg);
        }                      
    }
    
    public String trainBernoulliNB(){
        System.out.println("train");        
        getFeaturesCounts();
        int total = 1400;
        for(int i=0; i<2; i++){
            prior[i] = 700.0/total;
        }
        System.out.println(features.size());
        for(String feature: features){
            for(int i = 0; i<2; i++){
                if(i==0){
                    conprob_pos.put(feature, (pos_count.get(feature) + 1.0)/(700 + 2));
                }
                else{
                    conprob_neg.put(feature, (neg_count.get(feature) + 1.0)/(700+2));
                }
            }
        }
        System.out.println( features.size() + " "+ conprob_neg.size() + " " + conprob_pos.size());
        savePosAndNegBits();
        return "Prior_probabilities of features have been successfully calculated.";
        
    }
    public void savePosAndNegBits(){        
        DatabaseBuilder.createNaiveBayesTable();
        for(String feature: features){
            NaiveBayesFeature temp = new NaiveBayesFeature(feature, conprob_pos.get(feature), conprob_neg.get(feature));
            int success = QueryManager.insertNewFeaturesNaiveBayes(temp);
            if(success==0){
                System.err.println("Cannot insert into NaiveBayesFeatures!!");
            }
        }       
    }

 
    public double[] getScore() {
            return score;
    }
    public void setScore(double[] score) {
            this.score = score;
    }
}
