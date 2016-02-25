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
public class NaiveBayesFeature {
    private int id;
    private String feature;
    private double pos_bits;
    private double neg_bits;
    
    public NaiveBayesFeature(){}
    
    public NaiveBayesFeature(String feature, double pos_bits, double neg_bits){
        this.feature = feature;
        this.pos_bits = pos_bits;
        this.neg_bits = neg_bits;
    }

    public int getId() {
        return id;
    }

    public String getFeature() {
        return feature;
    }

    public double getPos_bits() {
        return pos_bits;
    }

    public double getNeg_bits() {
        return neg_bits;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public void setPos_bits(double pos_bits) {
        this.pos_bits = pos_bits;
    }

    public void setNeg_bits(double neg_bits) {
        this.neg_bits = neg_bits;
    }
    
    
    
}
