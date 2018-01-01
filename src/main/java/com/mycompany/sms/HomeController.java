package com.mycompany.sms;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import dao.ProductsDao;
import helpers.DatabaseConn;
import helpers.StageHolder;
import helpers.UserHolder;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.Optional;
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
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Pair;
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
    private TextArea notes;
    @FXML
    private Label availableQuantity;
    @FXML
    private Label success;
    @FXML
    private Label failed;

    ObservableList observableList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        createTables();
        setListView();
        disableInputs();

        final Pattern doublePattern = Pattern.compile("\\d{0,6}|\\d{0,6}\\.\\d{0,2}");
        final Pattern intPattern = Pattern.compile("\\d{0,6}");
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
        if (authenticate()) {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/fxml/add_product.fxml"));
                Scene scene = new Scene(root);
                scene.getStylesheets().add("/styles/add_product.css");
                Stage stage = StageHolder.getStag();
                stage.setScene(scene);
            } catch (IOException ex) {
                Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void goToEditProduct(MouseEvent event) {
        if (authenticate()) {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/fxml/editProduct.fxml"));
                Scene scene = new Scene(root);
                scene.getStylesheets().add("/styles/editproduct.css");
                Stage stage = StageHolder.getStag();
                stage.setScene(scene);
            } catch (IOException ex) {
                Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void goToHistory(MouseEvent event) {
        if (authenticate()) {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/fxml/history.fxml"));
                Scene scene = new Scene(root);
                scene.getStylesheets().add("/styles/history.css");
                Stage stage = StageHolder.getStag();
                stage.setScene(scene);
            } catch (IOException ex) {
                Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void goToMain(MouseEvent event) {
    }

    @FXML
    private void sell(ActionEvent event) {
        if (validate()) {
            Transaction transaction = new Transaction();
            transaction.setProduct((Product) productsList.getValue());
            transaction.setQuantity(Integer.parseInt(sellQuantity.getText()));
            transaction.setSellingPrice(Double.parseDouble(sellPrice.getText()));
            transaction.setDate(new Date().getTime());
            if (new ProductsDao().sellProduct(transaction)) {
                success();
            } else {
                failed();
            }
        } else {
            failed();
        }

    }

    @FXML
    private void selection(ActionEvent event) {
        Product p = (Product) productsList.getValue();
        if (p != null) {
            enableInputs();
            sellPrice.setText(p.getFinalPrice() + "");
            fixedPrice.setText(p.getPrice() + "");
            availableQuantity.setText(p.getQuantity() + "");
            notes.setText(p.getNotes());
            if (p.getQuantity() > 0) {
                sellQuantity.setText("1");
            } else {
                sellQuantity.setText("0");
            }
        }
    }

    private boolean validate() {
        if (productsList.getValue() != null && !sellPrice.getText().trim().isEmpty() && !sellQuantity.getText().trim().isEmpty()) {
            return Integer.parseInt(sellQuantity.getText()) > 0
                    && Integer.parseInt(sellQuantity.getText()) <= ((Product) productsList.getValue()).getQuantity();
        } else {
            return false;
        }
    }

    private void success() {
        failed.setVisible(false);
        success.setVisible(true);
        productsList.setValue(null);
        sellQuantity.setText("");
        fixedPrice.setText("");
        sellPrice.setText("");
        availableQuantity.setText("");
        notes.setText("");
        setListView();
        disableInputs();
    }

    private void enableInputs() {
        sellQuantity.setEditable(true);
        sellPrice.setEditable(true);
    }

    private void disableInputs() {
        sellQuantity.setEditable(false);
        sellPrice.setEditable(false);
    }

    private void failed() {
        failed.setVisible(true);
        success.setVisible(false);
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

    private void createTables() {
        try {
            DatabaseConn.conn.createStatement().execute("create table if not exists products (id integer primary key autoincrement, name string, price double, final_price double, quantity integer, notes string, active BOOLEAN default true);");
            DatabaseConn.conn.createStatement().execute("create table if not exists transactions (id integer primary key autoincrement, product_id int, sell_price double, quantity integer, date integer);");

        } catch (SQLException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        @FXML
        private Label finalPrice;

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
            finalPrice.setText(product.getFinalPrice()+"");

        }

        public HBox getBox() {
            return hBox;
        }
    }

    private boolean authenticate() {
        Dialog<Pair<String, String>> dialog = new Dialog<>();

        final ButtonType loginButtonType = new ButtonType("دخول", ButtonData.OK_DONE);
        ButtonType cancelButtonType = new ButtonType("إلغاء", ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, cancelButtonType);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        final TextField username = new TextField();
        username.setPromptText("اسم المستخدم");
        final PasswordField password = new PasswordField();
        password.setPromptText("كلمة السر");

        grid.add(new Label("اسم المستخدم"), 1, 0);
        grid.add(username, 0, 0);
        grid.add(new Label("اسم المستخدم"), 1, 1);
        grid.add(password, 0, 1);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(new Callback<ButtonType, Pair<String, String>>() {
            @Override
            public Pair<String, String> call(ButtonType dialogButton) {
                if (dialogButton == loginButtonType) {
                    return new Pair<>(username.getText(), password.getText());
                }
                return null;
            }
        });

        Optional<Pair<String, String>> result = dialog.showAndWait();

        if (result.isPresent()) {
            return result.get().getKey().equals(UserHolder.getUserName()) && result.get().getValue().equals(UserHolder.getPassword());
        } else {
            return false;
        }
    }

}
