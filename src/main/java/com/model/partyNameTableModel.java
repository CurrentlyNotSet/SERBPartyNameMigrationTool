/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Andrew
 */
public class partyNameTableModel {
    public StringProperty ID;
    public StringProperty partyName;

    public partyNameTableModel() {
        this(null, null);
    }
    
    public partyNameTableModel(String ID, String partyName) {
        this.ID = new SimpleStringProperty(ID);
        this.partyName = new SimpleStringProperty(partyName);
    }

    public StringProperty getID() {
        return ID;
    }

    public void setID(StringProperty ID) {
        this.ID = ID;
    }
    
    public StringProperty getPartyName() {
        return partyName;
    }

    public void setPartyName(StringProperty partyName) {
        this.partyName = partyName;
    }
}
