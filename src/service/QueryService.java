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
public class QueryService {
    
    public int insertNewFeaturesInfoGain(InfoGainFeature feature)throws SQLException, ClassNotFoundException{
        int success = 0;
        try{
            Connection connection = DatabaseUtility.openConnection();
            Statement statement = connection.createStatement();
            success = statement.executeUpdate("INSERT INTO " + DatabaseBuilder.TABLE_FEATURES_IG + " VALUES (null, '" + feature.getFeature() +"',"+ feature.getPos() + ", " + feature.getNeg() +", " + feature.getGain() + ");");
            statement.close();
        }
        catch(SQLException ex){
            System.err.println(ex.getMessage());
            throw ex;
        }
        catch(ClassNotFoundException ex){
            System.err.println(ex.getMessage());
            throw ex;
        }
        finally{
            DatabaseUtility.closeConnection();          
        }       
        return success;
    }  
    
    public int insertNewFeaturesNaiveBayes(NaiveBayesFeature feature)throws SQLException, ClassNotFoundException{
        int success = 0;
        try{
            Connection connection = DatabaseUtility.openConnection();
            Statement statement = connection.createStatement();
            success = statement.executeUpdate("INSERT INTO " + DatabaseBuilder.TABLE_FEATURES_NB + " VALUES(null, '" + feature.getFeature() + "', " + feature.getPos_bits() + ", " + feature.getNeg_bits()+");");
            statement.close();
        }
        catch(SQLException ex){
            System.err.println(ex.getMessage());
            throw ex;
        }
        catch(ClassNotFoundException ex){
            System.err.println(ex.getMessage());
            throw ex;
        }
        finally{
            DatabaseUtility.closeConnection();
        }       
        return success;
    }
    

    
    public int insertNewTrainReview(TrainReview trainReview)throws SQLException, ClassNotFoundException{
        int success = 0;
        try{
            Connection connection = DatabaseUtility.openConnection();
            Statement statement = connection.createStatement();
            success = statement.executeUpdate("INSERT INTO " + DatabaseBuilder.TABLE_TRAIN_REVIEW + " VALUES (null, '" + trainReview.getReview_no() + "', " + trainReview.getTotal_pos_bits() + ", " + trainReview.getTotal_neg_bits()+ ", '" + trainReview.getSentiment()+"');");
            statement.close();
        }
        catch(SQLException ex){
            System.err.println(ex.getMessage());
            throw ex;
        }
        catch(ClassNotFoundException ex){
            System.err.println(ex.getMessage());
            throw ex;
        }
        finally{
            DatabaseUtility.closeConnection();
        }       
        return success;
    }
    
    public List<InfoGainFeature> getInfoGainFeature(double gain)throws SQLException, ClassNotFoundException{
        List<InfoGainFeature> features = new ArrayList<>();
        try{
            Connection connection = DatabaseUtility.openConnection();
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery("SELECT * FROM "+ DatabaseBuilder.TABLE_FEATURES_IG + " WHERE " + DatabaseBuilder.COLUMN_FIG_GAIN + " > "+ gain +";");
            while(results.next()){
                InfoGainFeature f = new InfoGainFeature(results.getString(DatabaseBuilder.COLUMN_FIG_FEATURE), results.getInt(DatabaseBuilder.COLUMN_FIG_POS), results.getInt(DatabaseBuilder.COLUMN_FIG_NEG), results.getFloat(DatabaseBuilder.COLUMN_FIG_GAIN));
                features.add(f);
            }
            results.close();
            statement.close();
        }
        catch(SQLException ex){
            System.err.println(ex.getMessage());
            throw ex;
        }
        catch(ClassNotFoundException ex){
            System.err.println(ex.getMessage());
            throw ex;
        }
        finally{
            DatabaseUtility.closeConnection();
        }   
        return features;
    }
    
    public List<NaiveBayesFeature> getNaiveBayesFeature()throws SQLException, ClassNotFoundException{
        List<NaiveBayesFeature> features = new ArrayList<>();
        try{
            Connection connection = DatabaseUtility.openConnection();
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery("SELECT * FROM "+ DatabaseBuilder.TABLE_FEATURES_NB + ";");
            while(results.next()){
                NaiveBayesFeature f = new NaiveBayesFeature(results.getString(DatabaseBuilder.COLUMN_FNB_FEATURE), results.getDouble(DatabaseBuilder.COLUMN_FNB_POS_BITS), results.getDouble(DatabaseBuilder.COLUMN_FNB_NEG_BITS));
                features.add(f);
            }
            results.close();
            statement.close();
        }
        catch(SQLException ex){
            System.err.println(ex.getMessage());
            throw ex;
        }
        catch(ClassNotFoundException ex){
            System.err.println(ex.getMessage());
            throw ex;
        }
        finally{
            DatabaseUtility.closeConnection();
        }   
        return features;
    }
}
