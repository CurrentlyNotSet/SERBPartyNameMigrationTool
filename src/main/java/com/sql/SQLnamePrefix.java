/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sql;

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
public class SQLnamePrefix {
    
    public static List getSalutationList(){
        List<String> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.connectToDB();
            String sql = "SELECT prefix FROM namePrefix WHERE active = 1";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();           
            list.add("");
            while(rs.next()) {
               list.add(rs.getString("prefix"));
           }  
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        } finally {
            DbUtils.closeQuietly(ps);
            DbUtils.closeQuietly(rs);
            DbUtils.closeQuietly(conn);
        }
        return list;
    }
    
}
