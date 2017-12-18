package com.mycompany.sms;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import helpers.StageHolder;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.StringConverter;

/**
 * FXML Controller class
 *
 * @author omari
 */
public class HistoryController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private DatePicker from;

    @FXML
    private DatePicker to;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
//                 from.setPromptText("DD/MM/YYYY");
                 
              final   String pattern = "dd/MM/yyy";

from.setPromptText(pattern.toLowerCase());

from.setConverter(new StringConverter<LocalDate>() {
     DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

     @Override 
     public String toString(LocalDate date) {
         if (date != null) {
             return dateFormatter.format(date);
         } else {
             return "";
         }
     }

     @Override 
     public LocalDate fromString(String string) {
         if (string != null && !string.isEmpty()) {
             return LocalDate.parse(string, dateFormatter);
         } else {
             return null;
         }
     }
 });

to.setPromptText(pattern.toLowerCase());

to.setConverter(new StringConverter<LocalDate>() {
     DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

     @Override 
     public String toString(LocalDate date) {
         if (date != null) {
             return dateFormatter.format(date);
         } else {
             return "";
         }
     }

     @Override 
     public LocalDate fromString(String string) {
         if (string != null && !string.isEmpty()) {
             return LocalDate.parse(string, dateFormatter);
         } else {
             return null;
         }
     }
 });

    }

    @FXML
    private void search(ActionEvent event) {
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(Calendar.DAY_OF_MONTH, from.getValue().getDayOfMonth());
        cal.set(Calendar.MONTH, from.getValue().getMonthValue()-1);
        cal.set(Calendar.YEAR, from.getValue().getYear());
        long fromLong = cal.getTimeInMillis();
        cal.clear();
        cal.set(Calendar.DAY_OF_MONTH, to.getValue().getDayOfMonth());
        cal.set(Calendar.MONTH, to.getValue().getMonthValue()-1);
        cal.set(Calendar.YEAR, to.getValue().getYear());
        long toLong = cal.getTimeInMillis();
        System.out.println(fromLong);
        System.out.println(toLong);

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
    private void goToHistory(MouseEvent event) {
//           try {
//            Parent root = FXMLLoader.load(getClass().getResource("/fxml/history.fxml"));
//            Scene scene = new Scene(root);
//            scene.getStylesheets().add("/styles/history.css");
//            Stage stage = StageHolder.getStag();
//            stage.setScene(scene);
//        } catch (IOException ex) {
//            Logger.getLogger(SignInController.class.getName()).log(Level.SEVERE, null, ex);
//        }
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
