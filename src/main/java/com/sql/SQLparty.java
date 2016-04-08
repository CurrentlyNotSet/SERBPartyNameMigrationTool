/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sql;

import com.model.partyModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.dbutils.DbUtils;

/**
 *
 * @author Andrew
 */
public class SQLparty {
    
    public static List getContactList(String startsWith){
        List<partyModel> list = new ArrayList<>();
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
            
            while(rs.next()) {
               partyModel item = new partyModel();
               item.setPartyID(rs.getInt("id"));
               item.setFirstName(rs.getString("firstName"));
               item.setMiddleInitial(rs.getString("middleInitial"));
               item.setLastName(rs.getString("lastName"));
               list.add(item);
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
            String sql = "SELECT * FROM party WHERE partyID = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, partyID);
            rs = ps.executeQuery();
            if(rs.first()){
                item.setPartyID(rs.getInt(""));
                item.setPrefixID(rs.getInt(""));
                item.setPrefix(rs.getString(""));
                item.setFirstName(rs.getString(""));
                item.setMiddleInitial(rs.getString(""));
                item.setLastName(rs.getString(""));
                item.setSuffix(rs.getString(""));
                item.setNameTitle(rs.getString(""));
                item.setJobTitle(rs.getString(""));
                item.setCompanyName(rs.getString(""));
                item.setAddress1(rs.getString(""));
                item.setAddress2(rs.getString(""));
                item.setAddress3(rs.getString(""));
                item.setCity(rs.getString(""));
                item.setState(rs.getString(""));
                item.setZip(rs.getString(""));
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
                    + "WHERE partyID = ?";  //13
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
