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
public class InfoGainFeature {
    private int id;
    private String feature;
    private int pos;
    private int neg;
    private double gain;
    
    public InfoGainFeature(){}
    
    public InfoGainFeature(String feature, int pos, int neg, double gain){
        this.feature = feature;
        this.pos = pos;
        this.neg = neg;
        this.gain = gain;
    }

    public int getId() {
        return id;
    }

    public String getFeature() {
        return feature;
    }

    public int getPos() {
        return pos;
    }

    public int getNeg() {
        return neg;
    }

    public double getGain() {
        return gain;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public void setNeg(int neg) {
        this.neg = neg;
    }

    public void setGain(double gain) {
        this.gain = gain;
    }
    
    
    
}
