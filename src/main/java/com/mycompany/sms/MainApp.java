package com.mycompany.sms;

import helpers.DatabaseConn;
import helpers.StageHolder;
import helpers.UserHolder;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        setData();
        StageHolder.setStage(stage);
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/home.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/home.css");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setWidth(1000.0);
        stage.setHeight(667.0);
        stage.show();

    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    private void setData() {
        try {

            File fXmlFile = File.createTempFile("temp", "");
            fXmlFile.deleteOnExit();
            FileOutputStream out = new FileOutputStream(fXmlFile);
            InputStream in = getClass().getResourceAsStream("/config/config.xml");
            byte[] buffer = new byte[1024];
            int len = in.read(buffer);
            while (len != -1) {
                out.write(buffer, 0, len);
                len = in.read(buffer);
            }
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();
            String username = doc.getElementsByTagName("username").item(0).getTextContent().trim();
            String password = doc.getElementsByTagName("password").item(0).getTextContent().trim();
            String dbPath = doc.getElementsByTagName("database_path").item(0).getTextContent().trim();
//            String dbPath = "jdbc:sqlite:" + System.getProperty("user.dir") + "\\SMS.sqlite";
            DatabaseConn.setUrl(dbPath);
            UserHolder.setUserName(username);
            UserHolder.setPassword(password);
            in.close();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
