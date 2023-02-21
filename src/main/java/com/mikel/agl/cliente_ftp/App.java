package com.mikel.agl.cliente_ftp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.StageStyle;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    public static Stage st;
    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("login"));
        st = stage;
        st.setTitle("Cliente FTP");
        st.getIcons().add(new Image(App.class.getResourceAsStream("resources/icon.png")));
        st.initStyle(StageStyle.TRANSPARENT);
        scene.setFill(Color.TRANSPARENT);
        st.setScene(scene);
        st.sizeToScene();
        st.centerOnScreen();
        st.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}