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
        
        name += item.getPrefix();
        if (!"".equals(item.getPrefix())){
            name += " ";
        }
        name += item.getFirstName();
        if (!"".equals(item.getFirstName())){
            name += " ";
        }
        name += item.getMiddleInitial();
        if (!"".equals(item.getMiddleInitial())) {
            name += ". ";
        }
        name += item.getLastName();

        if (!"".equals(item.getSuffix())) {
            if (!"".equals(name.trim())) {
                name += " ";
            }
            name += item.getSuffix();
        }
        return name;
    }
}
