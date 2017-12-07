package com.mycompany.sms;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import dao.ProductsDao;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import models.Product;

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
    private ListView productsList;
//    private List<Product> stringSet = new ArrayList<>();
    ObservableList observableList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setListView();
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
