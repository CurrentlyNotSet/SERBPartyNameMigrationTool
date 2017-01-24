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
public class StringUtilities {
    
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
