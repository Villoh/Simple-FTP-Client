/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mikel.agl.cliente_ftp.metodos;

import com.mikel.agl.cliente_ftp.App;
import com.mikel.agl.cliente_ftp.controladores.MainMenuController;
import com.mikel.agl.cliente_ftp.controladores.ProgressController;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
/**
 *
 * @author Villoh
 */
public class View {
    
    /**
     * Carga la vista main_menu y le pasa el stage al controlador
     * @param stage stage en el que va a cargar la vista
     * @param ftp Objeto FTP con todos los metodos y datos de la conexión.
     * @param rootItem nodo ra;iz de la vista en arbol.
     */
    public static void loadMainMenu(Stage stage, ConexionFTP ftp, TreeItem<String> rootItem) {
        Scene scene;
        try {
            FXMLLoader fxmlLoader = loadFXML("main_menu");
            scene = new Scene(fxmlLoader.load());
            //Obtengo el controlador y seteo las diferentes variables.
            MainMenuController mmc = ((MainMenuController) fxmlLoader.getController());
            mmc.setStage(stage);
            mmc.setFTP(ftp);
            mmc.setRootItem(rootItem);
            scene.setFill(Color.TRANSPARENT);
            stage.setScene(scene);
            stage.sizeToScene();
            stage.centerOnScreen();
            stage.show();
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
    
    /**
     * Carga la vista progress y le pasa el stage al controlador
     * @param stage stage en el que va a cargar la vista
     */
    public static void loadProgress(Stage stage) {
        Scene scene;
        try {
            FXMLLoader fxmlLoader = loadFXML("progress");
            scene = new Scene(fxmlLoader.load());
            ((ProgressController) fxmlLoader.getController()).setStage(stage);
            stage.getIcons().add(new Image(App.class.getResourceAsStream("resources/icon.png")));
            scene.setFill(Color.TRANSPARENT);
            stage.setScene(scene);
            stage.sizeToScene();
            stage.centerOnScreen();
            stage.show();
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }

    private static FXMLLoader loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader;
    }
}
