/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package evaluate;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author Wai Nwe Tun
 */
public class Evaluation {
    private StringBuilder result;
    
    public Evaluation(){
        result = new StringBuilder();
        //this.evaluate();
    }
    
    public void evaluate(){
        int true_pos =  getTruePos();
        int true_neg = getTrueNeg();
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
    }
    
    public int getTruePos(){
        int true_pos = 0;
        Connection con = null;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            //System.out.println("OK");
            con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/MOVIE_REVIEW", "root", "root");
            System.out.println("connection ok");

            Statement statement = (Statement) con.createStatement();
            ResultSet set = statement.executeQuery("SELECT COUNT(*) FROM TRAIN_TEST_RESULT WHERE CLASS = 1 AND REVIEW LIKE 'p%'");
            while(set.next()){
                    true_pos = set.getInt(1);
            }
            set.close();
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
        return true_pos;
    }
	
    public int getTrueNeg(){
        int true_neg = 0;
        Connection con = null;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            //System.out.println("OK");
            con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/MOVIE_REVIEW", "root", "root");
            System.out.println("connection ok");

            Statement statement = (Statement) con.createStatement();
            ResultSet set = statement.executeQuery("SELECT COUNT(*) FROM TRAIN_TEST_RESULT WHERE CLASS = -1 AND REVIEW LIKE 'n%'");
            while(set.next()){
                true_neg = set.getInt(1);
            }
            set.close();
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

        return true_neg;
    }

    public String getResults(){
        return this.result.toString();
    }
}
