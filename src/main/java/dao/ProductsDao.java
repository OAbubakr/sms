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
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Product;
import models.Transaction;

/**
 *
 * @author omari
 */
public class ProductsDao {

    public ArrayList<Product> getAllProducts() {
        ArrayList<Product> prodList = new ArrayList<>();
        try {
            ResultSet rs = DatabaseConn.conn.createStatement().executeQuery("select * from products where active = 'true' ORDER BY name ASC;");
            while (rs.next()) {
                Product p = new Product();
                p.setId(rs.getInt("id"));
                p.setName(rs.getString("name"));
                p.setPrice(rs.getDouble("price"));
                p.setQuantity(rs.getInt("quantity"));
                p.setNotes(rs.getString("notes"));
                prodList.add(p);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return prodList;
    }

    public boolean insertProduct(Product product) {
        boolean r = false;

        try {
            DatabaseConn.conn.createStatement().execute("insert into products(name, price, quantity, notes)"
                    + " values('" + product.getName() + "', "
                    + "'" + product.getPrice() + "', "
                    + "'" + product.getQuantity() + "', "
                    + "'" + product.getNotes() + "');");
            r = true;
        } catch (SQLException ex) {
            Logger.getLogger(ProductsDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return r;
    }

    public boolean sellProduct(Transaction transaction) {

        boolean r = false;
        try {
            DatabaseConn.conn.createStatement().execute("insert into transactions(product_id, sell_price, quantity, date)"
                    + " values('" + transaction.getProduct().getId() + "', "
                    + "'" + transaction.getSellingPrice() + "', "
                    + "'" + transaction.getQuantity() + "', "
                    + "'" + transaction.getDate() + "');");
            DatabaseConn.conn.createStatement().execute("update products set quantity = quantity - " + transaction.getQuantity() + " where id = " + transaction.getProduct().getId() + ";");

            r = true;
        } catch (SQLException ex) {
            Logger.getLogger(ProductsDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return r;
    }

    public ArrayList<Transaction> getTransacrions(long from, long to) {
        ArrayList<Transaction> transations = new ArrayList<>();
        try {
            ResultSet rs = DatabaseConn.conn.createStatement().executeQuery("select p.name as name, t.sell_price as price, t.quantity as quantity, t.date as date   from transactions t, products p  where t.product_id = p.id and "
                    + "date between " + from + " and " + to + " ;");

            while (rs.next()) {
                Transaction t = new Transaction();
                Product p = new Product();

                p.setName(rs.getString("name"));
                t.setSellingPrice(rs.getDouble("price"));
                t.setQuantity(rs.getInt("quantity"));
                t.setDate(rs.getLong("date"));
                t.setProduct(p);
                transations.add(t);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return transations;
    }

    public boolean editProduct(Product product) {
        boolean r = false;
        try {
            DatabaseConn.conn.createStatement().execute("update products set"
                    + " name = '" + product.getName() + "',"
                    + " price = " + product.getPrice() + ","
                    + " quantity = " + product.getQuantity() + ", "
                    + " notes = '" + product.getNotes() + "'"
                    + " where id = " + product.getId());
            r = true;
        } catch (SQLException ex) {
            Logger.getLogger(ProductsDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return r;

    }

    public boolean removeProduct(Product p) {
        boolean r = false;
        try {
            DatabaseConn.conn.createStatement().execute("update products set"
                    + " active = 'false'" 
                    + " where id = " + p.getId());
            r = true;
        } catch (SQLException ex) {
            Logger.getLogger(ProductsDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return r;
    }

}
