/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package classify;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.HashSet;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;


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


    public BernoulliNB(){
        features = new HashSet<String>();
        prior = new double[2];
        conprob_pos = new HashMap<String, Double>();
        conprob_neg = new HashMap<String, Double>();
        pos_count = new HashMap<>();
        neg_count = new HashMap<>();
        score = new double[2];
        
    }
    
    
    public void applyBernoulliNB(String review){
        getPriorProb();
        int total = 1400;
        for(int i=0; i<2; i++){
            prior[i] = 700.0/total;
        }
        for(int i=0; i<2; i++){
            score[i] = Math.log(prior[i]);
            for(String feature: features){
                if(review.contains(feature)){
                    if(i==0){
                        score[i] = score[i] + Math.log(conprob_pos.get(feature));
                    }
                    else{
                        score[i] = score[i] + Math.log(conprob_neg.get(feature));
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
    }

    
    public void getPriorProb(){  
        features.clear();
        Connection con = null;
            try{
                Class.forName("com.mysql.jdbc.Driver");
                //System.out.println("OK");
                con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/MOVIE_REVIEW", "root", "root");
                System.out.println("connection ok");

                Statement statement = (Statement)con.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT FEATURE, POS_BITS, NEG_BITS FROM TRAIN_PRIOR_PROBABILITY;");
                while(resultSet.next()){
                    String feature = resultSet.getString("FEATURE");
                    double pos_bits = resultSet.getDouble("POS_BITS");
                    double neg_bits = resultSet.getDouble("NEG_BITS");

                    conprob_pos.put(feature, pos_bits);
                    conprob_neg.put(feature, neg_bits);
                    features.add(feature);
                }

                    resultSet.close();
                    statement.close();				
            }
            catch(Exception ex){
                    System.out.println(ex.getMessage());
            }
            finally{
                    try{
                            if(con!=null){
                                    con.close();
                            }
                    }
                    catch(Exception ex){
                            System.out.println(ex.getMessage());
                    }
            }
    }
    public void getFeaturesCounts(){     
        System.out.println("feature counts");
        features.clear();
        Connection con = null;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            //System.out.println("OK");
            con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/MOVIE_REVIEW", "root", "root");
            //System.out.println("connection ok");			

            Statement statement = (Statement) con.createStatement();
            ResultSet set = statement.executeQuery("SELECT FEATURE, COUNT_IN_POSITIVE, COUNT_IN_NEGATIVE FROM TRAIN_FEATURES WHERE GAIN >= " +0.0007 + ";");
            while(set.next()){
                String feature = set.getString("FEATURE");
                int pos = set.getInt("COUNT_IN_POSITIVE");
                int neg = set.getInt("COUNT_IN_NEGATIVE");
                features.add(feature);
                pos_count.put(feature, pos);
                neg_count.put(feature, neg);
            }
            set.close();
            statement.close();
            
            System.out.println(features.size() + " " + pos_count.size() + " " + neg_count.size());
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }
        finally{
            try{
                if(con!=null){
                        con.close();
                }
            }
            catch(Exception ex){
                System.out.println(ex.getMessage());
            }
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
        Connection con = null;
        try{
                Class.forName("com.mysql.jdbc.Driver");
                //System.out.println("OK");
                con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/MOVIE_REVIEW", "root", "root");
                //System.out.println("connection ok");		
                Statement statement = (Statement) con.createStatement();
                statement.execute("DELETE FROM train_prior_probability;");
                statement.close();
                for(String feature: features){
                    statement = (Statement) con.createStatement();
                    statement.execute("INSERT INTO train_prior_probability(FEATURE, POS_BITS, NEG_BITS) VALUES ('" +feature + "', "+ conprob_pos.get(feature) + ", " + conprob_neg.get(feature) + ");");
                    statement.close();
                }
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }
        finally{
            try{
                if(con!=null){
                    con.close();
                }
            }
            catch(Exception ex){
                System.out.println(ex.getMessage());
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
