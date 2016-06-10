/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sql;

import com.model.partyModel;
import com.model.partyNameTableModel;
import com.util.Global;
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
    
    public static ObservableList<partyNameTableModel> getContactList() {
        Global.setRecordCount(0);
        ObservableList<partyNameTableModel> list = FXCollections.observableArrayList();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.connectToDB();
            String sql = "SELECT id, companyName FROM party WHERE active = 1 "
                    + "AND firstName IS NULL AND lastName IS NULL AND companyName IS NOT NULL "
                    + "AND companyName NOT LIKE '%union%'   AND companyName NOT LIKE '%cincinnati%' "
                    + "AND companyName NOT LIKE '%test%'    AND companyName NOT LIKE '%Village%' "
                    + "AND companyName NOT LIKE '%IBT%'     AND companyName NOT LIKE '%employee%' "
                    + "AND companyName NOT LIKE '%UAW%'     AND companyName NOT LIKE '%OAPSE%' "
                    + "AND companyName NOT LIKE '%park%'    AND companyName NOT LIKE '%nursing%' "
                    + "AND companyName NOT LIKE '%Asso%'    AND companyName NOT LIKE '%organization%' "
                    + "AND companyName NOT LIKE '%police%'  AND companyName NOT LIKE '%Assn%'"
                    + "AND companyName NOT LIKE '%fop%'     AND companyName NOT LIKE '%lodge%' "
                    + "AND companyName NOT LIKE '%local%'   AND companyName NOT LIKE '%labor%' "
                    + "AND companyName NOT LIKE '%county%'  AND companyName NOT LIKE '%Education%' "
                    + "AND companyName NOT LIKE '%college%' AND companyName NOT LIKE '%Authority%' "
                    + "AND companyName NOT LIKE '%Ohio%'    AND companyName NOT LIKE '%City%' "
                    + "AND companyName NOT LIKE '%worker%'  AND companyName NOT LIKE '%cleveland%' "
                    + "AND companyName NOT LIKE '%library%' AND companyName NOT LIKE '%teacher%' "
                    + "AND companyName NOT LIKE '%council%' AND companyName NOT LIKE '%health%' "
                    + "AND companyName NOT LIKE '%team%'    AND companyName NOT LIKE '%AFSCME%' "
                    + "AND companyName NOT LIKE '%school%'  AND companyName NOT LIKE '%sanitary%' "
                    + "AND companyName NOT LIKE '%Dayton%'  AND companyName NOT LIKE '%metro%' "
                    + "AND companyName NOT LIKE '%fire%'    AND companyName NOT LIKE '%career%' "
                    + "AND companyName NOT LIKE '%OPBA%'    AND companyName NOT LIKE '%residential%' "
                    + "AND companyName NOT LIKE '%SEIU%'    AND companyName NOT LIKE '%University%' "
                    + "AND companyName NOT LIKE '%Toledo%'  AND companyName NOT LIKE '%watershed%' "
                    + "AND companyName NOT LIKE '%Waste%'   AND companyName NOT LIKE '%Living Center%' "
                    + "AND companyName NOT LIKE '%Job%'     ORDER BY companyName";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Global.setRecordCount(Global.getRecordCount() + 1);
                list.add(new partyNameTableModel(
                        rs.getString("id"),
                        rs.getString("companyName")
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
    
    public static partyModel getPartydetails(int partyID) {
        System.out.println("PartyID: " + partyID);
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
    
    public static void savePartyInformation(partyModel item) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.connectToDB();
            String sql = "UPDATE party SET "
                    + "Prefix = ?, "        //01
                    + "FirstName = ?, "     //02
                    + "MiddleInitial = ?, " //03
                    + "LastName = ?, "      //04
                    + "Suffix = ?, "        //05
                    + "NameTitle = ?, "     //06
                    + "JobTitle = ?, "      //07
                    + "CompanyName = ?, "   //08
                    + "Address1 = ?, "      //09
                    + "Address2 = ?, "      //10
                    + "Address3 = ?, "      //11
                    + "city = ?, "          //12
                    + "stateCode = ?, "     //13
                    + "zipCode = ?, "       //14
                    + "phone1 = ?, "        //15
                    + "phone2 = ?, "        //16
                    + "emailAddress = ?  "  //17
                    + "WHERE id = ?";       //18
            ps = conn.prepareStatement(sql);
            ps.setString( 1, item.getPrefix());
            ps.setString( 2, item.getFirstName());
            ps.setString( 3, item.getMiddleInitial());
            ps.setString( 4, item.getLastName());
            ps.setString( 5, item.getSuffix());
            ps.setString( 6, item.getNameTitle());
            ps.setString( 7, item.getJobTitle());
            ps.setString( 8, item.getCompanyName());
            ps.setString( 9, item.getAddress1());
            ps.setString(10, item.getAddress2());
            ps.setString(11, item.getAddress3());
            ps.setString(12, item.getCity());
            ps.setString(13, item.getState());
            ps.setString(14, item.getZip());
            ps.setString(15, item.getPhoneOne());
            ps.setString(16, item.getPhoneTwo());
            ps.setString(17, item.getEmailAddress());
            ps.setInt   (18, item.getPartyID());
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            DbUtils.closeQuietly(ps);
            DbUtils.closeQuietly(rs);
            DbUtils.closeQuietly(conn);
        }
    }
    
    public static void deleteContact(int partyID){
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBConnection.connectToDB();
            String sql = "UPDATE party SET Active = 0 WHERE id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, partyID);
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            DbUtils.closeQuietly(ps);
            DbUtils.closeQuietly(conn);
        }
    }
    
}
