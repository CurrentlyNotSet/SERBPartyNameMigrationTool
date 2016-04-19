/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.util;

import com.model.partyModel;

/**
 *
 * @author Andrew
 */
public class StringUtilities {
    
    public static String fullName(partyModel item) {
        String name = "";
        name += ((item.getPrefix() == null || "".equals(item.getPrefix())) 
                ? "" : item.getPrefix() + " ");
        name += ((item.getFirstName()== null || "".equals(item.getFirstName())) 
                ? "" : item.getFirstName() + " ");
        name += ((item.getMiddleInitial() == null || "".equals(item.getMiddleInitial())) 
                ? "" : item.getMiddleInitial() +  ". ");
        name += ((item.getLastName() == null) 
                ? "" : item.getLastName());
        name += ((item.getSuffix() == null || "".equals(item.getSuffix())) 
                ? "" : " " + item.getSuffix());
        
        return name.trim();
    }
    
    public static String convertStringToPhoneNumber(String number) {
        String formattedNumber = "";
        if (number.length() >= 6){
            formattedNumber += "(" + number.substring(0, 3) + ") ";
            formattedNumber += number.substring(3, 6) + "-";
            formattedNumber += number.substring(6);
        }
        return formattedNumber;
    }
    
    public static String convertPhoneNumberToString(String number) {
        return number.replaceAll("[^0-9]", "");
    }
    
}
