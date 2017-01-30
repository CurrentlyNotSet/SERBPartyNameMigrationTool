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
            
    private static int recordCount;
    
    private static final String states[] = {"", "AL", "AK", "AS", "AZ", "AR", 
        "CA", "CO", "CT", "DE", "DC", "FL", "FM", "GA", "GU", "HI", "ID", 
        "IL", "IN", "IA", "KS", "KY", "LA", "ME", "MH", "MD", "MA", "MI", 
        "MN", "MS", "MO", "MT", "NE", "NV", "NH", "NJ", "NM", "NY", "NC", 
        "ND", "MP", "OH", "OK", "OR", "PW", "PA", "PR", "RI", "SC", "SD", 
        "TN", "TX", "UM", "UT", "VT", "VA", "VI", "WA", "WV","WI", "WY"};

    /**
     * Gets record count from how many persons are loaded into the table
     * @return Integer
     */
    public static int getRecordCount() {
        return recordCount;
    }

    /**
     * Sets record count from how many persons are loaded into the table
     * @param recordCount Integer
     */
    public static void setRecordCount(int recordCount) {
        Global.recordCount = recordCount;
    }

    /**
     * Gets a list of all of the 2 character postal codes for all 50 states
     * @return String[]
     */
    public static String[] getStates() {
        return states;
    }

}
