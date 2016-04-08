/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sql;

import com.model.partyModel;
import com.model.salutationModel;
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
public class SQLsalutation {
    
    public static List getSalutationList(){
        List<salutationModel> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.connectToDB();
            String sql = "";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();           
            
            while(rs.next()) {
               salutationModel item = new salutationModel();
               item.setSalutationID(rs.getInt(""));
               item.setSalutationName(rs.getString(""));
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
}
