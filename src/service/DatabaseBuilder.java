/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author wainwetun
 */
public class DatabaseBuilder {
    public static final String DATABASE_NAME = "sentimentclassification";
    
    public static final String TABLE_FEATURES_IG = "features_infogain";
    public static final String TABLE_FEATURES_NB = "features_naivebayes";
    public static final String TABLE_TRAIN_REVIEW = "train_review";
    
    public static final String COLUMN_FIG_ID = "id";
    public static final String COLUMN_FIG_FEATURE = "feature";
    public static final String COLUMN_FIG_POS = "count_in_positive";
    public static final String COLUMN_FIG_NEG = "count_in_negative";
    public static final String COLUMN_FIG_GAIN = "gain";
    
    public static final String COLUMN_FNB_ID = "id";
    public static final String COLUMN_FNB_FEATURE = "feature";
    public static final String COLUMN_FNB_POS_BITS = "positive_bits";
    public static final String COLUMN_FNB_NEG_BITS = "negative_bits";
    
    public static final String COLUMN_TR_ID = "id";
    public static final String COLUMN_TR_RNO = "review_no";
    public static final String COLUMN_TR_POS_BITS = "total_pos_bits";
    public static final String COLUMN_TR_NEG_BITS = "total_neg_bits";
    public static final String COLUMN_TR_SENTIMENT = "sentiment";
    
    public static final String CREATE_FIG_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_FEATURES_IG +
            " ( " + COLUMN_FIG_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_FIG_FEATURE + " TEXT, " +
            COLUMN_FIG_POS + " INTEGER, " + 
            COLUMN_FIG_NEG + " INTEGER, " + 
            COLUMN_FIG_GAIN + " REAL);";
    
    public static final String DELETE_FIG_TABLE = "DROP TABLE IF EXISTS " + TABLE_FEATURES_IG+ ";";
    
    public static final String CREATE_FNB_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_FEATURES_NB +
            " ( " + COLUMN_FNB_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_FNB_FEATURE + " TEXT, " +
            COLUMN_FNB_POS_BITS + " REAL, " + 
            COLUMN_FNB_NEG_BITS + " REAL);";
    
    public static final String DELETE_FNB_TABLE = "DROP TABLE IF EXISTS " + TABLE_FEATURES_NB+ ";";
    
    public static final String CREATE_TR_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_TRAIN_REVIEW + 
            " ( " + COLUMN_TR_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + 
            COLUMN_TR_RNO + " TEXT," +
            COLUMN_TR_POS_BITS + " REAL, " +
            COLUMN_TR_NEG_BITS + " REAL, " + 
            COLUMN_TR_SENTIMENT + " TEXT);";
    
    public static final String DELETE_TR_TABLE = "DROP TABLE IF EXISTS " + TABLE_TRAIN_REVIEW + ";";
    
    public static void createTrainReviewTable(){
        try{
            Connection connection = DatabaseUtility.openConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate(DELETE_TR_TABLE);
            statement.executeUpdate(CREATE_TR_TABLE);
            statement.close();
        }
        catch(SQLException ex){
            System.err.println(ex.getMessage());
        }
        catch(ClassNotFoundException ex){
            System.err.println(ex.getMessage());
        }
        finally{
            try{
                DatabaseUtility.closeConnection();
            }
            catch(SQLException ex){
                System.err.println(ex.getMessage());
            }           
        }       
    }
    
    public static void createInfoGainTable(){
        try{
            Connection connection = DatabaseUtility.openConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate(DELETE_FIG_TABLE);
            statement.executeUpdate(CREATE_FIG_TABLE);
            statement.close();
        }
        catch(SQLException ex){
            System.err.println(ex.getMessage());
        }
        catch(ClassNotFoundException ex){
            System.err.println(ex.getMessage());
        }
        finally{
            try{
                DatabaseUtility.closeConnection();
            }
            catch(SQLException ex){
                System.err.println(ex.getMessage());
            }           
        }       
    }
    
    public static void createNaiveBayesTable(){
        try{
            Connection connection = DatabaseUtility.openConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate(DELETE_FNB_TABLE);
            statement.executeUpdate(CREATE_FNB_TABLE);
            statement.close();
        }
        catch(SQLException ex){
            System.err.println(ex.getMessage());
        }
        catch(ClassNotFoundException ex){
            System.err.println(ex.getMessage());
        }
        finally{
            try{
                DatabaseUtility.closeConnection();
            }
            catch(SQLException ex){
                System.err.println(ex.getMessage());
            }
        }       
    }
    
}
