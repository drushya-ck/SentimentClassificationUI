/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author wainwetun
 */
public class TrainReview {
    private int id;
    private String review_no;
    private double total_pos_bits;
    private double total_neg_bits;
    private String sentiment;
    
    public TrainReview(){}
    
    public TrainReview(String review_no, double total_pos_bits, double total_neg_bits, String sentiment){
        this.review_no = review_no;
        this.total_pos_bits = total_pos_bits;
        this.total_neg_bits = total_neg_bits;
        this.sentiment = sentiment;
    }

    public int getId() {
        return id;
    }

    public String getReview_no() {
        return review_no;
    }

    public double getTotal_pos_bits() {
        return total_pos_bits;
    }

    public double getTotal_neg_bits() {
        return total_neg_bits;
    }

    public String getSentiment() {
        return sentiment;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setReview_no(String review_no) {
        this.review_no = review_no;
    }

    public void setTotal_pos_bits(double total_pos_bits) {
        this.total_pos_bits = total_pos_bits;
    }

    public void setTotal_neg_bits(double total_neg_bits) {
        this.total_neg_bits = total_neg_bits;
    }

    public void setSentiment(String sentiment) {
        this.sentiment = sentiment;
    }
    
    
    
}
