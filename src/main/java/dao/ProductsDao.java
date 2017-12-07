/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import helpers.DatabaseConn;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import models.Product;

/**
 *
 * @author omari
 */
public class ProductsDao {

    public ArrayList<Product> getAllProducts() {
        ArrayList<Product> prodList = new ArrayList<>();
        
        try {
            ResultSet rs = DatabaseConn.conn.createStatement().executeQuery("select * from products;");
            while (rs.next()) {
                
                Product p = new Product();
                p.setName(rs.getString("name"));
                p.setPrice(rs.getDouble("price"));
                p.setQuantity(rs.getInt("quantity"));
                prodList.add(p);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        
        return prodList;

    }

}
