package com.mycompany.sms;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import helpers.StageHolder;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author omari
 */
public class EditProductController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
        @FXML
    private void goToAddProduct(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/add_product.fxml"));
            Scene scene = new Scene(root);
            scene.getStylesheets().add("/styles/add_product.css");
            Stage stage = StageHolder.getStag();
            stage.setScene(scene);
        } catch (IOException ex) {
            Logger.getLogger(SignInController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
  @FXML
    private void goToEditProduct(MouseEvent event) {
//        try {
//            Parent root = FXMLLoader.load(getClass().getResource("/fxml/editProduct.fxml"));
//            Scene scene = new Scene(root);
//            scene.getStylesheets().add("/styles/editproduct.css");
//            Stage stage = StageHolder.getStag();
//            stage.setScene(scene);
//        } catch (IOException ex) {
//            Logger.getLogger(SignInController.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }
    @FXML
    private void goToHistory(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/history.fxml"));
            Scene scene = new Scene(root);
            scene.getStylesheets().add("/styles/history.css");
            Stage stage = StageHolder.getStag();
            stage.setScene(scene);
        } catch (IOException ex) {
            Logger.getLogger(SignInController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void goToMain(MouseEvent event) {
         try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/home.fxml"));
            Scene scene = new Scene(root);
            scene.getStylesheets().add("/styles/home.css");
            Stage stage = StageHolder.getStag();
            stage.setScene(scene);
        } catch (IOException ex) {
            Logger.getLogger(SignInController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
