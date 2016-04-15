/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sql;

import com.model.partyModel;
import com.model.partyNameTableModel;
import com.util.StringUtilities;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.commons.dbutils.DbUtils;

/**
 *
 * @author Andrew
 */
public class SQLparty {
    
    public static ObservableList<partyNameTableModel> getContactList(String startsWith){
        ObservableList<partyNameTableModel> list = FXCollections.observableArrayList();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.connectToDB();
            String sql = "SELECT id, firstName, middleInitial, lastName "
                    + "FROM Party WHERE lastName LIKE ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, startsWith);
            rs = ps.executeQuery();

            while (rs.next()) {
                
                partyModel name = new partyModel();
                name.setPrefix("");
                name.setFirstName(rs.getString("firstName"));
                name.setMiddleInitial(rs.getString("middleInitial"));
                name.setLastName(rs.getString("lastName"));
                name.setSuffix("");
                
                
                
                list.add(new partyNameTableModel(
                        rs.getString("id"),
                        StringUtilities.fullName(name)
                ));
           }  
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            DbUtils.closeQuietly(ps);
            DbUtils.closeQuietly(rs);
            DbUtils.closeQuietly(conn);
        }
        return list;
    }
    
    public static partyModel getPartydetails(int partyID){
        partyModel item = new partyModel();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.connectToDB();
            String sql = "SELECT * FROM party WHERE id = ?";
            ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ps.setInt(1, partyID);
            rs = ps.executeQuery();
            if(rs.first()){
                item.setPartyID(rs.getInt("id"));
                item.setPrefix(rs.getString("prefix"));
                item.setFirstName(rs.getString("firstName"));
                item.setMiddleInitial(rs.getString("middleInitial"));
                item.setLastName(rs.getString("lastName"));
                item.setSuffix(rs.getString("suffix"));
                item.setNameTitle(rs.getString("nameTitle"));
                item.setJobTitle(rs.getString("jobTitle"));
                item.setCompanyName(rs.getString("companyName"));
                item.setAddress1(rs.getString("address1"));
                item.setAddress2(rs.getString("address2"));
                item.setAddress3(rs.getString("address3"));
                item.setCity(rs.getString("city"));
                item.setState(rs.getString("stateCode"));
                item.setZip(rs.getString("zipcode"));
                item.setPhoneOne(rs.getString("phone1"));
                item.setPhoneTwo(rs.getString("phone2"));
                item.setEmailAddress(rs.getString("emailAddress"));
            } else {
                item = null;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            DbUtils.closeQuietly(ps);
            DbUtils.closeQuietly(rs);
            DbUtils.closeQuietly(conn);
        }
        return item;
    }
    
    public static void savePartyInformation(partyModel item){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.connectToDB();
            String sql = "UPDATE parties SET "
                    + "Prefix = ?, "        //01
                    + "FirstName = ?, "     //02
                    + "MiddleInitial = ?, " //03
                    + "LastName = ?, "      //04
                    + "Suffix = ?, "        //05
                    + "NameTitle = ?, "     //06
                    + "JobTitle = ?, "      //07
                    + "CompanyFlag = ?, "   //08
                    + "CompanyName = ?, "   //09
                    + "Address1 = ?, "      //10
                    + "Address2 = ?, "      //11
                    + "Address3 = ?, "      //12
                    + "WHERE id = ?";  //13
            ps = conn.prepareStatement(sql);
            ps.setInt   ( 1, item.getPrefixID());
            ps.setString( 2, item.getFirstName());
            ps.setString( 3, item.getMiddleInitial());
            ps.setString( 4, item.getLastName());
            ps.setString( 5, item.getSuffix());
            ps.setString( 6, item.getNameTitle());
            ps.setString( 7, item.getJobTitle());
            ps.setInt   ( 8, item.getCompanyFlag());
            ps.setString( 9, item.getCompanyName());
            ps.setString(10, item.getAddress1());
            ps.setString(11, item.getAddress2());
            ps.setString(12, item.getAddress3());
            ps.setInt   (13, item.getPartyID());
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            DbUtils.closeQuietly(ps);
            DbUtils.closeQuietly(rs);
            DbUtils.closeQuietly(conn);
        }
    }
}
