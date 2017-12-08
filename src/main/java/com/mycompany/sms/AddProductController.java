package com.mycompany.sms;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import dao.ProductsDao;
import helpers.StageHolder;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import models.Product;

/**
 * FXML Controller class
 *
 * @author omari
 */
public class AddProductController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private TextField name;
    @FXML
    private TextField price;
    @FXML
    private TextField quantity;
    @FXML
    private TextArea notes;
    @FXML
    private Label success;
    @FXML
    private Label failed;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        final Pattern doublePattern = Pattern.compile("\\d*|\\d+\\.\\d*");
        final Pattern intPattern = Pattern.compile("\\d*");
        TextFormatter doublFormatter = new TextFormatter(new UnaryOperator<TextFormatter.Change>() {
            @Override
            public TextFormatter.Change apply(TextFormatter.Change change) {
                return doublePattern.matcher(change.getControlNewText()).matches() ? change : null;
            }
        });
        TextFormatter intFormatter = new TextFormatter(new UnaryOperator<TextFormatter.Change>() {
            @Override
            public TextFormatter.Change apply(TextFormatter.Change change) {
                return intPattern.matcher(change.getControlNewText()).matches() ? change : null;
            }
        });
        price.setTextFormatter(doublFormatter);
        quantity.setTextFormatter(intFormatter);

    }

    @FXML
    private void goToAddProduct(MouseEvent event) {

    }

    @FXML
    private void goToHistory(MouseEvent event) {
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

    @FXML
    private void saveProduct(ActionEvent event) {
        if (!validate()) {
            failed();
        } else {
            Product product = new Product();
            product.setName(name.getText());
            product.setPrice(Double.parseDouble(price.getText()));
            product.setQuantity(Integer.parseInt(quantity.getText()));
            product.setNotes(notes.getText());
            boolean r = new ProductsDao().insertProduct(product);
            if (r) {
                success();
            } else {
                failed();
            }

        }
    }

    private boolean validate() {
        return (!name.getText().trim().isEmpty() && !price.getText().trim().isEmpty() && !quantity.getText().trim().isEmpty());
    }

    private void success() {
        failed.setVisible(false);
        success.setVisible(true);
        name.setText("");
        price.setText("");
        quantity.setText("");
        notes.setText("");
    }

    private void failed() {
        failed.setVisible(true);
        success.setVisible(false);
    }
}
