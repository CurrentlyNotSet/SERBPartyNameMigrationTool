/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.util;

/**
 *
 * @author Andrew
 */
public class Global {
            
    private static final String states[] = {"", "AL", "AK", "AS", "AZ", "AR", 
        "CA", "CO", "CT", "DE", "DC", "FL", "FM", "GA", "GU", "HI", "ID", 
        "IL", "IN", "IA", "KS", "KY", "LA", "ME", "MH", "MD", "MA", "MI", 
        "MN", "MS", "MO", "MT", "NE", "NV", "NH", "NJ", "NM", "NY", "NC", 
        "ND", "MP", "OH", "OK", "OR", "PW", "PA", "PR", "RI", "SC", "SD", 
        "TN", "TX", "UM", "UT", "VT", "VA", "VI", "WA", "WV","WI", "WY"};
    
    private static final String alphabet[] = {"A", "B", "C", "D", 
        "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", 
        "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

    public static String[] getStates() {
        return states;
    }

    public static String[] getAlphabet() {
        return alphabet;
    }
    
}
