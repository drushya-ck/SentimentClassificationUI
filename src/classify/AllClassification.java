/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package classify;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Scanner;

/**
 *
 * @author Wai Nwe Tun
 */
public class AllClassification {
    private StringBuilder result;
    private BernoulliNB nb;
    public AllClassification(){
        result = new StringBuilder();
        this.prepareTable();
        nb = new BernoulliNB();       
    }
    
    public void classify(){
        for(int i=700; i<1000; i++){
            
            StringBuilder review = new StringBuilder();
            try{            
                Scanner scanner = new Scanner(new File("extracted_review/without_neg_hand/without_nouns/pos/cv" + i + ".txt"));
                while(scanner.hasNext()){
                    review.append(scanner.next() + " ");
                }
            scanner.close();           
            }
            catch(FileNotFoundException ex){
                System.err.println(ex.getMessage());
            }
            nb.applyBernoulliNB(review.toString());
            this.savingBits("p"+i, nb.getScore()[0], nb.getScore()[1]);
            if(nb.getScore()[0]>nb.getScore()[1]){
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
                Scanner scanner = new Scanner(new File("extracted_review/without_neg_hand/without_nouns/neg/cv" + i + ".txt"));
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
                result.append(" This cv" + i +" review (negative) has a negative sentiment.\n");
            }
            else{
                result.append(" This cv" + i +" review (negative) is ambiguious.\n");
            }
        }
    }  
    
    public void savingBits(String index, double pos, double neg){
		int classVar = 0;
		if(pos<neg){
			classVar = -1;
		}
		else if(pos>neg){
			classVar = 1;
		}
		      Connection con = null;
		try{
			Class.forName("com.mysql.jdbc.Driver");
			//System.out.println("OK");
			con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/MOVIE_REVIEW", "root", "root");
			System.out.println("connection ok");
			
			                 Statement statement = (Statement)con.createStatement();
			statement.executeUpdate("UPDATE TRAIN_TEST_RESULT SET POS_BIT = " + pos + ", NEG_BIT = " + neg + ", CLASS = " + classVar  +" WHERE REVIEW = '" + index + "';");
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
	public static void prepareTable(){
		Connection con = null;
		try{
			Class.forName("com.mysql.jdbc.Driver");
			//System.out.println("OK");
			con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/MOVIE_REVIEW", "root", "root");
			System.out.println("connection ok");
			Statement s = (Statement)con.createStatement();
                        s.executeUpdate("DELETE FROM TRAIN_TEST_RESULT;");
                        s.close();
			for(int i= 700; i<1000; i++){
				Statement statement = (Statement) con.createStatement();
				statement.executeUpdate("INSERT INTO TRAIN_TEST_RESULT (REVIEW) VALUES ('p" + i + "');");				
				statement.close();
			}
			for(int i= 700; i<1000; i++){
				Statement statement = (Statement) con.createStatement();
				statement.executeUpdate("INSERT INTO TRAIN_TEST_RESULT(REVIEW) VALUES ('n" + i + "');");				
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
    
   public String getResults(){
       return this.result.toString();
   }
}
