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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import models.Product;

/**
 * FXML Controller class
 *
 * @author omari
 */
public class EditProductController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private ComboBox productsList;
    ObservableList observableList = FXCollections.observableArrayList();
    @FXML
    private TextField name;
    @FXML
    private TextField quantity;
    @FXML
    private TextField price;
    @FXML
    private TextArea notes;
    @FXML
    private Label failed;
    @FXML
    private Label success_edit;
    @FXML
    private Label success_remove;
    
    Product p;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setListView();
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
        price.lengthProperty().addListener(new ChangeListener<Number>() {
            
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {
                if (newValue.intValue() > oldValue.intValue()) {
                    // Check if the new character is greater than LIMIT
                    if (price.getText().length() >= 9) {

                        // if it's 11th character then just setText to previous
                        // one
                        price.setText(price.getText().substring(0, 9));
                    }
                }
            }
        });
        
        quantity.lengthProperty().addListener(new ChangeListener<Number>() {
            
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {
                if (newValue.intValue() > oldValue.intValue()) {
                    // Check if the new character is greater than LIMIT
                    if (quantity.getText().length() >= 6) {

                        // if it's 11th character then just setText to previous
                        // one
                        quantity.setText(quantity.getText().substring(0, 6));
                    }
                }
            }
        });
        
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
    
    @FXML
    private void selection(ActionEvent event) {
        p = (Product) productsList.getValue();
        if (p != null) {
            name.setText(p.getName());
            price.setText(p.getPrice() + "");
            quantity.setText(p.getQuantity() + "");
            notes.setText(p.getNotes());
        }
    }
    
    @FXML
    private void edit(ActionEvent event) {
        if (!validate()) {
            failed();
        } else {
           
            p.setName(name.getText());
            p.setPrice(Double.parseDouble(price.getText()));
            p.setQuantity(Integer.parseInt(quantity.getText()));
            p.setNotes(notes.getText());
            boolean r = new ProductsDao().editProduct(p);
            if (r) {
                success_edit();
            } else {
                failed();
            }
            
        }
        
    }
    
    private void success_edit() {
        setListView();
        productsList.setValue(null);
        p = null;
        failed.setVisible(false);
        success_edit.setVisible(true);
        success_remove.setVisible(false);
        name.setText("");
        price.setText("");
        quantity.setText("");
        notes.setText("");
    }
    
    private void failed() {
        failed.setVisible(true);
        success_edit.setVisible(false);
        success_remove.setVisible(false);
    }
    
    @FXML
    private void remove(ActionEvent event) {
    }
    
    private boolean validate() {
        return (!name.getText().trim().isEmpty() && !price.getText().trim().isEmpty() && !quantity.getText().trim().isEmpty());
    }
    
    public void setListView() {
        
        observableList.setAll(new ProductsDao().getAllProducts());
        
        productsList.setItems(observableList);
        
        productsList.setCellFactory(
                new Callback<ListView<Product>, ListCell<Product>>() {
            @Override
            public ListCell<Product> call(ListView<Product> listView) {
                
                return new ListViewCell();
            }
        });
    }
    
    private static class ListViewCell extends ListCell<Product> {
        
        @Override
        public void updateItem(Product product, boolean empty) {
            super.updateItem(product, empty);
            if (product != null) {
                Data data = new Data();
                data.setInfo(product);
                
                if (product.getQuantity() == 0) {
                    setDisable(true);
                } else {
                    setDisable(false);
                }
                setGraphic(data.getBox());
            } else {
                setGraphic(null);
            }
        }
    }
    
    private static class Data {
        
        @FXML
        private HBox hBox;
        @FXML
        private Label name;
        @FXML
        private Label price;
        @FXML
        private Label quantity;
        
        public Data() {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/product_cell.fxml"));
            fxmlLoader.setController(this);
            try {
                fxmlLoader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        
        public void setInfo(Product product) {
            name.setText(product.getName());
            price.setText(product.getPrice() + "");
            quantity.setText(product.getQuantity() + "");
            
        }
        
        public HBox getBox() {
            return hBox;
        }
    }
    
}
