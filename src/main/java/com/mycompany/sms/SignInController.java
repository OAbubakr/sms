package com.mycompany.sms;

import helpers.DatabaseConn;
import helpers.StageHolder;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SignInController implements Initializable {

    @FXML
    private Label label;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;

    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        if (signIn(username.getText(), password.getText())) {
            startMainapp();
        } else {
            label.setText("NO");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            DatabaseConn.conn.createStatement().execute("create table if not exists users (id integer primary key autoincrement, username string, password string);");
            DatabaseConn.conn.createStatement().execute("create table if not exists products (id integer primary key autoincrement, name string, price double, quantity integer);");
            DatabaseConn.conn.createStatement().execute("create table if not exists transactions (id integer primary key autoincrement, product_id int, sell_price double, quantity integer, date timestamp);");

        } catch (SQLException ex) {
            Logger.getLogger(SignInController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static boolean signIn(String username, String password) {
//        boolean r = false;
//        try {
//            ResultSet rs = DatabaseConn.conn.createStatement().executeQuery("select * from users where " + " username = '" + username + "' and password = '" + password + "';");
//            r = rs.next();
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
//        return r;
return true;
    }

    private void startMainapp()  {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/home.fxml"));
            Scene scene = new Scene(root);
            scene.getStylesheets().add("/styles/Styles.css");
            Stage stage = StageHolder.getStag();
            stage.setScene(scene);
        } catch (IOException ex) {
            Logger.getLogger(SignInController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
