/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.InfoGainFeature;
import model.NaiveBayesFeature;
import model.TrainReview;

/**
 *
 * @author wainwetun
 */
public class QueryManager {
    public static int insertNewFeaturesInfoGain(InfoGainFeature feature){
        int success = 0;
        try{
            
            success = new QueryService().insertNewFeaturesInfoGain(feature);
        }
        catch(SQLException ex){
            System.err.println(ex.getMessage());
            
        }
        catch(ClassNotFoundException ex){
            System.err.println(ex.getMessage());
            
        }
               
        return success;
    }  
    
    public static int insertNewFeaturesNaiveBayes(NaiveBayesFeature feature){
        int success = 0;
        try{
            
            success = new QueryService().insertNewFeaturesNaiveBayes(feature);
        }
        catch(SQLException ex){
            System.err.println(ex.getMessage());
            
        }
        catch(ClassNotFoundException ex){
            System.err.println(ex.getMessage());
            
        }
              
        return success;
    }
    public static int insertNewTrainReview(TrainReview trainReview){
        int success = 0;
        try{
            success = new QueryService().insertNewTrainReview(trainReview);
        }
        catch(SQLException ex){
            System.err.println(ex.getMessage());
            
        }
        catch(ClassNotFoundException ex){
            System.err.println(ex.getMessage());
            
        }
               
        return success;
    }
    
    public static List<InfoGainFeature> getInfoGainFeature(double gain){
        List<InfoGainFeature> features = new ArrayList<>();
        try{
            features = new QueryService().getInfoGainFeature(gain);
        }
        catch(SQLException ex){
            System.err.println(ex.getMessage());
        }
        catch(ClassNotFoundException ex){
            System.err.println(ex.getMessage());
        }
        return features;
    }
    
    public static List<NaiveBayesFeature> getNaiveBayesFeature(){
        List<NaiveBayesFeature> features = new ArrayList<>();
        try{
            features = new QueryService().getNaiveBayesFeature();
        }
        catch(SQLException ex){
            System.err.println(ex.getMessage());
        }
        catch(ClassNotFoundException ex){
            System.err.println(ex.getMessage());
        } 
        return features;
    }
}
