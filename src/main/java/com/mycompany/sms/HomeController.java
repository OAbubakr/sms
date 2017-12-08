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
import java.util.Date;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
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
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import models.Product;
import models.Transaction;

/**
 * FXML Controller class
 *
 * @author omari
 */
public class HomeController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private ComboBox productsList;
    @FXML
    private TextField sellPrice;
    @FXML
    private TextField sellQuantity;
    @FXML
    private Label fixedPrice;
    @FXML
    private Label availableQuantity;
    
    ObservableList observableList = FXCollections.observableArrayList();
    
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
        sellPrice.setTextFormatter(doublFormatter);
        sellQuantity.setTextFormatter(intFormatter);
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
    private void goToHistory(ActionEvent event) {
    }
    
    @FXML
    private void goToMain(ActionEvent event) {
    }
    
    @FXML
    private void sell(ActionEvent event) {
        //TODO Validate
        Transaction transaction = new Transaction();
        transaction.setProduct((Product) productsList.getValue());
        transaction.setQuantity(Integer.parseInt( sellQuantity.getText()));
        transaction.setSellingPrice(Double.parseDouble(sellPrice.getText()));
        transaction.setDate(new Date());
        new ProductsDao().sellProduct(transaction);
        
    }
    
    @FXML
    private void selection(ActionEvent event) {
        Product p = (Product) productsList.getValue();
        fixedPrice.setText(p.getPrice() + "");
        sellPrice.setText(p.getPrice() + "");
        availableQuantity.setText(p.getQuantity() + "");
        sellQuantity.setText("1");
    }
    
    @FXML
    private void deleteCell(ActionEvent event) {
        productsList.getItems().remove(0, productsList.getItems().size());
        System.out.println(observableList);
        
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
