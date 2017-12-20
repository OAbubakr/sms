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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import models.Product;
import models.Transaction;

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
    private ListView productsList;
    ObservableList observableList = FXCollections.observableArrayList();

    @FXML
    private DatePicker from;

    @FXML
    private DatePicker to;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        final String pattern = "dd/MM/yyy";

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

        from.setValue(LocalDate.now());
        to.setValue(LocalDate.now());
    }

    @FXML
    private void search(ActionEvent event) {
        if (from.getValue() != null && to.getValue() != null) {
            Calendar cal = Calendar.getInstance();
            cal.clear();
            cal.set(Calendar.DAY_OF_MONTH, from.getValue().getDayOfMonth());
            cal.set(Calendar.MONTH, from.getValue().getMonthValue() - 1);
            cal.set(Calendar.YEAR, from.getValue().getYear());
            long fromLong = cal.getTimeInMillis();
            cal.clear();
            cal.set(Calendar.DAY_OF_MONTH, to.getValue().getDayOfMonth());
            cal.set(Calendar.MONTH, to.getValue().getMonthValue() - 1);
            cal.set(Calendar.YEAR, to.getValue().getYear());
            long toLong = cal.getTimeInMillis();
            System.out.println(fromLong);
            System.out.println(toLong);
            setListView(fromLong, toLong);
        }
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

    @FXML
    private void goToEditProduct(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/editProduct.fxml"));
            Scene scene = new Scene(root);
            scene.getStylesheets().add("/styles/editproduct.css");
            Stage stage = StageHolder.getStag();
            stage.setScene(scene);
        } catch (IOException ex) {
            Logger.getLogger(SignInController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setListView(long from, long to) {

        observableList.setAll(new ProductsDao().getTransacrions(from, to));

        productsList.setItems(observableList);

        productsList.setCellFactory(
                new Callback<ListView<Transaction>, ListCell<Transaction>>() {
            @Override
            public ListCell<Transaction> call(ListView<Transaction> listView) {

                return new ListViewCell();
            }
        });
    }

    private static class ListViewCell extends ListCell<Transaction> {

        @Override
        public void updateItem(Transaction transaction, boolean empty) {
            super.updateItem(transaction, empty);
            if (transaction != null) {
                Data data = new Data();
                data.setInfo(transaction);

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
        private Label sellPrice;
        @FXML
        private Label quantity;
        @FXML
        private Label date;

        public Data() {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/history_cell.fxml"));
            fxmlLoader.setController(this);
            try {
                fxmlLoader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        public void setInfo(Transaction transaction) {
            name.setText(transaction.getProduct().getName());
            sellPrice.setText(transaction.getSellingPrice() + "");
            quantity.setText(transaction.getQuantity() + "");
            Date d = new Date(transaction.getDate());
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            String reportDate = df.format(d);
            date.setText(reportDate);
        }

        public HBox getBox() {
            return hBox;
        }
    }

}
